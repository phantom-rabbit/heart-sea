package com.kw.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 登录表单

 */
@Data
@ApiModel(value = "发布交易")
public class SellForm {
    @ApiModelProperty(value = "id")
    @NotBlank(message="purposeId不能为空")
    String purposeId;

    @ApiModelProperty(value = "金额")
    @NotBlank(message="金额不能为空")
    String amount;

    @ApiModelProperty(value = "谷歌验证码")
//    @NotNull(message="谷验歌证码不能为空")
    Long code;


    @ApiModelProperty(value = "支付密码")
    @NotBlank(message="支付密码不能为空")
    String paypassword;



}
