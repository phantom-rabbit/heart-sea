package com.kw.modules.app.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kw.modules.app.entity.LtcLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 矿机日志
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-02 11:25:25
 */
@Mapper
public interface LtcLogDao extends BaseMapper<LtcLogEntity> {

    IPage<LtcLogEntity> queryList(IPage<LtcLogEntity> page, @Param("params") Map<String, Object> params);

    List<HashMap> queryReportList(@Param("params") Map<String,Object> params);
}
