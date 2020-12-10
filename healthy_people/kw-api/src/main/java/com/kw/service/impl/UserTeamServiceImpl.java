package com.kw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;
import com.kw.dao.UserTeamDao;
import com.kw.entity.UserTeamEntity;
import com.kw.service.UserTeamService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("userTeamService")
public class UserTeamServiceImpl extends ServiceImpl<UserTeamDao, UserTeamEntity> implements UserTeamService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserTeamEntity> page = this.page(
                new Query<UserTeamEntity>().getPage(params),
                new QueryWrapper<UserTeamEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public UserTeamEntity queryByUserId(Long userId) {
        return baseMapper.queryByUserId(userId);
    }

    @Override
    public List<UserTeamEntity> queryByInviterId(Long userId) {
        return baseMapper.queryByInviterId(userId);
    }
}