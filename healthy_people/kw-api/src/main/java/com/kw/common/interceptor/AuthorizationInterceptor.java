package com.kw.common.interceptor;


import com.kw.common.annotation.Login;
import com.kw.common.exception.RRException;
import com.kw.common.utils.Constant;
import com.kw.common.utils.R;
import com.kw.common.utils.RedisUtils;
import com.kw.service.TokenService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限(Token)验证

 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RedisUtils redisUtils;

    public static final String USER_KEY = "userId";
    public static final String TOKEN = "token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Login annotation;
        if(handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
//            response.addHeader("Access-Control-Allow-Origin", "*");
//            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
//            response.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
//            response.addHeader("Access-Control-Max-Age", "3600");
        }else{
            return true;
        }

        if(annotation == null){
            return true;
        }


        Integer loginClosed = redisUtils.get(Constant.LOGIN_CLOSED, Integer.class);
        if (loginClosed != null && loginClosed == 1) {
            throw new RRException("抱歉，网站维护中，请您稍后再试");
        }


        //从header中获取token
        String token = request.getHeader("token");

        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = request.getParameter("token");
        }

        //token为空
        if(StringUtils.isBlank(token)){
            throw new RRException("token不能为空");
        }

        //查询token信息
        Long userId = tokenService.queryByToken(token);
        if(userId == null){
            throw new RRException("token失效，请重新登录", 401);
        }

        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(USER_KEY, userId);
        request.setAttribute(TOKEN, token);
        return true;
    }
}
