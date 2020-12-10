package com.kw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kw.entity.UserDaybillsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * 用户日业绩
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-11 18:29:17
 */
@Mapper
public interface UserDaybillsDao extends BaseMapper<UserDaybillsEntity> {

    IPage<UserDaybillsEntity> selectPageVo(IPage<UserDaybillsEntity> page, @Param("ids") Object[] ids);
}
