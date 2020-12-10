package com.kw.modules.app.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kw.modules.app.entity.AccountEntity;
import com.kw.modules.app.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员记录
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:15:46
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

    IPage<?> queryList(IPage<UserEntity> page,@Param("params") Map<String, Object> params);

    List<HashMap> queryReportList(@Param("params") Map<String,Object> params);
}
