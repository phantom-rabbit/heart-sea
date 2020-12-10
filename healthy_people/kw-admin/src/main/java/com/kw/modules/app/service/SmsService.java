package com.kw.modules.app.service;

import com.kw.common.sms.config.SmsResult;


public interface SmsService {
    /**
     * 发送短信
     * @param mobile
     * @param content
     * @return
     */
    SmsResult sendSms(String mobile, String content);
}
