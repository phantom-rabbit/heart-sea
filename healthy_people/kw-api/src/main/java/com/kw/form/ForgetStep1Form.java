package com.kw.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "忘记密码第一步短信验证")
public class ForgetStep1Form {

    @ApiModelProperty(value = "手机号")
    @NotBlank(message="手机号不能为空")
    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "手机号码格式错误")
    private String mobile;

    @ApiModelProperty(value = "短信验证码")
    @NotBlank(message="短信验证码不能为空")
    private String code;

    @ApiModelProperty(value = "图形验证码")
    @NotBlank(message="图形验证码不能为空")
    private String captcha;

    @ApiModelProperty(value = "UUID")
    @NotBlank(message="UUID参数错误")
    private String uuid;
}
