package com.kw.common.enums;

import org.apache.commons.lang.StringUtils;

/**
 * Created by Medivh on 2020/4/3.
 * 愿我出走半生,归来仍是少年
 */
public enum BuyTransStatus {


    STATUS0("待上传照片", 0),
    STATUS1("待卖家确认", 1),
    STATUS2("交易完成", 2);

    private String name;
    private Integer value;

    BuyTransStatus(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
