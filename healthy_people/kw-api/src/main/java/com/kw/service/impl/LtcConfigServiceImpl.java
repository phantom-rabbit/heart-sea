package com.kw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;
import com.kw.dao.LtcConfigDao;
import com.kw.entity.LtcConfigEntity;
import com.kw.service.LtcConfigService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.Map;


@Service("ltcConfigService")
public class LtcConfigServiceImpl extends ServiceImpl<LtcConfigDao, LtcConfigEntity> implements LtcConfigService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LtcConfigEntity> page = baseMapper.queryList(
                new Query<LtcConfigEntity>().getPage(params),
                params
        );

        return new PageUtils(page);
    }


    @Override
    @Cacheable(value="kw_coin:ltc_config:id",key="#id")
    public LtcConfigEntity getById(Long id) {
        return super.getById(id);
    }

}