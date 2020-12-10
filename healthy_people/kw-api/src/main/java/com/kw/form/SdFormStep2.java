package com.kw.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by Medivh on 2020/4/9.
 * 愿我出走半生,归来仍是少年
 */
@Data
public class SdFormStep2 {


    @ApiModelProperty(value = "凭证")
    @NotBlank(message="凭证不能为空")
    private String photo;

}
