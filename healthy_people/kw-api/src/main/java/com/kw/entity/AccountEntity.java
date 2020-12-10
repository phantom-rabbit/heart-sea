package com.kw.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

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
//	/**
//	 * 动力
//	 */
//	private BigDecimal power;
//	/**
//	 * OPC
//	 */
//	private BigDecimal parentMoney;
//	/**
//	 * MAC
//	 */
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
	private Long teamId;
	/**
	 * 
	 */
	private Long userId;

	/**
	 *
	 */
	private Long levelId;


	private Integer alipayStatus;

	private Integer bankStatus;

	private Integer usdtStatus;

	@TableField(exist = false)
	private int createwordStatus;
	private Integer sdStatus;


	private String sdProvinceCity;


	private String sdPhoto;


	@TableField(exist = false)
	private String vipName;

	@TableField(exist = false)
	private String levelName;

	/**
	 * 动力值
	 */
	@TableField(exist = false)
	private BigDecimal hyd;


	/**
	 * 工作中的ltc
	 */
	@TableField(exist = false)
	private int workingLtc;


	@TableField(exist = false)
	private String transFee;


//	private BigDecimal powerSource;

	@TableField(exist = false)
	private String mobile;
	//战友数
	@TableField(exist = false)
	private int directUserCount;


	/**
	 * 动力值
	 */
	@TableField(exist = false)
	private BigDecimal power;
	/**
	 * 母币
	 */
	@TableField(exist = false)
	private BigDecimal parentMoney;
	/**
	 * 子币
	 */
	@TableField(exist = false)
	private BigDecimal sonMoney;
	/**
	 * 动力源
	 */
	@TableField(exist = false)
	private BigDecimal powerSource;



}
