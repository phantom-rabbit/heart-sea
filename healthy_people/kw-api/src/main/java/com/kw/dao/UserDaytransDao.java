package com.kw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kw.entity.UserDaytransEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户日买卖
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-12 08:07:04
 */
@Mapper
public interface UserDaytransDao extends BaseMapper<UserDaytransEntity> {

    IPage<UserDaytransEntity> selectPageVo(IPage<UserDaytransEntity> page, @Param("ids") Object[] ids);
}
