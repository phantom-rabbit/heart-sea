package com.kw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.entity.LtcLogEntity;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 矿机日志
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-02 11:25:25
 */
public interface LtcLogService extends IService<LtcLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询今天可领取的收益总额
     * @param userId
     * @return
     */
    BigDecimal findLtcIncomeTotal(Long userId);

    /**
     * 领取矿机收益
     * @param userId
     */
    void income(Long userId);
}

