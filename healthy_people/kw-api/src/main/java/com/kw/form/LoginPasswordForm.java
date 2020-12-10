package com.kw.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "修改密码）")
public class LoginPasswordForm {

    @ApiModelProperty(value = "原密码")
    @NotBlank(message="原密码不能为空")
    private String oldPassword;

    @ApiModelProperty(value = "新密码")
    @NotBlank(message="新密码不能为空")
    @Length(min = 6, max=50, message = "支付密码长度至少6个字符")
    private String password;

//    @ApiModelProperty(value = "谷歌验证码")
//    @NotNull(message="谷歌验证码不能为空")
    Long code;

}
