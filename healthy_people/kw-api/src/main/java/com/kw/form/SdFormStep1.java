package com.kw.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by Medivh on 2020/4/9.
 * 愿我出走半生,归来仍是少年
 */
@Data
public class SdFormStep1 {

    @ApiModelProperty(value = "省市")
    @NotBlank(message="省市不能为空")
    private String provinceCity;




}
