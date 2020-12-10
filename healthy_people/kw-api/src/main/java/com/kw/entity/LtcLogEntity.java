package com.kw.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 矿机日志
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-02 11:25:25
 */
@Data
@TableName("kw_ltc_log")
public class LtcLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 会员ID
	 */
	private Long userId;
	/**
	 * 矿机ID
	 */
	private Long ltcId;
	/**
	 * 收益
	 */
	private BigDecimal income;
	/**
	 * 状态： 0 待领取  1 已领取   2 已作废
	 */
	private Integer status;
	/**
	 * 产生日期
	 */
	private Date createDate;
	/**
	 * 领取时间
	 */
	private Date incomeTime;

	@TableField(exist = false)
	private String mobile;


	/**
	 * 收益（MAC）
	 */
	private BigDecimal incomeCoin;
}
