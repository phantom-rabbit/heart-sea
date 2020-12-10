package com.kw.modules.app.dao;

import com.kw.modules.app.entity.LtcConfigEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 矿机配置表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Mapper
public interface LtcConfigDao extends BaseMapper<LtcConfigEntity> {
	
}
