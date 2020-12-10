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
 * @date 2020-04-22 23:09:51
 */
@Data
@TableName("kw_account_money")
public class AccountMoneyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private Long userId;
	/**
	 * 动力值
	 */
	private BigDecimal power;
	/**
	 * 母币
	 */
	private BigDecimal parentMoney;
	/**
	 * 子币
	 */
	private BigDecimal sonMoney;
	/**
	 * 动力源
	 */
	private BigDecimal powerSource;

	@TableField(exist = false)
	private String mobile;

	@TableField(exist = false)
	private BigDecimal powerp;

	private  BigDecimal frezenParentMoney;

}
