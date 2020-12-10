package com.kw.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.Constant;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;
import com.kw.common.utils.RedisUtils;
import com.kw.modules.app.dao.PurposeBuyDao;
import com.kw.modules.app.entity.PurposeBuyEntity;
import com.kw.modules.app.service.PurposeBuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;


@Service("purposeBuyService")
public class PurposeBuyServiceImpl extends ServiceImpl<PurposeBuyDao, PurposeBuyEntity> implements PurposeBuyService {

    @Autowired
    RedisUtils redisUtils;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {


        return new PageUtils(baseMapper.queryList(new Query<PurposeBuyEntity>().getPage(params), params));
    }

    @Override
    public int reduceHasBuy(Long purposeId, BigDecimal amount) {
       return  this.baseMapper.reduceHasBuy(purposeId,amount);
    }

}