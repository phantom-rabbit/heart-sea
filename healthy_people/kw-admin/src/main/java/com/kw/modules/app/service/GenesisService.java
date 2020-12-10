package com.kw.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.modules.app.entity.GenesisEntity;

import java.util.Map;

/**
 * 合伙人
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-12 09:41:09
 */
public interface GenesisService extends IService<GenesisEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

