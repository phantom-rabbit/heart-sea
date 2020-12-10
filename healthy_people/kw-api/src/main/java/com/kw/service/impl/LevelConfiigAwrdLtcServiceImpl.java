package com.kw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;
import com.kw.dao.LevelConfiigAwrdLtcDao;
import com.kw.entity.LevelConfiigAwrdLtcEntity;
import com.kw.service.LevelConfiigAwrdLtcService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("levelConfiigAwrdLtcService")
public class LevelConfiigAwrdLtcServiceImpl extends ServiceImpl<LevelConfiigAwrdLtcDao, LevelConfiigAwrdLtcEntity> implements LevelConfiigAwrdLtcService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LevelConfiigAwrdLtcEntity> page  = this.baseMapper.selectPage( new Query<LevelConfiigAwrdLtcEntity>().getPage(params), new QueryWrapper<LevelConfiigAwrdLtcEntity>());
        return new PageUtils(page);

    }



}