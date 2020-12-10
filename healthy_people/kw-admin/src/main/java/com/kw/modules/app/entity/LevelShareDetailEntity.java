package com.kw.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 分红表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-09 23:01:59
 */
@Data
@TableName("kw_level_share_detail")
public class LevelShareDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 账户id
	 */
	private Long userId;
	/**
	 * 分红金额
	 */
	private BigDecimal shareMoney;
	/**
	 * 世代金额
	 */
	private BigDecimal shareSdMoney;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 交易id
	 */
	private Long transId;
	/**
	 * 汇总标计
	 */
	private Integer gatherStatus;

	@TableField(exist = false)
	private String mobile;

}
