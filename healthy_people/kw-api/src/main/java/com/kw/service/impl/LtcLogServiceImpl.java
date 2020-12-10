package com.kw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.enums.LogType;
import com.kw.common.exception.RRException;
import com.kw.common.utils.DateUtils;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;
import com.kw.dao.LtcLogDao;
import com.kw.entity.AccountEntity;
import com.kw.entity.LtcLogEntity;
import com.kw.service.AccountLogService;
import com.kw.service.AccountService;
import com.kw.service.LtcLogService;
import com.kw.service.UserService;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


@Service("ltcLogService")
public class LtcLogServiceImpl extends ServiceImpl<LtcLogDao, LtcLogEntity> implements LtcLogService {


    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountLogService accountLogService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        return new PageUtils(baseMapper.queryList(new Query<LtcLogEntity>().getPage(params), params));
    }


    @Override
    public BigDecimal findLtcIncomeTotal(Long userId) {
        return baseMapper.findLtcIncomeTotal(userId);
    }

    @Override
    @Transactional
    public void income(Long userId) {
        AccountEntity account = accountService.queryByUserId(userId);
        if(account == null){
            throw new RRException("抱歉，您账户异常");
        }
        //查询
        BigDecimal total = baseMapper.findLtcIncomeTotal(userId);
        BigDecimal coinTotal = baseMapper.findLtcIncomeCoinTotal(userId);
        if((total == null || total.signum() == 0) && (coinTotal == null || coinTotal.signum() == 0)){
            throw new RRException("抱歉，您今日还没有可领取的收益");
        }
        //增加余额
        if(total != null)
        accountService.addParentMoney(account.getUserId(),total)   ;
       // account.setParentMoney(total.add(account.getParentMoney()));
        if(coinTotal != null)
        accountService.addSonMoney(account.getUserId(),coinTotal) ;
     //   account.setSonMoney(coinTotal.add(account.getSonMoney()));
     //   accountService.updateById(account);
        //记录资金日志
        accountLogService.logAccount(userId,
                total,
                "领取"+DateFormatUtils.format(new Date(),"yyyy-MM-dd")+"日收益",
                LogType.LTC_INCOME.name(),
                1);
        accountLogService.logAccount(userId,
                coinTotal,
                "领取"+DateFormatUtils.format(new Date(),"yyyy-MM-dd")+"日收益",
                LogType.LTC_INCOME.name(),
                2);
        //更新状态
        baseMapper.updateLtcIncomeStatus(userId);
    }
}