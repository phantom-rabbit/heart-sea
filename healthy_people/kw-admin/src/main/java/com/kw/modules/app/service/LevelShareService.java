package com.kw.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;

import com.kw.modules.app.entity.LevelShareEntity;

import java.util.List;
import java.util.Map;

/**
 * 分红汇总表
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-09 23:01:59
 */
public interface LevelShareService extends IService<LevelShareEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void gather();

    void share(Long[] ids);
}

