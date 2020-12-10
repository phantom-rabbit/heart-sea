package com.kw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kw.entity.TeamConfigEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 战队配置表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Mapper
public interface TeamConfigDao extends BaseMapper<TeamConfigEntity> {
    TeamConfigEntity queryByUserId(Long userId);
}
