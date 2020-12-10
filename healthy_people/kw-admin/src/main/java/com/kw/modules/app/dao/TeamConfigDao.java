package com.kw.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kw.modules.app.entity.TeamConfigEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 战队配置表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Mapper
public interface TeamConfigDao extends BaseMapper<TeamConfigEntity> {
    IPage<?> queryList(IPage<TeamConfigEntity> page, @Param("params") Map<String, Object> params);
}
