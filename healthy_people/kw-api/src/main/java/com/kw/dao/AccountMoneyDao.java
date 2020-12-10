package com.kw.dao;

import com.kw.entity.AccountEntity;
import com.kw.entity.AccountMoneyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 账户表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-22 23:09:51
 */
@Mapper
public interface AccountMoneyDao extends BaseMapper<AccountMoneyEntity> {

    AccountMoneyEntity queryByUserId(@Param("userId") Long id);
}
