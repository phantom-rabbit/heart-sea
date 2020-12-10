package com.kw.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "用户认证")
public class UserAuthForm {

    @ApiModelProperty(value = "姓名")
    @NotBlank(message="姓名不能为空")
    private String realName;

    @ApiModelProperty(value = "身份证号码")
    @NotBlank(message="身份证号码不能为空")
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$", message = "身份证格式错误")
    private String idCard;
}
