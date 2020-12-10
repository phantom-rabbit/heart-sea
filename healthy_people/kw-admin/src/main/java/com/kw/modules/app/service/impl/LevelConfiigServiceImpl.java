package com.kw.modules.app.service.impl;

import com.kw.common.constant.CacheConstants;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;

import com.kw.modules.app.dao.LevelConfiigDao;
import com.kw.modules.app.entity.LevelConfiigEntity;
import com.kw.modules.app.service.LevelConfiigService;


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
//    @Override
//    @Cacheable(CacheConstants.LEVEL_CONFIG_LIST)
//    public List<LevelConfiigEntity> list(){
//            return super.list();
//    }

    @Override
    public LevelConfiigEntity getLevelConfigAtAuthFinished() {
        return baseMapper.getLevelConfigAtAuthFinished();
    }

}