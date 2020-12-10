package com.kw.form;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "申请战队")
public class ApplyTeamCaptainForm {

    @ApiModelProperty(value = "战队名称")
    @NotBlank(message="战队名称不能为空")
    private String name;

    @ApiModelProperty(value = "战队微信")
    @NotBlank(message="战队微信不能为空")
    private String wechat;

    @ApiModelProperty(value = "战队口号")
    @NotBlank(message="战队口号不能为空")
    private String slogan;
}
