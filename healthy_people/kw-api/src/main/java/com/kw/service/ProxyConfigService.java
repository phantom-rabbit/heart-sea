package com.kw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.entity.ProxyConfigEntity;

import java.util.Map;

/**
 * 代理表
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
public interface ProxyConfigService extends IService<ProxyConfigEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

