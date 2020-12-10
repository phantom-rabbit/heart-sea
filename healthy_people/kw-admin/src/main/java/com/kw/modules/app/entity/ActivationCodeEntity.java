package com.kw.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Data
@TableName("kw_activation_code")
public class ActivationCodeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 激活码
	 */
	private String activationCode;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 使用人
	 */
	private Integer endUser;
	/**
	 * 添加时间
	 */
	private Date createTime;
	/**
	 * 使用时间
	 */
	private Date useTime;
	/**
	 * 1.系统发放2.购买生成
	 */
	private Integer codeType;

	/**
	 * 拥有者id
	 */
	private Long userId;
	/**
	 * 使用者手机号
	 */
	private String endUserMobile;
	/**
	 * 拥有者手机号
	 */
	private String mobile;

}
