package com.kw.service.impl;

import com.kw.entity.AccountEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;

import com.kw.dao.AccountMoneyDao;
import com.kw.entity.AccountMoneyEntity;
import com.kw.service.AccountMoneyService;


@Service("accountMoneyService")
public class AccountMoneyServiceImpl extends ServiceImpl<AccountMoneyDao, AccountMoneyEntity> implements AccountMoneyService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AccountMoneyEntity> page = this.page(
                new Query<AccountMoneyEntity>().getPage(params),
                new QueryWrapper<AccountMoneyEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public AccountMoneyEntity queryByUserId(Long id) {
        return this.baseMapper.queryByUserId(id);
    }

}