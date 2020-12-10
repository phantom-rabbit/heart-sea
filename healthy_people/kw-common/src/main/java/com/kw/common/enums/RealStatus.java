package com.kw.common.enums;

/**
 * 认证状态
 */
public enum RealStatus {
    /**
     * 未提交
     */
    NONE(0),
    /**
     * 待审核
     */
    WAIT_AUTH(1),
    /**
     * 审核成功
     */
    SUCCESS(2),
    /**
     * 审核失败
     */
    FAIL(-1);

    private Integer value;
    RealStatus(Integer value) {
       this.value = value;
    }

    public Integer getValue() {
        return value;
    }}
