package com.kw.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.entity.UserDaytransEntity;


import java.util.List;
import java.util.Map;

/**
 * 用户日买卖
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-12 08:07:04
 */
public interface UserDaytransService extends IService<UserDaytransEntity> {

    PageUtils queryPage(Map<String, Object> params);

    IPage<UserDaytransEntity> selectPageVo(IPage<UserDaytransEntity> page, Object[] ids);
}

