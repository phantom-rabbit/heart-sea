package com.kw.modules.app.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;

import com.kw.modules.app.dao.ProxyConfigDao;
import com.kw.modules.app.entity.ProxyConfigEntity;
import com.kw.modules.app.service.ProxyConfigService;


@Service("proxyConfigService")
public class ProxyConfigServiceImpl extends ServiceImpl<ProxyConfigDao, ProxyConfigEntity> implements ProxyConfigService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProxyConfigEntity> page = this.page(
                new Query<ProxyConfigEntity>().getPage(params),
                new QueryWrapper<ProxyConfigEntity>()
        );

        return new PageUtils(page);
    }

}