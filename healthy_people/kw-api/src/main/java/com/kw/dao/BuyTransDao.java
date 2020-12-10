package com.kw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kw.entity.BuyTransEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 交易表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Mapper
@Repository
public interface BuyTransDao extends BaseMapper<BuyTransEntity> {

    BigDecimal selectTransMoney(@Param("yesterday") String yesterday,@Param("now")String now);

    int countByUserId(@Param("buyerId") Long buyerId,@Param("lastMonth")  String date,@Param("now") String date1);

    List<BuyTransEntity> queryListByStatus();
}
