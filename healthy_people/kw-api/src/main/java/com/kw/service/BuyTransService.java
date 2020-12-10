package com.kw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;
import com.kw.entity.BuyTransEntity;
import com.kw.entity.PurposeBuyEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
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

    R sell(Long id, PurposeBuyEntity purposeBuyEntity, String amount, String yesterday, String now);

    R confirm(Long transId, Long userId);

    BigDecimal selectTransMoney(String yesterday, String now);


    List<BuyTransEntity> queryListByStatus();
    void cancle(Long id);
}

