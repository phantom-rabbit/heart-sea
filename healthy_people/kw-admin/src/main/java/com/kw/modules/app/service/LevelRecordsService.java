package com.kw.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.modules.app.entity.LevelRecordsEntity;

import java.util.Map;

/**
 * 级别升级审核
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-21 03:21:00
 */
public interface LevelRecordsService extends IService<LevelRecordsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    LevelRecordsEntity queryById(Long id);
}

