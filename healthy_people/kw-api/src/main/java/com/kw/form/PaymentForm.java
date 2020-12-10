package com.kw.form;

import com.kw.common.group.AlipayGroup;
import com.kw.common.group.BankGroup;
import com.kw.common.group.USDTGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录表单

 */
@Data
@ApiModel(value = "保存收款方式")
public class PaymentForm {
    @ApiModelProperty(value = "支付宝")
    @NotBlank(message="支付宝",groups = AlipayGroup.class)
    private String aliPayNumber;


    @ApiModelProperty(value = "USDT")
    @NotBlank(message="USDT",groups = USDTGroup.class)
    private String usdtToken;



    @ApiModelProperty(value = "bank")
    @NotBlank(message="银行不能为空",groups = BankGroup.class)
    private String bank;


    @ApiModelProperty(value = "branch")
    @NotBlank(message="分行不能为空",groups = BankGroup.class)
    private String branch;

    @ApiModelProperty(value = "branch")
    @NotBlank(message="银行卡号不能为空",groups = BankGroup.class)
    private String cardNo;



    @ApiModelProperty(value = "type")
    @NotBlank(message="支付种类不能为空")
    private String type;




}
