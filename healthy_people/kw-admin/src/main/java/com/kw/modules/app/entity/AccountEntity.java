package com.kw.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 账户表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Data
@TableName("kw_account")
public class AccountEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 名字
	 */
	private String realName;
	/**
	 * 身份证
	 */
	private String idCard;
	/**
	 * 认证状态
	 */
	private Integer realStatus;
	/**
	 * 动力
	 */
//	private BigDecimal power;
	/**
	 * OPC
	 */
//	private BigDecimal parentMoney;
	/**
	 * MAC
	 */
//	private BigDecimal sonMoney;
	/**
	 * 
	 */
	private String usdtToken;
	/**
	 * 支付宝账号
	 */
	private String alipayNumber;
	/**
	 * 银行
	 */
	private String bank;
	/**
	 * 分行
	 */
	private String branch;
	/**
	 * 卡号
	 */
	private String bankCardno;
	/**
	 * vip_id
	 */
	private Long vipId;
	/**
	 * 代理id
	 */
	private Integer proxyId;
//	/**
//	 * 冻结中金额
//	 */
//	private BigDecimal frezenParentMoney;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 战队id
	 */
	private Integer teamId;
	/**
	 * 
	 */
	private Long userId;

	/**
	 * 用户级别
	 */
	private Long levelId;



	private Integer sdStatus;


	private String sdProvinceCity;


	private String sdPhoto;


//	private BigDecimal powerSource;


	private Integer createwordStatus;

	@TableField(exist = false)
	private String mobile;


	@TableField(exist = false)
	private String levelName;


	@TableField(exist = false)
	private String vipName;

}
