package com.kw.modules.app.controller;

import com.google.gson.Gson;
import com.kw.common.sms.config.SmsConfig;
import com.kw.common.sms.config.SmsConstant;
import com.kw.common.sms.config.SmsForm;
import com.kw.common.sms.config.SmsResult;
import com.kw.common.sms.group.SmsAliyunGroup;
import com.kw.common.sms.group.SmsJuHeGroup;
import com.kw.common.sms.group.SmsMovekGroup;
import com.kw.common.sms.group.SmsQcloudGroup;
import com.kw.common.sysconfig.service.SysConfigService;
import com.kw.common.utils.R;
import com.kw.common.validator.ValidatorUtils;
import com.kw.modules.app.service.SmsService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信通知
 */
@RestController
@RequestMapping("app/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 短信配置信息
     */
    @RequestMapping("/config")
    @RequiresPermissions("app:sms:config")
    public R config() {
        SmsConfig config = sysConfigService.getConfigObject(SmsConstant.SMS_CONFIG_KEY, SmsConfig.class);
        return R.ok().put("config", config);
    }


    /**
     * 保存云存储配置信息
     */
    @RequestMapping("/saveConfig")
    @RequiresPermissions("app:sms:config")
    public R saveConfig(@RequestBody SmsConfig config) {
        //校验类型
        ValidatorUtils.validateEntity(config);

        if (config.getType() == SmsConstant.SmsType.MOVEK.getValue()) {
            //校验数据
            ValidatorUtils.validateEntity(config, SmsMovekGroup.class);
        } else if (config.getType() == SmsConstant.SmsType.ALIYUN.getValue()) {
            //校验阿里云数据
            ValidatorUtils.validateEntity(config, SmsAliyunGroup.class);
        } else if (config.getType() == SmsConstant.SmsType.QCLOUD.getValue()) {
            //校验腾讯云数据
            ValidatorUtils.validateEntity(config, SmsQcloudGroup.class);
        }else if (config.getType() == SmsConstant.SmsType.JUHE.getValue()) {
            //校验腾讯云数据
            ValidatorUtils.validateEntity(config, SmsJuHeGroup.class);
        }
        sysConfigService.updateValueByKey(SmsConstant.SMS_CONFIG_KEY2, new Gson().toJson(config));

        return R.ok();
    }

    /**
     * 发送短信测试
     * @return
     * @throws Exception
     */
    @RequestMapping("/send")
    @RequiresPermissions("app:sms:send")
    public R upload(@RequestBody SmsForm form) throws Exception {
        ValidatorUtils.validateEntity(form);
        SmsResult result = smsService.sendSms(form.getMobile(), form.getContent());
        return R.ok().put("data", result );
    }

    /**
     * 删除
     */
//    @RequestMapping("/delete")
//    @RequiresPermissions("sys:oss:all")
//    public R delete(@RequestBody Long[] ids) {
//        smsLogService.removeByIds(Arrays.asList(ids));
//        return R.ok();
//    }
}
