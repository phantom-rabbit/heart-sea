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
public class Award {
    @ApiModelProperty(value = "用户")
    @NotNull(message="userId不能为空")
    private Long userId;
    @ApiModelProperty(value = "奖励ltcid")
    @NotNull(message="矿机不能为空")
    private Long awardLtcId;

}
