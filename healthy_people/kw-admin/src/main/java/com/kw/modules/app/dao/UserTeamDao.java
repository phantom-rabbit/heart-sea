package com.kw.modules.app.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kw.modules.app.entity.UserTeamEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户战队层级关系
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Mapper
public interface UserTeamDao extends BaseMapper<UserTeamEntity> {
    List<UserTeamEntity> queryByInviterId(Long userId);

    UserTeamEntity queryByUserId(Long userId);

    IPage<UserTeamEntity> queryPage(IPage<UserTeamEntity> page, @Param("params") Map<String,Object> params);
}
