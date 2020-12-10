package com.kw.modules.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kw.modules.app.entity.LevelShareDetailEntity;
import com.kw.modules.app.entity.LevelShareEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 分红汇总表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-09 23:01:59
 */
@Mapper
@Repository
public interface LevelShareDao extends BaseMapper<LevelShareEntity> {
    IPage<?> queryList(IPage<LevelShareEntity> page, Map<String, Object> params);
}
