package com.kw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.entity.BanksEntity;


import java.util.List;
import java.util.Map;

/**
 * 银行表
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-06 21:58:43
 */
public interface BanksService extends IService<BanksEntity> {

    PageUtils queryPage(Map<String, Object> params);

    public List<BanksEntity> list();
}

