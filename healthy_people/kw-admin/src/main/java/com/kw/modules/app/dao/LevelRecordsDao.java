package com.kw.modules.app.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kw.modules.app.entity.LevelRecordsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 级别升级审核
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-21 03:21:00
 */
@Mapper
public interface LevelRecordsDao extends BaseMapper<LevelRecordsEntity> {

    IPage<?> queryList(IPage<LevelRecordsEntity> page, @Param("params") Map<String, Object> params);

    LevelRecordsEntity queryById(@Param("id") Long id);
}
