package com.kw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kw.entity.VipConfigEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Vip配置表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Mapper
public interface VipConfigDao extends BaseMapper<VipConfigEntity> {

    VipConfigEntity getVipConfigAtAuthFinished();

    List<VipConfigEntity> queryAll();
}
