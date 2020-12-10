package com.kw.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.modules.app.entity.UserDaybillsEntity;

import java.util.Map;

/**
 * 用户日业绩
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-11 18:29:17
 */
public interface UserDaybillsService extends IService<UserDaybillsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

