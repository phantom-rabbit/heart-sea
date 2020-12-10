package com.kw.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.exception.RRException;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;
import com.kw.modules.app.dao.AccountLogDao;
import com.kw.modules.app.entity.AccountLogEntity;
import com.kw.modules.app.entity.AccountMoneyEntity;
import com.kw.modules.app.service.AccountLogService;
import com.kw.modules.app.service.AccountMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


@Service("accountLogService")
public class AccountLogServiceImpl extends ServiceImpl<AccountLogDao, AccountLogEntity> implements AccountLogService {

    @Autowired
    AccountMoneyService accountMoneyService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        return new PageUtils(baseMapper.queryList(new Query<AccountLogEntity>().getPage(params), params));
    }


    @Override
    public void logAccount(Long userId, BigDecimal parentPrice, String mark, String type, Integer kind) {
        AccountMoneyEntity accountMoneyEntity = accountMoneyService.getOne(new QueryWrapper<AccountMoneyEntity>().eq("user_id", userId));

        AccountLogEntity accountLogEntity = null;
        if (kind == 1) {
            accountLogEntity = new AccountLogEntity(userId, accountMoneyEntity.getParentMoney(), parentPrice, mark, type, kind);
        } else if (kind == 2) {
            accountLogEntity = new AccountLogEntity(userId, accountMoneyEntity.getSonMoney(), parentPrice, mark, type, kind);
        } else if (kind == 3) {
            accountLogEntity = new AccountLogEntity(userId, accountMoneyEntity.getPower(), parentPrice, mark, type, kind);
        } else if (kind == 4) {
            accountLogEntity = new AccountLogEntity(userId, accountMoneyEntity.getPowerSource(), parentPrice, mark, type, kind);
        } else {
            throw new RRException("资金类型错误");
        }
        accountLogEntity.setCreateTime(new Date());
        save(accountLogEntity);
    }
}