package com.kw.common.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Created by Medivh on 2020/4/1.
 * 愿我出走半生,归来仍是少年
 */
public enum LogType{

    LTC_INCOME("矿机收益", "ltc_income","in"),
    LTC_AWARD("发放", "ltc_award","in"),
    LTC_BUY("购买矿机", "ltc_buy","out"),
    DECUTE_BACK("后台扣除", "decute_manager","out"),
    LTC_BUY_POWER("购买矿机获得动力", "ltc_buy_power","in"),
    LTC_BUY_AWARD_MONEY("下游购买矿机获得奖励", "ltc_buy_award_money","in"),
    SHARE_SD("市代分红", "share_sd","in"),
    SHARE_SD_SON("市代MAC分红", "share_sd_son","in"),
    USER_AUTH_SD("下级实名奖励市代", "user_auth_sd","in"),
    SHARE("OPC分红", "share","in"),
    OPC_SELL("卖出OPC", "opc_sell","out"),
    OPC_SELL_FREZEN("出售冻结", "opc_sell_frezen","frezen"),
    OPC_SELL_FREZEN_TRANS("出售冻结手续费", "opc_sell_frezen_trans","frezen_out"),
    OPC_SEL_DEDUCT("出售解冻扣除", "opc_sel_deduct","out"),
    OPC_SEL_DEDUCT_FEETRANS("出售手续费解冻扣除", "opc_sel_deduct_fee","out"),
    OPC_BUY("购买OPC", "opc_buy","in"),
    TRANS_CANCLE("后台取消交易", "ltc_manager_canel","frezen_in"),
    INVITE_POWER_SOURE("获得动力源", "auth_get_powersoure","in"),
    INVITE_PARENT_MONEY("获得邀请OPC奖励", "auth_get_money","in");

    private String name;
    private String value;
    private String flow;

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    LogType(String name, String value,String flow) {
        this.name = name;
        this.value = value;
        this.flow = flow;
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


    public static Map<String,String> getMap(){
        Map<String,String> result = Maps.newHashMap();
        for(LogType item : LogType.values()){
            result.put(item.getValue(), item.flow);
        }
        return result;
    }
}

