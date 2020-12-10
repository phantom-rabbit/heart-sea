package com.kw.modules.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.kw.common.exception.RRException;
import com.kw.common.sms.config.SmsConfig;
import com.kw.common.sms.config.SmsConstant;
import com.kw.common.sms.config.SmsFactory;
import com.kw.common.sms.config.SmsResult;
import com.kw.common.utils.RedisUtils;
import com.kw.common.utils.SpringContextUtils;
import com.kw.modules.app.entity.SmsLogEntity;
import com.kw.modules.app.service.SmsLogService;
import com.kw.modules.app.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("smsService")
public class SmsServiceImpl implements SmsService {

    @Autowired
    private SmsLogService smsLogService;

    /**
     * 发送短信
     * @param mobile
     * @param content
     * @return
     */
    public SmsResult sendSms(String mobile, String content) {
        RedisUtils redisUtils = SpringContextUtils.getBean("redisUtils", RedisUtils.class);
        Object configValue = redisUtils.get(SmsConstant.SMS_CONFIG_KEY);
        if(configValue == null){
            throw new RRException("短信配置错误");
        }
        SmsConfig config = JSON.parseObject(String.valueOf(configValue), SmsConfig.class);
        if(config == null){
            throw new RRException("短信配置错误");
        }
        SmsResult result = SmsFactory.build().notifySms(mobile, content);
        SmsLogEntity log = new SmsLogEntity();
        log.setContent(content);
        log.setCreateDate(new Date());
        log.setMobile(mobile);
        log.setType(config.getType());
        log.setReturnMsg(JSON.toJSONString(result.getResult()));
        log.setSuccess(result.isSuccessful()?1:0);
        smsLogService.save(log);

        return result;
    }
}
