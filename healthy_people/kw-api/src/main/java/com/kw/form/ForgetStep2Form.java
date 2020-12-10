package com.kw.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "忘记密码第二步短信验证")
public class ForgetStep2Form {
    @ApiModelProperty(value = "谷歌验证码")
    private Long code;

    @ApiModelProperty(value = "新密码")
    @NotBlank(message="新密码不能为空")
    private String password;

    @ApiModelProperty(value = "UUID")
    @NotBlank(message="UUID参数错误")
    private String uuid;
}
