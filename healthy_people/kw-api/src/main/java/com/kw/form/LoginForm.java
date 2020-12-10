package com.kw.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录表单

 */
@Data
@ApiModel(value = "登录表单")
public class LoginForm {
    @ApiModelProperty(value = "手机号")
    @NotBlank(message="手机号不能为空")
    private String mobile;

    @ApiModelProperty(value = "密码")
    @NotBlank(message="密码不能为空")
    private String password;

    @ApiModelProperty(value = "图形验证码")
    @NotBlank(message="图形验证码不能为空")
    private String captcha;


    @ApiModelProperty(value = "uuid")
    @NotBlank(message="图形验证码已失效")
    private String uuid;

}
