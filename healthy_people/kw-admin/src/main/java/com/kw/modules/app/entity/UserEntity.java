package com.kw.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 会员记录
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:15:46
 */
@Data
@TableName("kw_user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 支付密码
	 */
	private String payPassword;
	/**
	 * 登录时间
	 */
	private Date lastLoginTime;
	/**
	 * 登录IP
	 */
	private String lastLoginIp;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 注册时间
	 */
	private Date createDate;
	/**
	 * 注册IP
	 */
	private String createIp;
	/**
	 * 状态：0 启用 1 禁用
	 */
	private Integer status;

	/**
	 * 谷歌验证码密钥
	 */
	private String secretKey;

	/**
	 * 是否绑定谷歌验证码 0 否 1是
	 */
	private Integer bindAuthCode;

	/**
	 * 省
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;

}
