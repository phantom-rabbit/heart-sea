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

import com.kw.modules.app.dao.UserDaytransDao;
import com.kw.modules.app.entity.UserDaytransEntity;
import com.kw.modules.app.service.UserDaytransService;


@Service("userDaytransService")
public class UserDaytransServiceImpl extends ServiceImpl<UserDaytransDao, UserDaytransEntity> implements UserDaytransService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserDaytransEntity> page = this.baseMapper.queryPage(
                new Query<UserDaytransEntity>().getPage(params),
              params
        );

        return new PageUtils(page);
    }

    @Override
    public List<HashMap> queryReportList(Map<String, Object> params) {
        return this.baseMapper.queryReportList(params);
    }

}