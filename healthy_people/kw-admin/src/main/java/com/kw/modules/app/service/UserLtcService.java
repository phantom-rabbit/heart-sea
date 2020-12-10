package com.kw.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.modules.app.entity.UserLtcEntity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户矿机表
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
public interface UserLtcService extends IService<UserLtcEntity> {

    PageUtils queryPage(Map<String, Object> params);

    BigDecimal queryHyd(Long userId);
    /**
     * 查询userId所有直推好友部门满足的，部门就是直推好友的团队
     *
     * @param userId
     * @param configHyd 配置值
     * @return
     */
    BigDecimal queryMaxHydFromSon(Long userId, BigDecimal configHyd);

    List<HashMap> queryReportList(Map<String,Object> params);
}

