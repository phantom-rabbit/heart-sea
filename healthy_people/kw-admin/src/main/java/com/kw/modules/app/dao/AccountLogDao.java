package com.kw.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kw.modules.app.entity.AccountLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 资金记录表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Mapper
@Repository
public interface AccountLogDao extends BaseMapper<AccountLogEntity> {

    IPage<?> queryList(IPage<AccountLogEntity> page, @Param("params")Map<String, Object> params);
}
