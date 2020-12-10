package com.kw.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.modules.app.entity.TeamConfigEntity;

import java.util.Map;

/**
 * 战队配置表
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
public interface TeamConfigService extends IService<TeamConfigEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

