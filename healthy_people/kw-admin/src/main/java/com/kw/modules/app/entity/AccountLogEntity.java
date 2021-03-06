package com.kw.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 资金记录表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Data
@TableName("kw_account_log")
@NoArgsConstructor
public class AccountLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	public AccountLogEntity(Long userId, BigDecimal account, BigDecimal money, String mark, String logType, Integer moneyKind){
		this.userId = userId;
		this.mark = mark;
		this.money = money;
		this.account = account;
		this.logType = logType;
		this.moneyKind = moneyKind;
	}
	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 用户id
	 */
	private Long userId;


	/**
	 * 用户id
	 */
	private Integer moneyKind;

	/**
	 * 记录种类
	 */
	private String logType;


	private String mobile;
	/**
	 * 备注
	 */
	private String mark;
	/**
	 * 操作金额
	 */
	private BigDecimal money;
	/**
	 * 操作后金额
	 */
	private BigDecimal account;
	/**
	 * 
	 */
	private Date createTime;

}
