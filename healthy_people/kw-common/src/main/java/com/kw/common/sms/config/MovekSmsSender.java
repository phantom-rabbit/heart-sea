package com.kw.common.sms.config;

import com.alibaba.fastjson.JSON;
import com.kw.common.utils.HttpUtil;
import com.kw.common.utils.XmlUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MovekSmsSender implements SmsSender {
    private final Log logger = LogFactory.getLog(MovekSmsSender.class);
    private String sign;
    private String apiUrl;
    private String uid;
    private String accessKeyId;
    private String accessKeySecret;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

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

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    @Override
    public SmsResult send(String phone, String content) {
        SmsResult result = new SmsResult();
        try {
            StringBuffer param = new StringBuffer(getApiUrl());
            param.append("?action=send&userid=").append(getUid());
            param.append("&account=").append(getAccessKeyId());
            param.append("&password=").append(getAccessKeySecret());
            param.append("&content=").append(URLEncoder.encode(getSign() + content, "UTF-8"));
            param.append("&mobile=").append(phone);
            String responseStr = HttpUtil.sendGet(param.toString());
            Map<String, Object> res;
            if (StringUtils.isNotBlank(responseStr)) {
                res = XmlUtil.xmlStrToMap(responseStr);
            } else {
                res = new HashMap<>();
            }
            if ("Success".equals(res.get("returnstatus"))) {
                result.setSuccessful(true);
                result.setResult(res);
            } else {
                result.setSuccessful(false);
                result.setResult(res);
            }
        } catch (Exception e) {
            logger.error("Movek发送短信失败", e);
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
