package com.kw.modules.app.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by Medivh on 2020/5/2.
 * 愿我出走半生,归来仍是少年
 */
@Data
public class OperaAccount {
    @ApiModelProperty(value = "用户")
    @NotNull(message="userId不能为空")
    private Long userId;
    @ApiModelProperty(value = "金额")
    @NotBlank(message="金额不能为空")
    private String money;

    @ApiModelProperty(value = "数量")
    @NotNull(message="数量不能为空")
    private Integer ct;
    @ApiModelProperty(value = "支付种类")
    @NotBlank(message="支付种类不能为空")
    private String payType;
}
