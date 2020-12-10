package com.kw.modules.app.dao;

import com.kw.modules.app.entity.NoticeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 通告管理
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-03 18:03:32
 */
@Mapper
public interface NoticeDao extends BaseMapper<NoticeEntity> {
	
}
