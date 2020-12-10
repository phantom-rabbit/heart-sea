package com.kw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.enums.LogType;
import com.kw.common.utils.Constant;
import com.kw.common.utils.DateUtils;
import com.kw.common.utils.R;
import com.kw.common.utils.RedisUtils;
import com.kw.dao.AccountDao;
import com.kw.entity.*;
import com.kw.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service("accountService")
public class AccountServiceImpl extends ServiceImpl<AccountDao, AccountEntity> implements AccountService {
    @Autowired
    LtcConfigService ltcConfigService;

    @Autowired
    UserLtcService userLtcService;

    @Autowired
    AccountLogService accountLogService;


    @Autowired
    UserTeamService userTeamService;

    @Autowired
    AccountDao accountDao;

    @Autowired
    RedisUtils redisUtils;

    @Override
    public AccountEntity queryByUserId(Long userId) {
        return baseMapper.queryByUserId(userId);
    }

    @Override
    @Transactional
    public R buyLtc(Long userId, String ltcId) {

        LtcConfigEntity ltcConfigEntity  = ltcConfigService.getById(ltcId);
        if(ltcConfigEntity.getConfigStatus()==0){
            return R.error("矿机尚未开房");
        }
        if(ltcConfigEntity == null){
            return R.error("矿机不存在");
        }
        int count  = userLtcService.count(new QueryWrapper<UserLtcEntity>().eq("ltc_id",ltcId).in("ltc_status",0,1).eq("user_id",userId).eq("is_award",0));
        if(count>=ltcConfigEntity.getMaxHold()){
            return R.error("拥有该种类矿机过多");
        }
        //减少用户余额
        if(this.baseMapper.reduceAmount(userId,ltcConfigEntity.getParentPrice())<1){
            return R.error("余额不足");
        }
        //记录日志
        accountLogService.logAccount(userId,ltcConfigEntity.getParentPrice(),LogType.LTC_BUY.getName(), LogType.LTC_BUY.getValue(),1);

        //增加用户矿机
        UserLtcEntity userLtcEntity = new UserLtcEntity(userId,ltcConfigEntity, "用户购买") ;
        userLtcEntity.setIsAward(0);

        userLtcService.save(userLtcEntity);

        addPower(userId,ltcConfigEntity.getPower());
        //记录日志
        accountLogService.logAccount(userId,ltcConfigEntity.getPower(),LogType.LTC_BUY_POWER.getName(), LogType.LTC_BUY_POWER.getValue(),3);



        //搜索邀请人
        UserTeamEntity userTeamEntity = userTeamService.queryByUserId(userId);

        //推荐奖 上游5%
       // String award = redisUtils.get(Constant.INVITER_KJ_AWARD);
        String high = redisUtils.get(Constant.SS_HIGH);
        String lower = redisUtils.get(Constant.SS_LOWER);
        if(StringUtils.isNotBlank(lower)&&StringUtils.isNotBlank(high)&&userTeamEntity.getInviterUserId()!=0L) {



            //发烧奖
            //查询我的矿机总量
     //       List<UserLtcEntity> ltcEntities = userLtcService.list(new QueryWrapper<UserLtcEntity>().eq("user_id",userId).eq("is_award",0).in("ltc_status",0,1));

            List<UserLtcEntity> ltcEntitiesInviter = userLtcService.list(new QueryWrapper<UserLtcEntity>().eq("user_id",userTeamEntity.getInviterUserId()).eq("is_award",0).eq("ltc_status",1));
            BigDecimal parentInviter = BigDecimal.ZERO;
            for(UserLtcEntity userLtcEntity1:ltcEntitiesInviter){
                parentInviter = parentInviter.add(userLtcEntity1.getParentPrice());
            }
            BigDecimal parentMe = ltcConfigEntity.getParentPrice();
//            for(UserLtcEntity userLtcEntity1:ltcEntities){
//                parentMe = parentMe.add(userLtcEntity1.getParentPrice());
//            }
            log.debug("邀请人:"+parentInviter.toString()+"被邀请人:"+parentMe.toString());
            if(parentInviter.compareTo(parentMe)>=0){
                addParentMoney(userTeamEntity.getInviterUserId(),new BigDecimal(high).multiply(ltcConfigEntity.getParentPrice()) );
                accountLogService.logAccount(userTeamEntity.getInviterUserId(),new BigDecimal(high).multiply(ltcConfigEntity.getParentPrice()),LogType.LTC_BUY_AWARD_MONEY.getName()+parentInviter.toString()+"邀请"+parentMe.toString(), LogType.LTC_BUY_AWARD_MONEY.getValue(),1);

            }else{
                addParentMoney(userTeamEntity.getInviterUserId(),new BigDecimal(lower).multiply(ltcConfigEntity.getParentPrice()) );
                accountLogService.logAccount(userTeamEntity.getInviterUserId(),new BigDecimal(lower).multiply(ltcConfigEntity.getParentPrice()),LogType.LTC_BUY_AWARD_MONEY.getName()+parentInviter.toString()+"邀请"+parentMe.toString(), LogType.LTC_BUY_AWARD_MONEY.getValue(),1);

            }



        }



        //给用户增加动力
        return R.ok("购买成功");
    }


    @Override
    public int frenZen(Long userId, String amount) {
        return accountDao.frenZen(userId,amount);
    }

    @Override
    public int deductFrezen(Long userId, BigDecimal amount) {
        return accountDao.deductFrezen(userId,amount);
    }

    @Override
    @Transactional
    public int addParentMoney(Long userId, BigDecimal amount) {
        return accountDao.addParentMoney(userId,amount);
    }

    public int updateParentMoneyByMap(HashMap<Object, Object> map){return accountDao.updateParentMoneyByMap(map);}

    @Override
    public int countRealNumByUserId(Long userId) {
        return accountDao.countRealNumByUserId(userId);
    }

    @Override
    public BigDecimal queryTeamPower(Long userId) {
        return baseMapper.queryTeamPower(userId);
    }

    @Override
    public int querySameLevelCountInTeam(Long userId, Long levelId) {
        return baseMapper.querySameLevelCountInTeam(userId, levelId);
    }

    @Override
    public int awardPowerSource(Long userId, BigDecimal powerSource) {
        String date = DateUtils.format(new Date());
        String date_key = date+"-"+userId;
        //日统计
        baseMapper.updateEveryDayPowerSource(date_key,date,powerSource,userId);
        return baseMapper.awardPowerSource(userId,powerSource);

    }

    @Override
    public void LogDaytrans(BuyTransEntity buyTransEntity) {
        String date = DateUtils.format(new Date());
        String date_key = date+"-"+buyTransEntity.getSellerId();
        this.baseMapper.updateEveryDaySell(buyTransEntity.getSellerId(),buyTransEntity.getFee(),buyTransEntity.getAmount(),date,date_key);
        date_key = date+"-"+buyTransEntity.getBuyerId();
        this.baseMapper.updateEveryBuy(buyTransEntity.getBuyerId(),buyTransEntity.getAmount(),date,date_key);
    }

    /***
     *
     *动力值
     */

    private void addPower(Long userId, BigDecimal power) {
        String date = DateUtils.format(new Date());
        String date_key = date+"-"+userId;
        //日统计
        baseMapper.updateEveryDayPower(date_key,date,power,userId);
        this.baseMapper.addPower(userId,power);
    }




    @Override
    public AccountEntity querySdFromCity(String province, String city) {
        return baseMapper.querySdFromCity(province, city);
    }

    @Override
    public IPage<AccountEntity> selectPageVo(IPage<AccountEntity> page, Object[] ids) {
        return baseMapper.selectPageVo(page,ids);
    }


    @Override
    @Transactional
    public long addPowerSource(String[] userIds, BigDecimal source) {
        return baseMapper.addPowerSource(userIds, source);
    }

    @Override
    @Transactional
    public void updateVip(Long userId, Long vipId) {
        baseMapper.updateVip(userId, vipId);
    }

    @Override
    public void addSonMoney(Long userId, BigDecimal amount) {
        baseMapper.addSonMoney(userId,amount);
    }


    @Override
    public AccountEntity queryByIdcard(String idcard) {
        return baseMapper.queryByIdcard(idcard);
    }

    @Override
    public List<AccountEntity> queryByIds(Object[] ids) {
        return baseMapper.queryByIds(ids);
    }

    @Override
    public int canCleOrder(Long sellerId, BigDecimal money) {
        return baseMapper.canCleOrder(sellerId,money);
    }
}