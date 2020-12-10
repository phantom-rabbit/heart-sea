package com.kw.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
	@JsonIgnore
	private String password;
	/**
	 * 密码
	 */
	@JsonIgnore
	private String payPassword;
	/**
	 * 登录时间
	 */
	@JsonIgnore
	private Date lastLoginTime;
	/**
	 * 登录IP
	 */
	@JsonIgnore
	private String lastLoginIp;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 注册时间
	 */
	@JsonIgnore
	private Date createDate;
	/**
	 * 注册IP
	 */
	@JsonIgnore
	private String createIp;
	/**
	 * 状态：0 启用 1 禁用
	 */
	private Integer status;


	/**
	 * 谷歌验证码密钥
	 */
	@JsonIgnore
	private String secretKey;


	/**
	 * 是否绑定谷歌验证码
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
