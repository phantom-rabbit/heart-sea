package com.kw.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.kw.common.exception.RRException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;


/**
 * 身份证实名认证调用示例代码 － 聚合数据
 * 在线接口文档：http://www.juhe.cn/docs/103
 **/
@Slf4j
public class JuheUtils {
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";


    public static boolean auth(String appkey, String realname, String idcard) {
        Map<String, String> params = Maps.newHashMap();
        try {
            String url = String.format("http://op.juhe.cn/idcard/query?idcard=%s&realname=%s&orderid=&key=%s", idcard, URLEncoder.encode(realname, "utf-8"), appkey);
            String res = net(url, params, "get");
            JSONObject data = JSON.parseObject(res, JSONObject.class);
            if (data.getInteger("error_code") != 0) {
                log.error("实名认证服务器连接失败:{}", data.getString("reason"));
                throw new RRException("请联系客服，实名认证服务器连接失败");
            }
            if (data.getJSONObject("result").getInteger("res") == 1) {
                return true;
            }
        } catch (Exception e) {
            throw new RRException(e.getMessage());
        }
        return false;
    }


    //1.手机归属地查询
    public static JSONObject getMobileInfo(String appkey, String mobile) {
        String result = null;
        String url = String.format("http://apis.juhe.cn/mobile/get?phone=%s&dtype=json&key=%s", mobile, appkey);
        log.info(url);
        try {
            result = net(url, Maps.newHashMap(), "get");
            JSONObject object = JSON.parseObject(result, JSONObject.class);
            if (object.getInteger("error_code") == 0) {
                System.out.println(object.get("result"));
                return object.getJSONObject("result");
            } else {
                log.error("手机号码归属地查询失败：" + object.get("error_code") + ":" + object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("系统错误");
        }
        return null;
    }


    /**
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return 网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params, String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if (method == null || method.equals("GET")) {
                strUrl = strUrl + "?" + urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (method == null || method.equals("GET")) {
                conn.setRequestMethod("GET");
            } else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params != null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //将map型转为请求参数型
    public static String urlencode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}