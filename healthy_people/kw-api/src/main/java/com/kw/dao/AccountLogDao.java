package com.kw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kw.entity.AccountEntity;
import com.kw.entity.AccountLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    List<AccountLogEntity> queryListByUserId(Long userId);
    List<AccountLogEntity> queryListByPage(@Param("params") Map<String, Object> params);
}
