package com.kw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;

import com.kw.dao.GenesisDao;
import com.kw.entity.GenesisEntity;
import com.kw.service.GenesisService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("genesisService")
public class GenesisServiceImpl extends ServiceImpl<GenesisDao, GenesisEntity> implements GenesisService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        return new PageUtils(baseMapper.queryList(new Query<GenesisEntity>().getPage(params), params));
    }

}