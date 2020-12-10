package com.kw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;
import com.kw.dao.SmsLogDao;
import com.kw.entity.SmsLogEntity;
import com.kw.service.SmsLogService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("smsLogService")
public class SmsLogServiceImpl extends ServiceImpl<SmsLogDao, SmsLogEntity> implements SmsLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SmsLogEntity> page = this.page(
                new Query<SmsLogEntity>().getPage(params),
                new QueryWrapper<SmsLogEntity>()
        );

        return new PageUtils(page);
    }

}