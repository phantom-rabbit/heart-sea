package com.kw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kw.entity.ProxyConfigEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 代理表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Mapper
public interface ProxyConfigDao extends BaseMapper<ProxyConfigEntity> {
	
}
