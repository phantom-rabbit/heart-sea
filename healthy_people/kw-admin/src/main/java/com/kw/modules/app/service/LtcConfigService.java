package com.kw.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.modules.app.entity.LtcConfigEntity;

import java.util.Map;

/**
 * 矿机配置表
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
public interface LtcConfigService extends IService<LtcConfigEntity> {

    PageUtils queryPage(Map<String, Object> params);



}

