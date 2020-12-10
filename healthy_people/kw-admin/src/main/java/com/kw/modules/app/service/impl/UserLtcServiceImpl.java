package com.kw.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.Constant;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;
import com.kw.common.utils.RedisUtils;
import com.kw.modules.app.dao.UserLtcDao;
import com.kw.modules.app.entity.UserLtcEntity;
import com.kw.modules.app.service.UserLtcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("userLtcService")
public class UserLtcServiceImpl extends ServiceImpl<UserLtcDao, UserLtcEntity> implements UserLtcService {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        return new PageUtils(this.baseMapper.queryList(new Query<UserLtcEntity>().getPage(params), params));
    }

    @Override
    public BigDecimal queryHyd(Long userId) {
        int dl =  redisUtils.get(Constant.IS_DL,int.class);

        return baseMapper.queryHyd(userId,dl);
    }


    @Override
    public BigDecimal queryMaxHydFromSon(Long userId, BigDecimal configHyd) {
        int dl =  redisUtils.get(Constant.IS_DL,int.class);
        return baseMapper.queryMaxHydFromSon(userId,dl, configHyd);
    }

    @Override
    public List<HashMap> queryReportList(Map<String, Object> params) {
        return baseMapper.queryReportList(params);
    }
}