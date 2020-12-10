package com.kw.modules.app.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;

import com.kw.modules.app.dao.SmsLogDao;
import com.kw.modules.app.entity.SmsLogEntity;
import com.kw.modules.app.service.SmsLogService;


@Service("smsLogService")
public class SmsLogServiceImpl extends ServiceImpl<SmsLogDao, SmsLogEntity> implements SmsLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SmsLogEntity> page = this.page(
                new Query<SmsLogEntity>().getPage(params),
                new QueryWrapper<SmsLogEntity>().orderByDesc("id")
        );

        return new PageUtils(page);
    }

}