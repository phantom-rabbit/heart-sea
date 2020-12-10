package com.kw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.kw.entity.GenesisEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 合伙人
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-12 09:41:09
 */
@Mapper
public interface GenesisDao extends BaseMapper<GenesisEntity> {

    IPage<?> queryList(IPage<GenesisEntity> page, @Param("params") Map<String, Object> params);

}
