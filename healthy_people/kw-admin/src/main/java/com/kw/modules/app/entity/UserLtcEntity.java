package com.kw.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户矿机表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Data
@TableName("kw_user_ltc")
@NoArgsConstructor
public class UserLtcEntity implements Serializable {



	public UserLtcEntity(Long userId,LtcConfigEntity ltcConfigEntity, String remark){
		this.name = ltcConfigEntity.getName();
		this.parentPrice = ltcConfigEntity.getParentPrice();
		this.incomeParentPerday = ltcConfigEntity.getIncomeParentPerday();
		this.days = ltcConfigEntity.getDays();
		this.maxHold = ltcConfigEntity.getMaxHold();
		this.incomeSonPerday = ltcConfigEntity.getIncomeSonPerday();
		this.power = ltcConfigEntity.getPower();
		this.ltcStatus = 0;
		this.settleStatus = 1;
		this.ltcId = ltcConfigEntity.getId();
		this.createTime = new Date();
		this.userId = userId;
		this.ltcImg = ltcConfigEntity.getLtcImg();
		this.remark = remark;

	}


	private static final long serialVersionUID = 1L;
	private Long userId;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 
	 */
	@TableId
	private Long id;
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
	/**
	 * 最大持有量
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
	 * 矿机状态0待启动 1启动中
	 */
	private Integer ltcStatus;
	/**
	 * OPC采集状态 (需要用户采集动作)
	 */
	private Integer settleStatus;
	/**
	 * 矿机id
	 */
	private Integer ltcId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 启动时间
	 */
	private Date startTime;

	private Integer  runDays;
	/**
	 * 是否已领取激活码
	 */
	private Integer isActiveCode;

	private Integer isAward;
	/**
	 * 备注
	 */
	private String remark;

	@TableField(exist = false)
	private String ltcImg;

}
