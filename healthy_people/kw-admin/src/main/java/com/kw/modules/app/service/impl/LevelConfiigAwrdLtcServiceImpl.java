package com.kw.modules.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;

import com.kw.modules.app.dao.LevelConfiigAwrdLtcDao;
import com.kw.modules.app.entity.LevelConfiigAwrdLtcEntity;
import com.kw.modules.app.service.LevelConfiigAwrdLtcService;


@Service("levelConfiigAwrdLtcService")
public class LevelConfiigAwrdLtcServiceImpl extends ServiceImpl<LevelConfiigAwrdLtcDao, LevelConfiigAwrdLtcEntity> implements LevelConfiigAwrdLtcService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LevelConfiigAwrdLtcEntity> page  = this.baseMapper.selectPage( new Query<LevelConfiigAwrdLtcEntity>().getPage(params), new QueryWrapper<LevelConfiigAwrdLtcEntity>());
        return new PageUtils(page);

    }

}