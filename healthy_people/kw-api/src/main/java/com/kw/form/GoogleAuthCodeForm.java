package com.kw.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "绑定谷歌验证码")
public class GoogleAuthCodeForm {

    @ApiModelProperty(value = "谷歌验证码")
    @NotNull(message="谷歌验证码不能为空")
    private Long code;


    @ApiModelProperty(value = "短信验证码")
    @NotBlank(message="短信验证码不能为空")
    private String captcha;
}
