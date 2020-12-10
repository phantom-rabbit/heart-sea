package com.kw.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 分红汇总表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-09 23:01:59
 */
@Data
@TableName("kw_level_share")
public class LevelShareEntity implements Serializable {
	private static final long serialVersionUID = 1L;

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
	 * 分红金额
	 */
	private BigDecimal shareMoney;



	/**
	 * 分红金额
	 */
	private BigDecimal shareSonMoney;




	/**
	 * 
	 */
	private BigDecimal shareSdMoney;
	/**
	 * 产生时间
	 */
	private Date createDate;
	/**
	 * 分红状态
	 */
	private Integer shareStatus;


	@TableField(exist = false)
	private  String mobile;

}
