package com.kw.common.sms.config;


import com.kw.common.sms.group.SmsAliyunGroup;
import com.kw.common.sms.group.SmsJuHeGroup;
import com.kw.common.sms.group.SmsMovekGroup;
import com.kw.common.sms.group.SmsQcloudGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 云存储配置信息

 */
@Data
public class SmsConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 类型 1：沃动  2：阿里云  3：腾讯 4:聚合
     */
    @Range(min=1, max=4, message = "类型错误")
    private Integer type;

    @NotNull(message = "是否启用")
    @Range(min=0, max=1, message = "请选择是否启用")
    private Integer enable;

    @NotBlank(message="签名不能为空")
    private String sign;
    @NotBlank(message="uid不能为空", groups = SmsMovekGroup.class)
    private String movekUid;
    @NotBlank(message="URL不可为空", groups = SmsMovekGroup.class)
    @URL(message = "URL格式错误", groups = SmsMovekGroup.class)
    private String movekUrl;
    @NotBlank(message="URL不可为空", groups = SmsMovekGroup.class)
    private String movekAccessKeyId;
    @NotBlank(message="URL不可为空", groups = SmsMovekGroup.class)
    private String movekAccessKeySecret;


    @NotBlank(message="所属地区不能为空", groups = SmsAliyunGroup.class)
    private String aliyunRegionId;
    @NotBlank(message="阿里云AccessKeyId不能为空", groups = SmsAliyunGroup.class)
    private String aliyunAccessKeyId;
    @NotBlank(message="阿里云AccessKeySecret不能为空", groups = SmsAliyunGroup.class)
    private String aliyunAccessKeySecret;

    @NotBlank(message="腾讯云绑定的域名不能为空", groups = SmsQcloudGroup.class)
    @URL(message = "腾讯云绑定的域名格式不正确", groups = SmsQcloudGroup.class)
    private String qcloudDomain;
    @NotNull(message="腾讯云AppId不能为空", groups = SmsQcloudGroup.class)
    private Integer qcloudAppId;
    @NotBlank(message="腾讯云SecretId不能为空", groups = SmsQcloudGroup.class)
    private String qcloudSecretId;
    @NotBlank(message="腾讯云SecretKey不能为空", groups = SmsQcloudGroup.class)
    private String qcloudSecretKey;
    @NotBlank(message="腾讯云BucketName不能为空", groups = SmsQcloudGroup.class)
    private String qcloudBucketName;
    @NotBlank(message="所属地区不能为空", groups = SmsQcloudGroup.class)
    private String qcloudRegion;



    @NotBlank(message="聚合短信域名不能为空", groups = SmsJuHeGroup.class)
    @URL(message = "聚合短信域名格式不正确", groups = SmsJuHeGroup.class)
    private String juheDomain;
    @NotNull(message="聚合APPKEY不能为空", groups = SmsJuHeGroup.class)
    private String juheSecretKey;
    @NotBlank(message="短信模板ID不能为空", groups = SmsJuHeGroup.class)
    private String juheTplId;
}

