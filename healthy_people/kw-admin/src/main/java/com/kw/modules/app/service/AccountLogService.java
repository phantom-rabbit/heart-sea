package com.kw.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.modules.app.entity.AccountLogEntity;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 资金记录表
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
public interface AccountLogService extends IService<AccountLogEntity> {

    /**
     * 购买矿机
     * @param userId    用户id
     * @param parentPrice    金额
     * @param mark    标记
     * @param type    见com.kw.common.enums.LogType
     * @param kind    1OPC2MAC3动力

     */
    void logAccount(Long userId, BigDecimal parentPrice, String mark, String type, Integer kind);

    PageUtils queryPage(Map<String, Object> params);
}

