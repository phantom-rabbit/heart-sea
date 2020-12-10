package com.kw.common.enums;

/**
 * 0待选择城市1待确认 2待打款上传 3.成功
 * 认证状态
 */
public enum SdStatus {
    /**
     * 未提交
     */
    NONE(0),
    /**
     * 待确认
     */
    WAIT_VERIFY1(1),
    /**
     * 待打款上传
     */
    WAIT_USDT(2),

    /**
     * 待打款上传
     */
    WAIT_VERITY2(3),

    /**
     * 待打款上传
     */
    SUCCESS(4),
    /**
     * 审核失败
     */
    FAIL(-1);

    private Integer value;
    SdStatus(Integer value) {
       this.value = value;
    }

    public Integer getValue() {
        return value;
    }}
