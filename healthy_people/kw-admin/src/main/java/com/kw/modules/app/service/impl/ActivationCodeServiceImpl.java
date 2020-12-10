package com.kw.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;
import com.kw.modules.app.dao.ActivationCodeDao;
import com.kw.modules.app.entity.ActivationCodeEntity;
import com.kw.modules.app.service.ActivationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("activationCodeService")
public class ActivationCodeServiceImpl extends ServiceImpl<ActivationCodeDao, ActivationCodeEntity> implements ActivationCodeService {
    @Autowired
    ActivationCodeDao activationCodeDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        return new PageUtils(baseMapper.queryList(new Query<ActivationCodeEntity>().getPage(params), params));
    }


    @Override
    public ActivationCodeEntity findByInvitedUserId(Long userId) {
        return baseMapper.findByInvitedUserId(userId);
    }

    @Override
    public List<ActivationCodeEntity> queryAll(Long userId) {
        return baseMapper.queryAll(userId);
    }

    @Override
    public ActivationCodeEntity queryByCode(String code) {
        return baseMapper.queryByCode(code);
    }
}