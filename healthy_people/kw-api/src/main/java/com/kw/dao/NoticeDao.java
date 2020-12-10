package com.kw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kw.entity.NoticeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 通告管理
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-03 18:03:32
 */
@Mapper
public interface NoticeDao extends BaseMapper<NoticeEntity> {

    IPage<?> queryList(IPage<NoticeEntity> page, @Param("params") Map<String, Object> params);
}
