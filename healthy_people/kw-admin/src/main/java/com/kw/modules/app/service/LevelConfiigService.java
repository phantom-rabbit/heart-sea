package com.kw.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.modules.app.entity.LevelConfiigEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 级别配置
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
public interface LevelConfiigService extends IService<LevelConfiigEntity> {

    PageUtils queryPage(Map<String, Object> params);



    LevelConfiigEntity getLevelConfigAtAuthFinished();

}

