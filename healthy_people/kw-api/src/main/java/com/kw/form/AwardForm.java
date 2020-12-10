package com.kw.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Medivh on 2020/4/20.
 * 愿我出走半生,归来仍是少年
 */
@Data
public class AwardForm {


    @ApiModelProperty(value = "被发放人Id")
    @NotNull(message="被发放人Id不能为空")
    private Long awardUserId;

    @ApiModelProperty(value = "发放")
    @NotNull(message="发放数量不能为空")
    private Long num;


}
