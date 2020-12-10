package com.kw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;
import com.kw.dao.TeamConfigDao;
import com.kw.entity.TeamConfigEntity;
import com.kw.service.TeamConfigService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("teamConfigService")
public class TeamConfigServiceImpl extends ServiceImpl<TeamConfigDao, TeamConfigEntity> implements TeamConfigService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TeamConfigEntity> page = this.page(
                new Query<TeamConfigEntity>().getPage(params),
                new QueryWrapper<TeamConfigEntity>()
        );

        return new PageUtils(page);
    }


    @Override
    public TeamConfigEntity queryByUserId(Long userId) {
        return baseMapper.queryByUserId(userId);
    }
}