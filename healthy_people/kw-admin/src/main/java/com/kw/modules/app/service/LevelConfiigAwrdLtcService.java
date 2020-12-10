package com.kw.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.modules.app.entity.LevelConfiigAwrdLtcEntity;

import java.util.Map;

/**
 * 级别配置奖励
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 15:30:54
 */
public interface LevelConfiigAwrdLtcService extends IService<LevelConfiigAwrdLtcEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

