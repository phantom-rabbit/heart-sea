package com.kw.common.sms.config;

import com.kw.common.utils.RedisKeys;

public interface SmsConstant {
    /**
     * 短信配置KEY
     */
    String SMS_CONFIG_KEY2 = "SMS_CONFIG_KEY";
    String SMS_CONFIG_KEY = RedisKeys.getSysConfigKey(SMS_CONFIG_KEY2);


    /**
     * 短信服务商
     */
    enum SmsType {
        /**
         * 沃动
         */
        MOVEK(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3),
        /**
         * 聚合
         */
        JUHE(4);

        private int value;

        SmsType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


    /**
     * 阿里 验证码模版
     */
    String SMS_CAPTCHA_ALI_TEMPLATE_ID = "SMS_187934882";

    /**
     * 阿里 交易模版SMS_189033736
     */
    String SMS_TRADE_ALI_TEMPLATE_ID = "SMS_189033736";
}
