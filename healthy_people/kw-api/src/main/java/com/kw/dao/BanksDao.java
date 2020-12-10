package com.kw.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kw.entity.BanksEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 银行表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-06 21:58:43
 */
@Mapper
public interface BanksDao extends BaseMapper<BanksEntity> {
	
}
