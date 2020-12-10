package com.kw.common.sms.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kw.common.utils.JuheUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

public class JuheSmsSender implements SmsSender {
    private final Log logger = LogFactory.getLog(JuheSmsSender.class);
    private String sign;
    private String apiUrl;
    private String accessKeySecret;
    private String tplId;
    private String tpValue;



    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getTplId() {
        return tplId;
    }

    public void setTplId(String tplId) {
        this.tplId = tplId;
    }

    public String getTpValue() {
        return tpValue;
    }

    public void setTpValue(String tpValue) {
        this.tpValue = tpValue;
    }

    @Override
    public SmsResult send(String phone, String content) {
        SmsResult result = new SmsResult();
        try {
            Map params = new HashMap();//请求参数
            params.put("mobile",phone);//接收短信的手机号码
            params.put("tpl_id",tplId);//短信模板ID，请参考个人中心短信模板设置
            params.put("tpl_value",tpValue);//变量名和变量值对。如果你的变量名或者变量值中带有#&=中的任意一个特殊符号，请先分别进行urlencode编码后再传递，<a href="http://www.juhe.cn/news/index/id/50" target="_blank">详细说明></a>
            params.put("key", accessKeySecret);//应用APPKEY(应用详细页查询)
            params.put("dtype","");//返回数据的格式,xml或json，默认json


            try {
                String res = JuheUtils.net(apiUrl, params, "GET");
                JSONObject object = JSON.parseObject(res);
                if(object.getInteger("error_code")==0){
                    result.setResult(object.get("result"));
                    result.setSuccessful(true);
                }else{
                    result.setResult(object.getString("reason"));
                    result.setSuccessful(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            logger.error("juhe发送短信失败", e);
        }

        logger.info(JSON.toJSONString(result));
        return result;
    }

    @Override
    public SmsResult sendWithTemplate(String phone, String templateId, String[] params) {
        String content = String.format(templateId, params);
        return send(phone, content);
    }
}
