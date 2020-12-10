package com.kw.common.sms.config;


import com.alibaba.fastjson.JSON;
import com.github.qcloudsms.SmsSingleSender;
import com.kw.common.exception.RRException;
import com.kw.common.sms.service.NotifyService;
import com.kw.common.utils.RedisUtils;
import com.kw.common.utils.SpringContextUtils;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * 短信通知
 */
public final class SmsFactory {



    public static NotifyService build(){
        RedisUtils redisUtils = SpringContextUtils.getBean("redisUtils", RedisUtils.class);
        Object configValue = redisUtils.get(SmsConstant.SMS_CONFIG_KEY);
        if(configValue == null){
            throw new RRException("短信配置错误");
        }
        SmsConfig config = JSON.parseObject(String.valueOf(configValue), SmsConfig.class);
        if(config == null){
            throw new RRException("短信配置错误");
        }
        if(config.getType() == SmsConstant.SmsType.MOVEK.getValue()){
            return  new NotifyService(movekSmsSender(config));
        }else if(config.getType() == SmsConstant.SmsType.ALIYUN.getValue()){
            return new NotifyService(aliyunSmsSender(config));
        }else if(config.getType() == SmsConstant.SmsType.QCLOUD.getValue()){
            return new NotifyService(tencentSmsSender(config));
        }else if(config.getType() == SmsConstant.SmsType.JUHE.getValue()){
            return new NotifyService(juheSmsSender(config));
        }
        return null;
    }

    private static TencentSmsSender tencentSmsSender(SmsConfig config) {
        TencentSmsSender smsSender = new TencentSmsSender();
        smsSender.setSender(new SmsSingleSender(config.getQcloudAppId(), config.getQcloudSecretKey()));
        smsSender.setSign(config.getSign());
        return smsSender;
    }

    private static AliyunSmsSender aliyunSmsSender(SmsConfig config) {
        AliyunSmsSender smsSender = new AliyunSmsSender();
        smsSender.setSign(config.getSign());
        smsSender.setRegionId(config.getAliyunRegionId());
        smsSender.setAccessKeyId(config.getAliyunAccessKeyId());
        smsSender.setAccessKeySecret(config.getAliyunAccessKeySecret());
        return smsSender;
    }


    public static MovekSmsSender movekSmsSender(SmsConfig config) {
        MovekSmsSender smsSender = new MovekSmsSender();
        smsSender.setSign(config.getSign());
        smsSender.setApiUrl(config.getMovekUrl());
        smsSender.setUid(config.getMovekUid());
        smsSender.setAccessKeyId(config.getMovekAccessKeyId());
        smsSender.setAccessKeySecret(config.getMovekAccessKeySecret());
        return smsSender;
    }


    public static JuheSmsSender juheSmsSender(SmsConfig config) {
        JuheSmsSender smsSender = new JuheSmsSender();
        smsSender.setSign(config.getSign());
        smsSender.setApiUrl(config.getJuheDomain());
        smsSender.setAccessKeySecret(config.getJuheSecretKey());
        smsSender.setTplId(config.getJuheTplId());
        return smsSender;
    }

}
