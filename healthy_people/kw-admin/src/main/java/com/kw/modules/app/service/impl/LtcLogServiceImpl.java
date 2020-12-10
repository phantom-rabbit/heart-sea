package com.kw.modules.app.service.impl;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;

import com.kw.modules.app.dao.LtcLogDao;
import com.kw.modules.app.entity.LtcLogEntity;
import com.kw.modules.app.service.LtcLogService;


@Service("ltcLogService")
public class LtcLogServiceImpl extends ServiceImpl<LtcLogDao, LtcLogEntity> implements LtcLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        return new PageUtils(baseMapper.queryList(new Query<LtcLogEntity>().getPage(params), params));
    }

    @Override
    public List<HashMap> queryReportList(Map<String, Object> params) {
        return baseMapper.queryReportList(params);
    }

}