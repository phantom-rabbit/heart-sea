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
@ApiModel(value = "图片上传确认")
public class UploadPhotoForm {
    @ApiModelProperty(value = "id")
    @NotNull(message="transId不能为空")
    Long transId;

    @ApiModelProperty(value = "图片")
    @NotBlank(message="图片不能为空")
    String photo;

//    @ApiModelProperty(value = "谷歌验证码")
//    @NotNull(message="谷歌验证码不能为空")
//    Long code;
}
