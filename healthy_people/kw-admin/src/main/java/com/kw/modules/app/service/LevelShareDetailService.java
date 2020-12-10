package com.kw.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.modules.app.entity.LevelShareDetailEntity;


import java.util.List;
import java.util.Map;

/**
 * 分红表
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-09 23:01:59
 */
public interface LevelShareDetailService extends IService<LevelShareDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<LevelShareDetailEntity> gatherList();

    boolean updateByStatusAndUserId(int i, Long userId);

    void cacue();
}

