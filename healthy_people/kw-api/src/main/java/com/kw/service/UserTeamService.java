package com.kw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.entity.UserTeamEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户战队层级关系
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
public interface UserTeamService extends IService<UserTeamEntity> {

    PageUtils queryPage(Map<String, Object> params);

    UserTeamEntity queryByUserId(Long userId);

    List<UserTeamEntity> queryByInviterId(Long userId);
}

