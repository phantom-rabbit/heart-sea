package com.kw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;

import com.kw.dao.UserDaybillsDao;
import com.kw.entity.UserDaybillsEntity;
import com.kw.service.UserDaybillsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service("userDaybillsService")
public class UserDaybillsServiceImpl extends ServiceImpl<UserDaybillsDao, UserDaybillsEntity> implements UserDaybillsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserDaybillsEntity> page  = this.baseMapper.selectPage( new Query<UserDaybillsEntity>().getPage(params), new QueryWrapper<UserDaybillsEntity>());
        //判断是否是代理 代理userid 查询in

        return new PageUtils(page);
    }

    @Override
    public IPage<UserDaybillsEntity> selectPageVo(IPage<UserDaybillsEntity> page, Object[] ids) {
        return this.baseMapper.selectPageVo(page,ids);
    }

}