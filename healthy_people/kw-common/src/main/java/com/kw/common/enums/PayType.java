package com.kw.common.enums;

import org.apache.commons.lang.StringUtils;

/**
 * Created by Medivh on 2020/4/3.
 * 愿我出走半生,归来仍是少年
 */
public enum  PayType {


    ALIPAY("支付宝", "1"),
    USDT("USDT", "2"),
    BANK("银行卡", "3");

    private String name;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    PayType(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static  String getNamByValue(String code){
        for(LogType logTypes : LogType.values()){
            if(StringUtils.equals(code,(logTypes.getValue()+""))){
                return logTypes.getName();
            }
        }
        return null;
    }
}
