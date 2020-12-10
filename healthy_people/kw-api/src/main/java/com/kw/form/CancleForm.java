package com.kw.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by Medivh on 2020/4/10.
 * 愿我出走半生,归来仍是少年
 */

@Data
@ApiModel(value = "取消求购")
public class CancleForm {

    private String purposeId;
}
