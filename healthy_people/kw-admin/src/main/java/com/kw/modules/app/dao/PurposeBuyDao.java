package com.kw.modules.app.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kw.modules.app.entity.PurposeBuyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 求购记录
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Mapper
@Repository
public interface PurposeBuyDao extends BaseMapper<PurposeBuyEntity> {

    int reduceHasBuy(@Param("id") Long purposeId,@Param("amount") BigDecimal amount);

    IPage<?> queryList(IPage<PurposeBuyEntity> page,@Param("params")  Map<String, Object> params);


}
