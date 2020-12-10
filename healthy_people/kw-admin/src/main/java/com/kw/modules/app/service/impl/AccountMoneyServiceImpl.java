package com.kw.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.Constant;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;
import com.kw.common.utils.RedisUtils;
import com.kw.modules.app.dao.AccountMoneyDao;
import com.kw.modules.app.entity.AccountMoneyEntity;
import com.kw.modules.app.service.AccountMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;


@Service("accountMoneyService")
public class AccountMoneyServiceImpl extends ServiceImpl<AccountMoneyDao, AccountMoneyEntity> implements AccountMoneyService {

    @Autowired
    RedisUtils redisUtils;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        int dl =  redisUtils.get(Constant.IS_DL,int.class);
        params.put("dl",dl);
        return new PageUtils(baseMapper.queryList( new Query<AccountMoneyEntity>().getPage(params), params));
    }


    @Override
    @Transactional
    public int addParentMoney(Long userId, BigDecimal amount) {
        return baseMapper.addParentMoney(userId,amount);
    }


    @Override
    @Transactional
    public long addPowerSource(String[] userIds, BigDecimal source) {
        return baseMapper.addPowerSource(userIds, source);
    }
    @Override
    public void addSonMoney(Long userId, BigDecimal amount) {
        baseMapper.addSonMoney(userId,amount);
    }

}