package com.kw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;

import com.kw.dao.UserDaytransDao;
import com.kw.entity.UserDaytransEntity;
import com.kw.service.UserDaytransService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("userDaytransService")
public class UserDaytransServiceImpl extends ServiceImpl<UserDaytransDao, UserDaytransEntity> implements UserDaytransService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserDaytransEntity> page = this.baseMapper.selectPage(
                new Query<UserDaytransEntity>().getPage(params),
                new QueryWrapper<UserDaytransEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public IPage<UserDaytransEntity> selectPageVo(IPage<UserDaytransEntity> page, Object[] ids) {
        return this.baseMapper.selectPageVo(page,ids);
    }

}