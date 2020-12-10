package com.kw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kw.entity.PurposeBuyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

/**
 * 求购记录
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Mapper
public interface PurposeBuyDao extends BaseMapper<PurposeBuyEntity> {

    int preSell(@Param("purposeId") Long purposeId,@Param("amount") String amount);

    BigDecimal sumPrebuy();

    HashMap selectMinMax();

    HashMap selectMinMaxUSDT();

    Integer countByUserId(@Param("buyerId") Long buyerId, @Param("lastMonth") String start,@Param("now") String end);


    int reduceHasBuy(@Param("id") Long purposeId, @Param("amount") BigDecimal amount);
}
