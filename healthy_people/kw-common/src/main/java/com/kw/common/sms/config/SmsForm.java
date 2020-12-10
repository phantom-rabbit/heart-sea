package com.kw.common.sms.config;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SmsForm {
    @NotBlank(message = "手机号码不能为空")
    private String mobile;
    @NotBlank(message = "短信内容不能为空")
    private String content;
}
