package com.kw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kw.entity.UserTeamEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户战队层级关系
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Mapper
public interface UserTeamDao extends BaseMapper<UserTeamEntity> {

    UserTeamEntity queryByUserId(Long userId);

    List<UserTeamEntity> queryByInviterId(Long userId);
}
