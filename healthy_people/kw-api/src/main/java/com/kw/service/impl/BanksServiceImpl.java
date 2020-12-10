package com.kw.service.impl;

import com.kw.common.constant.CacheConstants;
import com.kw.dao.BanksDao;
import com.kw.entity.BanksEntity;
import com.kw.service.BanksService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;




@Service("banksService")
public class BanksServiceImpl extends ServiceImpl<BanksDao, BanksEntity> implements BanksService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BanksEntity> page = this.page(
                new Query<BanksEntity>().getPage(params),
                new QueryWrapper<BanksEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Cacheable(value=CacheConstants.BANKS)
    public List<BanksEntity> list() {
        return super.list();
    }
}