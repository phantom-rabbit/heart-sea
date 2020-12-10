package com.kw.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;
import com.kw.modules.app.entity.BuyTransEntity;

import java.util.List;
import java.util.Map;

/**
 * 交易表
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
public interface BuyTransService extends IService<BuyTransEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void cancle(Integer id);

    R confirm(Long id);
}

