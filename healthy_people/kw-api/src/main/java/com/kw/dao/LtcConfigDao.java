package com.kw.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kw.entity.LtcConfigEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 矿机配置表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Mapper
public interface LtcConfigDao extends BaseMapper<LtcConfigEntity> {

    IPage<LtcConfigEntity> queryList(IPage<LtcConfigEntity> page,@Param("params") Map<String, Object> params);
}
