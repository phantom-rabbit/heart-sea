package com.kw.modules.app.service.impl;

import com.kw.common.constant.CacheConstants;
import com.kw.common.enums.BuyTransStatus;
import com.kw.common.enums.LogType;
import com.kw.common.exception.RRException;
import com.kw.common.utils.R;
import com.kw.modules.app.entity.PurposeBuyEntity;
import com.kw.modules.app.service.AccountLogService;
import com.kw.modules.app.service.AccountService;
import com.kw.modules.app.service.PurposeBuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;

import com.kw.modules.app.dao.BuyTransDao;
import com.kw.modules.app.entity.BuyTransEntity;
import com.kw.modules.app.service.BuyTransService;
import org.springframework.transaction.annotation.Transactional;


@Service("buyTransService")
public class BuyTransServiceImpl extends ServiceImpl<BuyTransDao, BuyTransEntity> implements BuyTransService {

    @Autowired
    AccountService accountService ;

    @Autowired
    AccountLogService accountLogService ;
    @Autowired
    BuyTransDao buyTransDao ;

    @Autowired
    PurposeBuyService purposeBuyService ;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        return new PageUtils(baseMapper.queryList(new Query<BuyTransEntity>().getPage(params), params));
    }

    @Override
    @Transactional
    public void cancle(Integer id) {
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

    @Override
    @Transactional
    @CacheEvict(value = CacheConstants.SUMPREBUY,allEntries = true)
    public R confirm(Long transId) {
        BuyTransEntity buyTransEntity =  getById(transId);
        if(!buyTransEntity.getStatus().equals(BuyTransStatus.STATUS1.getValue())){
            return R.ok("不可操作状态");
        }
        //扣除冻结金额
        if(accountService.deductFrezen(buyTransEntity.getSellerId(),buyTransEntity.getAmount())<1){
            throw  new RRException("系统异常");
        }
        //记录日志
        accountLogService.logAccount(buyTransEntity.getSellerId(),buyTransEntity.getAmount(),LogType.OPC_SEL_DEDUCT.getName(),LogType.OPC_SEL_DEDUCT.getValue(),1);
        //扣除冻结手续费
        if(accountService.deductFrezen(buyTransEntity.getSellerId(),buyTransEntity.getFee())<1){
            throw  new RRException("系统异常");
        }
        //记录日志
        accountLogService.logAccount(buyTransEntity.getSellerId(),buyTransEntity.getFee(),LogType.OPC_SEL_DEDUCT_FEETRANS.getName(),LogType.OPC_SEL_DEDUCT_FEETRANS.getValue(),1);
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


}