package com.kw.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.modules.app.entity.AccountMoneyEntity;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 账户表
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-22 23:09:51
 */
public interface AccountMoneyService extends IService<AccountMoneyEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addSonMoney( Long userId, BigDecimal amount);

    int addParentMoney( Long userId, BigDecimal amount);

    /**
     * 添加动力源 源是包括自己账户在内以下所有注册实名认证通过的账户数量，每认证通过一个加一个源
     * @param userIds
     * @param one
     */
    long addPowerSource(String[] userIds, BigDecimal one);
}

