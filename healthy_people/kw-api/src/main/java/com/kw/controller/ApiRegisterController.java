package com.kw.controller;

import com.kw.common.exception.RRException;
import com.kw.common.sms.config.SmsConstant;
import com.kw.common.sms.config.SmsResult;
import com.kw.common.utils.*;
import com.kw.common.validator.ValidatorUtils;
import com.kw.entity.ActivationCodeEntity;
import com.kw.form.RegisterForm;
import com.kw.form.RegisterSmsForm;
import com.kw.service.ActivationCodeService;
import com.kw.service.SmsService;
import com.kw.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 注册接口
 */
@RestController
@RequestMapping("/api")
@Api(tags = "注册接口")
public class ApiRegisterController {
    @Autowired
    private UserService userService;

    @Autowired
    private SmsService smsService;
    @Autowired
    private ActivationCodeService activationCodeService;
    @Autowired
    private RedisUtils redisUtils;

    @PostMapping("register")
    @ApiOperation("注册")
    public R register(@RequestBody RegisterForm form, HttpServletRequest request) {
        //表单校验
        ValidatorUtils.validateEntity(form);
        Integer loginClosed = redisUtils.get(Constant.LOGIN_CLOSED, Integer.class);
        if (loginClosed != null && loginClosed == 1) {
            return R.ok("抱歉，网站维护中，请您稍后再试");
        }
        return R.ok("注册成功").put("token", userService.register(form, IPUtils.getIpAddr(request)));
    }

    @RequestMapping("register/sms")
    @ApiOperation("注册短信")
    public R registerSms(@RequestBody RegisterSmsForm form) {
        //表单校验
        ValidatorUtils.validateEntity(form);
        String val = redisUtils.get(form.getMobile() + "_SMS");
        if (StringUtils.isNotBlank(val)) {
            return R.error("请不要频繁发送短信");
        }
        //防止重复刷短信
        redisUtils.set(form.getMobile() + "_SMS", true, 60);

        ActivationCodeEntity activationCode = activationCodeService.queryByCode(form.getCode());
        if (activationCode == null || activationCode.getEndUser() != null) {
            redisUtils.delete(form.getMobile() + "_SMS");
            throw new RRException("抱歉，你的推荐码已效");
        }

        //验证码时效
        if(activationCode.getUserId() > 0){
            Integer days = redisUtils.get(Constant.ACTIVATION_CODE_VALID_TIME, Integer.class);
            if(DateUtils.addDateDays(activationCode.getCreateTime(),days).before(new Date())){
                throw new RRException("抱歉，你的推荐码已失效");
            }
        }

        String code = CharUtil.getRandomNum(6);
        //发送短信
        SmsResult result = smsService.sendWithTemplate(form.getMobile(), SmsConstant.SMS_CAPTCHA_ALI_TEMPLATE_ID, new String[]{code});
        if(!result.isSuccessful()){
            return R.error("发送失败");
        }
        //缓存
        redisUtils.set(form.getMobile() + form.getCode(), code, 600);
        return R.ok("发送成功");
    }


}
