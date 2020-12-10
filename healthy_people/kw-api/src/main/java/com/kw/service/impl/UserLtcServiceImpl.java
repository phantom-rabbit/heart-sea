package com.kw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.Constant;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;
import com.kw.common.utils.RedisUtils;
import com.kw.dao.UserLtcDao;
import com.kw.entity.LtcConfigEntity;
import com.kw.entity.LtcLogEntity;
import com.kw.entity.UserLtcEntity;
import com.kw.service.LtcConfigService;
import com.kw.service.LtcLogService;
import com.kw.service.UserLtcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;


@Service("userLtcService")
public class UserLtcServiceImpl extends ServiceImpl<UserLtcDao, UserLtcEntity> implements UserLtcService {

    @Autowired
    LtcConfigService ltcConfigService;
    @Autowired
    LtcLogService ltcLogService;

    @Autowired
    RedisUtils redisUtils;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper queryWrapper = null;

        if("2".equals(params.get("type"))){
            queryWrapper=  new QueryWrapper<UserLtcEntity>().eq("user_id",params.get("user_id")).in("ltc_status",0,1).orderByAsc("parent_price");
        }
        if("3".equals(params.get("type"))){
            queryWrapper=  new QueryWrapper<UserLtcEntity>().eq("user_id",params.get("user_id")).eq("ltc_status",-1).orderByAsc("parent_price");
        }
        IPage<UserLtcEntity> page = this.page(
                new Query<UserLtcEntity>().getPage(params),
                queryWrapper);
        for(UserLtcEntity ltcEntity:page.getRecords()){
            LtcConfigEntity configEntity = ltcConfigService.getById(ltcEntity.getLtcId());
            ltcEntity.setLtcImg(configEntity.getLtcImg());
        }

        return new PageUtils(page);
    }



    @Override
    public BigDecimal queryHyd(Long userId) {
        int dl =  redisUtils.get(Constant.IS_DL,int.class);

        return baseMapper.queryHyd(userId,dl);
    }

    @Override
    public BigDecimal queryHydPerson(Long userId) {

        int dl =  redisUtils.get(Constant.IS_DL,int.class);
        return baseMapper.queryHydPerson(userId,dl);
    }


    @Override
    public BigDecimal getNotActiveCodeTotalAmount(Long userId) {
        return baseMapper.getNotActiveCodeTotalAmount(userId);
    }

    @Override
    public int updateActiveCodeStatusByUserId(Long userId) {
        return baseMapper.updateActiveCodeStatusByUserId(userId);
    }

    @Override
    public int queryWorkingLtc(Long userId) {
        return baseMapper.queryWorkingLtc(userId);
    }

    @Override
    public void start(LtcLogEntity ltcLog, UserLtcEntity userLtcEntity) {
         ltcLogService.save(ltcLog);
         updateById(userLtcEntity);

    }
}