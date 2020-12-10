package com.kw.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 矿机配置表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Data
@TableName("kw_ltc_config")
public class LtcConfigEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	private String name;
	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * OPC数量
	 */
	private BigDecimal parentPrice;
	/**
	 * OPC每日收益
	 */
	private BigDecimal incomeParentPerday;
	/**
	 * 日期
	 */
	private Integer days;

	//0禁用 1启用
	private Integer configStatus;

	/**
	 * 总持有
	 */
	private Integer maxHold;
	/**
	 * MAC每日收益
	 */
	private BigDecimal incomeSonPerday;
	/**
	 * 动力值
	 */
	private BigDecimal power;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Long createUserId;


	private String ltcImg;



}
