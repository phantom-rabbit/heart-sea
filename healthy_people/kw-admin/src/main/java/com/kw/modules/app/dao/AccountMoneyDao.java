package com.kw.modules.app.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kw.modules.app.entity.AccountMoneyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 账户表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-22 23:09:51
 */
@Mapper
public interface AccountMoneyDao extends BaseMapper<AccountMoneyEntity> {

    IPage<?> queryList(IPage<AccountMoneyEntity> page, @Param("params") Map<String, Object> params);


    int addParentMoney(@Param("userId") Long userId,@Param("amount") BigDecimal amount);

    long addPowerSource(@Param("userIds") String[] userIds, @Param("source") BigDecimal source);

    void addSonMoney(@Param("userId") Long userId,@Param("amount")  BigDecimal amount);
}
