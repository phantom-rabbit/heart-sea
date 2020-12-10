package com.kw.modules.app.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;

import com.kw.modules.app.dao.GenesisDao;
import com.kw.modules.app.entity.GenesisEntity;
import com.kw.modules.app.service.GenesisService;


@Service("genesisService")
public class GenesisServiceImpl extends ServiceImpl<GenesisDao, GenesisEntity> implements GenesisService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        return new PageUtils(baseMapper.queryList(new Query<GenesisEntity>().getPage(params), params));
    }

}