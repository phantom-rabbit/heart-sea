package com.kw.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
	 * 使用人id
	 */
	private Long endUser;
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

}
