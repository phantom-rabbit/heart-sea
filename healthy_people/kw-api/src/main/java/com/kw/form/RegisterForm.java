package com.kw.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


/**
 * 注册表单

 */
@Data
@ApiModel(value = "注册表单")
public class RegisterForm {
    @ApiModelProperty(value = "手机号")
    @NotBlank(message="手机号不能为空")
    private String mobile;

    @ApiModelProperty(value = "密码")
    @NotBlank(message="密码不能为空")
    @Length(min = 6, max=50, message = "登录密码长度至少6个字符")
    private String password;

    @ApiModelProperty(value = "短信验证码")
    @NotBlank(message="短信验证码不能为空")
    private String captcha;

    @ApiModelProperty(value = "激活码")
    @NotBlank(message="激活码不能为空")
    private String code;

    @ApiModelProperty(value = "支付密码")
    @NotBlank(message="支付密码不能为空")
    @Length(min = 6, max=50, message = "支付密码长度至少6个字符")
    private String payPassword;
}
