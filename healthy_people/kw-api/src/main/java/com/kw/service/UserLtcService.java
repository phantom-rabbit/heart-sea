package com.kw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.entity.LtcLogEntity;
import com.kw.entity.UserLtcEntity;

import java.math.BigDecimal;
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

    BigDecimal queryHydPerson(Long userId);

    /**
     * 获取没有分配激活码的购买消费总额
     * @param userId
     * @return
     */
    BigDecimal getNotActiveCodeTotalAmount(Long userId);

    int updateActiveCodeStatusByUserId(Long userId);

    int queryWorkingLtc(Long userId);

    void start(LtcLogEntity ltcLog, UserLtcEntity userLtcEntity);
}

