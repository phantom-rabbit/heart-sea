package com.kw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;
import com.kw.dao.VipConfigDao;
import com.kw.entity.VipConfigEntity;
import com.kw.service.VipConfigService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("vipConfigService")
public class VipConfigServiceImpl extends ServiceImpl<VipConfigDao, VipConfigEntity> implements VipConfigService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<VipConfigEntity> page = this.page(
                new Query<VipConfigEntity>().getPage(params),
                new QueryWrapper<VipConfigEntity>()
        );

        return new PageUtils(page);
    }


    @Override
    public VipConfigEntity getVipConfigAtAuthFinished() {
        return baseMapper.getVipConfigAtAuthFinished();
    }


    @Override
    public List<VipConfigEntity> queryAll() {
        return baseMapper.queryAll();
    }
}