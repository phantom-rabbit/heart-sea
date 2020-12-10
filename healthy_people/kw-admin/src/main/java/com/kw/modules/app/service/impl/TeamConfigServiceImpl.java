package com.kw.modules.app.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;

import com.kw.modules.app.dao.TeamConfigDao;
import com.kw.modules.app.entity.TeamConfigEntity;
import com.kw.modules.app.service.TeamConfigService;


@Service("teamConfigService")
public class TeamConfigServiceImpl extends ServiceImpl<TeamConfigDao, TeamConfigEntity> implements TeamConfigService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        return new PageUtils(baseMapper.queryList(new Query<TeamConfigEntity>().getPage(params), params));
    }

}