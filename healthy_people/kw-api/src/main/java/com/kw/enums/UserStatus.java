package com.kw.enums;

public enum UserStatus {

    normal(0), reject(1);

    UserStatus(int value){
        this.value = value;
    }
    private Integer value;

    public Integer getValue() {
        return value;
    }
}
