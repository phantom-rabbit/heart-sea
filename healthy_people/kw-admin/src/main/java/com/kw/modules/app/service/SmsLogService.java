package com.kw.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.modules.app.entity.SmsLogEntity;

import java.util.Map;

/**
 * 短信记录
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 09:06:55
 */
public interface SmsLogService extends IService<SmsLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

