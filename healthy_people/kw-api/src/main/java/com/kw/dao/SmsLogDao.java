package com.kw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kw.entity.SmsLogEntity;
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
