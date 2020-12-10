package com.kw.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.modules.app.entity.PurposeBuyEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
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

    int reduceHasBuy(@Param("id") Long purposeId, @Param("amount") BigDecimal amount);
}

