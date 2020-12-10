package com.kw.modules.app.dao;

import com.kw.modules.app.entity.LevelConfiigEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 级别配置
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Mapper
public interface LevelConfiigDao extends BaseMapper<LevelConfiigEntity> {
    LevelConfiigEntity getLevelConfigAtAuthFinished();
}
