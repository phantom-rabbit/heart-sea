package com.kw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;
import com.kw.dao.NoticeDao;
import com.kw.entity.NoticeEntity;
import com.kw.service.NoticeService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("noticeService")
public class NoticeServiceImpl extends ServiceImpl<NoticeDao, NoticeEntity> implements NoticeService {



    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        return new PageUtils(baseMapper.queryList(new Query<NoticeEntity>().getPage(params), params));
    }



}