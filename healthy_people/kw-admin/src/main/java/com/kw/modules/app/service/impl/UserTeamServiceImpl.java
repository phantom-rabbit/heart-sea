package com.kw.modules.app.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;

import com.kw.modules.app.dao.UserTeamDao;
import com.kw.modules.app.entity.UserTeamEntity;
import com.kw.modules.app.service.UserTeamService;


@Service("userTeamService")
public class UserTeamServiceImpl extends ServiceImpl<UserTeamDao, UserTeamEntity> implements UserTeamService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserTeamEntity> page = this.baseMapper.queryPage(new Query<UserTeamEntity>().getPage(params), params);

        return new PageUtils(page);
    }

    @Override
    public List<UserTeamEntity> queryByInviterId(Long userId) {
        return baseMapper.queryByInviterId(userId);
    }

    @Override
    public UserTeamEntity queryByUserId(Long userId) {
        return baseMapper.queryByUserId(userId);
    }
}