package com.kw.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "售卖人确认交易")
public class TransConfirmForm {

    @ApiModelProperty(value = "id")
    @NotNull(message="transId不能为空")
    Long transId;

    @ApiModelProperty(value = "支付密码")
    @NotBlank(message="支付密码不能为空")
    String paypassword;

}
