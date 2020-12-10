package com.kw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.entity.AccountEntity;
import com.kw.entity.AccountMoneyEntity;

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

    AccountMoneyEntity queryByUserId(Long id);
}

