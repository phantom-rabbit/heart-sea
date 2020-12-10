package com.kw.modules.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kw.modules.app.entity.LevelShareDetailEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 分红表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-09 23:01:59
 */
@Mapper
@Repository
public interface LevelShareDetailDao extends BaseMapper<LevelShareDetailEntity> {

    List<LevelShareDetailEntity> gatherList();

    boolean updateByStatusAndUserId(@Param("status") int status,@Param("userId") Long userId);

    IPage<?> queryList(IPage<LevelShareDetailEntity> page, Map<String, Object> params);
}
