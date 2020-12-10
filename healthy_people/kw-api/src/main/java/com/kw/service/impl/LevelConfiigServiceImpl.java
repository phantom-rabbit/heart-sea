package com.kw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;
import com.kw.dao.LevelConfiigDao;
import com.kw.entity.LevelConfiigAwrdLtcEntity;
import com.kw.entity.LevelConfiigEntity;
import com.kw.service.LevelConfiigService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("levelConfiigService")
public class LevelConfiigServiceImpl extends ServiceImpl<LevelConfiigDao, LevelConfiigEntity> implements LevelConfiigService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LevelConfiigEntity> page = this.page(
                new Query<LevelConfiigEntity>().getPage(params),
                new QueryWrapper<LevelConfiigEntity>()
        );

        return new PageUtils(page);
    }


    @Override
    public LevelConfiigEntity getLevelConfigAtAuthFinished() {
        return baseMapper.getLevelConfigAtAuthFinished();
    }
}