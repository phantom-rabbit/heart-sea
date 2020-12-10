package com.kw.modules.app.service.impl;

import com.kw.modules.app.entity.LevelConfiigAwrdLtcEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;

import com.kw.modules.app.dao.UserDaybillsDao;
import com.kw.modules.app.entity.UserDaybillsEntity;
import com.kw.modules.app.service.UserDaybillsService;


@Service("userDaybillsService")
public class UserDaybillsServiceImpl extends ServiceImpl<UserDaybillsDao, UserDaybillsEntity> implements UserDaybillsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserDaybillsEntity> page  = this.baseMapper.selectPage( new Query<UserDaybillsEntity>().getPage(params), new QueryWrapper<UserDaybillsEntity>());
        //判断是否是代理 代理userid 查询in

        return new PageUtils(page);
    }

}