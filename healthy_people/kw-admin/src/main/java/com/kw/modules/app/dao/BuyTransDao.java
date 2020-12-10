package com.kw.modules.app.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kw.modules.app.entity.BuyTransEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kw.modules.app.entity.LevelShareEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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

    IPage<?> queryList(IPage<BuyTransEntity> page, @Param("params") Map<String, Object> params);
}
