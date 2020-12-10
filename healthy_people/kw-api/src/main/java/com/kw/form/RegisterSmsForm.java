package com.kw.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


/**
 * 注册表单

 */
@Data
@ApiModel(value = "注册短信表单")
public class RegisterSmsForm {
    @ApiModelProperty(value = "手机号")
    @NotBlank(message="手机号不能为空")
    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "手机号码格式错误")
    private String mobile;

    @ApiModelProperty(value = "激活码")
    @NotBlank(message="激活码不能为空")
    private String code;

}
