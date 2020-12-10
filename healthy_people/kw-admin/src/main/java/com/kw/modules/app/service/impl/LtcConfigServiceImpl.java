package com.kw.modules.app.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;

import com.kw.modules.app.dao.LtcConfigDao;
import com.kw.modules.app.entity.LtcConfigEntity;
import com.kw.modules.app.service.LtcConfigService;


@Service("ltcConfigService")
public class LtcConfigServiceImpl extends ServiceImpl<LtcConfigDao, LtcConfigEntity> implements LtcConfigService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LtcConfigEntity> page = this.page(
                new Query<LtcConfigEntity>().getPage(params),
                new QueryWrapper<LtcConfigEntity>()
        );

        return new PageUtils(page);
    }


}