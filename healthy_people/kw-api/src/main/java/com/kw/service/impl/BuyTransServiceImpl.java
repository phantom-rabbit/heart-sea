package com.kw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.constant.CacheConstants;
import com.kw.common.enums.BuyTransStatus;
import com.kw.common.enums.LogType;
import com.kw.common.enums.PayType;
import com.kw.common.exception.RRException;
import com.kw.common.sms.config.SmsConstant;
import com.kw.common.sms.config.SmsResult;
import com.kw.common.task.TaskService;
import com.kw.common.utils.*;
import com.kw.dao.BuyTransDao;
import com.kw.dao.PurposeBuyDao;
import com.kw.entity.AccountEntity;
import com.kw.entity.AccountMoneyEntity;
import com.kw.entity.BuyTransEntity;
import com.kw.entity.PurposeBuyEntity;
import com.kw.service.*;
import com.kw.task.WaitBuyerSubmitTask;
import lombok.extern.java.Log;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service("buyTransService")
@Log
public class BuyTransServiceImpl extends ServiceImpl<BuyTransDao, BuyTransEntity> implements BuyTransService {


    @Autowired
    AccountService accountService;
    @Autowired
    TaskService taskService;

    @Autowired
    AccountLogService accountLogService;

    @Autowired
    AccountMoneyService accountMoneyService;

    @Autowired
    SmsService smsService;


    @Autowired
    RedisUtils redisUtils;

    @Autowired
    PurposeBuyService purposeBuyService;
    @Autowired
    BuyTransDao buyTransDao;


    @Autowired
    UserService userService;



    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<BuyTransEntity> queryWrapper = new QueryWrapper<BuyTransEntity>() ;

        //买家和卖家
        if(params.get("type").equals("1")){
            queryWrapper.eq("buyer_id",params.get("userId"));

        }else{
            queryWrapper.eq("seller_id",params.get("userId"));
        }

        IPage<BuyTransEntity> page = this.page(
                new Query<BuyTransEntity>().getPage(params),queryWrapper

        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    @Caching(evict={@CacheEvict(value = CacheConstants.TRANS_MONEY_24H,allEntries = true),
            @CacheEvict(value = CacheConstants.BUY_TRANS_COUNT_30DAY,key ="#purposeBuyEntity.buyerId+'-'+#yesterday+'-'+#now")})
    public R sell(Long id,PurposeBuyEntity purposeBuyEntity, String amount,String yesterday, String now) {


        if(list(new QueryWrapper<BuyTransEntity>().eq("seller_id",id).in("status",0,1)).size()>0){
            throw new RRException("当前有其他正在售卖中的OPC");
        }


        AccountEntity account = accountService.queryByUserId(id);
        if(account.getRealStatus()!=2){
            throw new RRException("请先进行实名认证");
        }


        if(PayType.ALIPAY.getValue().equals(purposeBuyEntity.getPayType()+"")){
           if(account.getAlipayStatus()!=1){
               throw new RRException("尚未绑定支付宝");
           }

        }
        else if(PayType.USDT.getValue().equals(purposeBuyEntity.getPayType()+"")){
            if(account.getUsdtStatus()!=1){
                throw new RRException("尚未绑定USDT");
            }
        }
        else if(PayType.BANK.getValue().equals(purposeBuyEntity.getPayType()+"")){
            if(account.getBankStatus()!=1){
                throw new RRException("尚未绑定银行");
            }
        }
        AccountMoneyEntity accountMoneyEntity = accountMoneyService.queryByUserId(id);


        if(accountMoneyEntity.getSonMoney().compareTo(redisUtils.get(Constant.BUSINESS_MAC_LIMIT,BigDecimal.class))<0){
            throw new RRException("您的MAC小于"+redisUtils.get(Constant.BUSINESS_MAC_LIMIT)+"个");
        }

         //冻结purpose 增加
         if(purposeBuyService.preSell(purposeBuyEntity.getId(),amount)<1){
             throw new RRException("系统错误");
         }
        //冻结资金
         if((accountService.frenZen(id,amount))<1){
            throw new RRException("余额不足");
         }

        accountLogService.logAccount(id,new BigDecimal(amount),LogType.OPC_SELL_FREZEN.getName(),LogType.OPC_SELL_FREZEN.getValue(),1);

        //冻结手续费
        if((accountService.frenZen(id,new BigDecimal(amount).multiply(new BigDecimal(account.getTransFee())).toString()))<1){
            throw new RRException("余额不足");
        }
        accountLogService.logAccount(id,new BigDecimal(amount).multiply(new BigDecimal(account.getTransFee())),LogType.OPC_SELL_FREZEN_TRANS.getName(),LogType.OPC_SELL_FREZEN_TRANS.getValue(),1);

        //增加购买日志
         BuyTransEntity buyTransEntity = new BuyTransEntity();
         buyTransEntity.setBuyerId(purposeBuyEntity.getBuyerId());
         buyTransEntity.setPrice(purposeBuyEntity.getPrice().divide(purposeBuyEntity.getTotal(),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(amount)));
         buyTransEntity.setPurposeId(purposeBuyEntity.getId());
         buyTransEntity.setAmount(new BigDecimal(amount));
         buyTransEntity.setCreateTime(new Date());
         buyTransEntity.setPayType(purposeBuyEntity.getPayType());
         buyTransEntity.setSellerId(id);
         buyTransEntity.setFee(new BigDecimal(amount).multiply(new BigDecimal(account.getTransFee())));
         buyTransEntity.setStatus(BuyTransStatus.STATUS0.getValue());
         buyTransEntity.setSettleStatus(0);
         save(buyTransEntity);
         log.info("buyTransEntity id"+buyTransEntity.getId());
         WaitBuyerSubmitTask waitBuyerSubmitTask = new WaitBuyerSubmitTask(buyTransEntity.getId(),redisUtils.get(Constant.BUSINESS_TIME,Integer.class)*60*60*1000);
         taskService.addTask(waitBuyerSubmitTask);
       //  AccountEntity buyer = accountService.queryByUserId();
         //
        String mobile = userService.getById(purposeBuyEntity.getBuyerId()).getMobile();
        SmsResult result = smsService.sendWithTemplate(mobile, SmsConstant.SMS_TRADE_ALI_TEMPLATE_ID, new String[]{"买入",redisUtils.get(Constant.BUSINESS_TIME,Integer.class)+""});
        // redisUtils.delete();
         return R.ok("出售成功,请等待对方付款");
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConstants.SUMPREBUY,allEntries = true)
    public R confirm(Long transId, Long userId) {
        BuyTransEntity buyTransEntity =  getById(transId);


        if(!buyTransEntity.getStatus().equals(BuyTransStatus.STATUS1.getValue())){
            return R.ok("不可操作状态");
        }

        if(!buyTransEntity.getSellerId().equals(userId)){
            return R.ok("不能操作不是你的交易");
        }
        //扣除冻结金额
        if(accountService.deductFrezen(userId,buyTransEntity.getAmount())<1){
            throw  new RRException("系统异常");
        }
        //记录日志
        accountLogService.logAccount(userId,buyTransEntity.getAmount(),LogType.OPC_SEL_DEDUCT.getName(),LogType.OPC_SEL_DEDUCT.getValue(),1);


        //扣除冻结手续费
        if(accountService.deductFrezen(userId,buyTransEntity.getFee())<1){
            throw  new RRException("系统异常");
        }
        //记录日志
        accountLogService.logAccount(userId,buyTransEntity.getFee(),LogType.OPC_SEL_DEDUCT_FEETRANS.getName(),LogType.OPC_SEL_DEDUCT_FEETRANS.getValue(),1);

        //更新交易状态


        //

        //给购买人加钱
        if(accountService.addParentMoney(buyTransEntity.getBuyerId(),buyTransEntity.getAmount())<1){
            throw  new RRException("系统异常");
        }
        accountLogService.logAccount(buyTransEntity.getBuyerId(),buyTransEntity.getAmount(),LogType.OPC_BUY.getName(),LogType.OPC_BUY.getValue(),1);


        accountService.LogDaytrans(buyTransEntity);

        buyTransEntity.setSellerSubmitTime(new Date());
        buyTransEntity.setStatus(BuyTransStatus.STATUS2.getValue());
        if(!updateById(buyTransEntity)){
            throw  new RRException("系统异常");
        }
        //检查交易是否满额

        List<BuyTransEntity> list =  list(new QueryWrapper<BuyTransEntity>().eq("purpose_id",buyTransEntity.getPurposeId()));
        int count = 0 ;
        for(BuyTransEntity buyTrans:list){
            if(buyTrans.getStatus()==0||buyTrans.getStatus()==1){
                //有交易正在进行
                count = 1;
            }
        }
        PurposeBuyEntity purposeBuyEntity = purposeBuyService.getById(buyTransEntity.getPurposeId());
        //没有交易在进行 并且满额 那么设置为交易完成
        if(count==0&&purposeBuyEntity.getTotal().compareTo(purposeBuyEntity.getHasBuy())==0){
            purposeBuyEntity.setStatus(1);
            purposeBuyService.updateById(purposeBuyEntity);
        }

        return R.ok("交易确认成功");
    }



    @Override
    @Cacheable(value=CacheConstants.TRANS_MONEY_24H,key = "#yesterday+'-'+#now",unless = "#result == null")
    public BigDecimal selectTransMoney(String yesterday, String now) {
        return this.baseMapper.selectTransMoney(yesterday,now);
    }

    @Override
    public List<BuyTransEntity> queryListByStatus() {
        return buyTransDao.queryListByStatus();
    }

    @Override
    @Transactional
    public void cancle(Long id) {
        BuyTransEntity buyTransEntity = getById(id);
        if(buyTransEntity.getStatus().equals(BuyTransStatus.STATUS2.getValue())||buyTransEntity.getStatus()==-1){
            throw  new RRException("不能操作已经完成的");
        }
        if(accountService.canCleOrder(buyTransEntity.getSellerId(),buyTransEntity.getFee().add(buyTransEntity.getAmount()))<1){
            throw  new RRException("系统错误");
        }
        accountLogService.logAccount(buyTransEntity.getSellerId(),buyTransEntity.getFee().add(buyTransEntity.getAmount()),"交易后台取消,款项退回",LogType.TRANS_CANCLE.getValue(),1);
        // PurposeBuyEntity purposeBuyEntity = purposeBuyService.getById(buyTransEntity.getPurposeId());
        purposeBuyService.reduceHasBuy(buyTransEntity.getPurposeId(),buyTransEntity.getAmount());
        buyTransEntity.setStatus(-1);
        updateById(buyTransEntity);
    }
}