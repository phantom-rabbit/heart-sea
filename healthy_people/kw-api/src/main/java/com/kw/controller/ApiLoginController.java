package com.kw.controller;


import com.google.code.kaptcha.Producer;
import com.kw.common.annotation.Login;
import com.kw.common.exception.RRException;
import com.kw.common.utils.Constant;
import com.kw.common.utils.IPUtils;
import com.kw.common.utils.R;
import com.kw.common.utils.RedisUtils;
import com.kw.common.validator.ValidatorUtils;
import com.kw.form.LoginForm;
import com.kw.service.TokenService;
import com.kw.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 登录接口
 */
@RestController
@RequestMapping("/api")
@Api(tags = "登录接口")
public class ApiLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private Producer producer;
    @Autowired
    private RedisUtils redisUtils;

    @PostMapping("login")
    @ApiOperation("登录")
    public R login(@RequestBody LoginForm form, HttpServletRequest request) {
        //表单校验
        ValidatorUtils.validateEntity(form);
        Integer loginClosed = redisUtils.get(Constant.LOGIN_CLOSED, Integer.class);
        if (loginClosed != null && loginClosed == 1) {
            return R.ok("抱歉，网站维护中，请您稍后再试");
        }
        return R.ok("登录成功").put("token", userService.login(form, IPUtils.getIpAddr(request)));
    }

    @Login
    @PostMapping("logout")
    @ApiOperation("退出")
    public R logout(@ApiIgnore @RequestAttribute("userId") long userId, @RequestAttribute("token") String token) {
        tokenService.expireToken(token, userId);
        return R.ok();
    }


    /**
     * 图形验证码
     */
    @GetMapping("captcha")
    public void captcha(HttpServletResponse response, String uuid) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        if (StringUtils.isBlank(uuid)) {
            throw new RRException("uuid不能为空");
        }
        //生成文字验证码
        String code = producer.createText();

        //缓存5分钟
        redisUtils.set(uuid, code, 300);
        BufferedImage image = producer.createImage(code);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }
}
