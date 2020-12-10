package com.kw.modules.app.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kw.modules.app.entity.UserDaytransEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户日买卖
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-12 08:07:04
 */
@Mapper
public interface UserDaytransDao extends BaseMapper<UserDaytransEntity> {

    List<HashMap> queryReportList(@Param("params") Map<String,Object> params);

    IPage<UserDaytransEntity> queryPage(IPage<UserDaytransEntity> page,@Param("params") Map<String,Object> params);
}
