package com.kw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;
import com.kw.entity.PurposeBuyEntity;
import com.kw.form.PurposeForm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 求购记录
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
public interface PurposeBuyService extends IService<PurposeBuyEntity> {

    PageUtils queryPage(Map<String, Object> params);

    int preSell(Long purposeId, String amount);
    /**
     *
     * 挂单量

     *
     * */
    BigDecimal sumPrebuy();

    HashMap selectMinMax();

    R publish(Long id, PurposeForm purposeForm);

    Integer countByUserId(Long buyerId, String start, String end) ;

    void cancle(PurposeBuyEntity purposeBuyEntity);

    int reduceHasBuy(Long purposeId, BigDecimal amount);
}

