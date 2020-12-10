package com.kw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.entity.UserEntity;
import com.kw.form.LoginForm;
import com.kw.form.RegisterForm;
import com.kw.form.UserAuthForm;

/**
 * 用户

 */
public interface UserService extends IService<UserEntity> {

	UserEntity queryByMobile(String mobile);

	/**
	 * 用户登录
	 * @param form    登录表单
	 * @return        返回登录信息
	 */
	String login(LoginForm form, String ip);


	String register(RegisterForm form, String ip);

	/**
	 * 实名认证
	 * @param userId
	 * @param form
	 * @return
	 */
    boolean userAuth(Long userId, UserAuthForm form);
}
