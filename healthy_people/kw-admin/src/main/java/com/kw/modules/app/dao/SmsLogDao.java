package com.kw.modules.app.dao;

import com.kw.modules.app.entity.SmsLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 短信记录
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 09:06:55
 */
@Mapper
public interface SmsLogDao extends BaseMapper<SmsLogEntity> {
	
}
