package com.kw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kw.entity.UserLtcEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 用户矿机表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Mapper
public interface UserLtcDao extends BaseMapper<UserLtcEntity> {

    BigDecimal queryHyd(@Param("userId") Long userId,@Param("dl")  int dl);
    BigDecimal queryHydPerson(@Param("userId") Long userId,@Param("dl")  int dl);

    BigDecimal getNotActiveCodeTotalAmount(Long userId);

    int updateActiveCodeStatusByUserId(Long userId);

    int queryWorkingLtc(Long userId);
}
