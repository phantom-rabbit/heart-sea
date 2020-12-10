package com.kw.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录表单

 */
@Data
@ApiModel(value = "发布交易")
public class PurposeForm {
    @ApiModelProperty(value = "支付种类")
    @NotBlank(message="支付种类不能为空")
    String payType;

    @ApiModelProperty(value = "支付种类")
    @NotBlank(message="总金额不能为空")
    String total;

    @ApiModelProperty(value = "起付金额")
    @NotBlank(message="起付金额不能为空")
    String buyStart;

    @ApiModelProperty(value = "价格")
    @NotBlank(message="价格不能为空")
    String price;




    @ApiModelProperty(value = "支付密码")
    @NotBlank(message="支付密码不能为空")
    String paypassword;
}
