package com.kw.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;
import com.kw.modules.app.dao.LevelRecordsDao;
import com.kw.modules.app.entity.LevelRecordsEntity;
import com.kw.modules.app.service.LevelRecordsService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("levelRecordsService")
public class LevelRecordsServiceImpl extends ServiceImpl<LevelRecordsDao, LevelRecordsEntity> implements LevelRecordsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        return new PageUtils(baseMapper.queryList(new Query<LevelRecordsEntity>().getPage(params), params));
    }

    @Override
    public LevelRecordsEntity queryById(Long id) {
        return baseMapper.queryById(id);
    }
}