package com.kw.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 级别配置
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Data
@TableName("kw_level_confiig")
public class LevelConfiigEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 名称
	 */
	private String name;


	private Integer isDirect;
	/**
	 * 邀请人数要求
	 */
	private Integer inviteCount;
	/**
	 * 团队动力要求
	 */
	private BigDecimal teamPower;
	/**
	 * 下级人数要求
	 */
	private Integer lowerCount;
	/**
	 * 实名要求
	 */
	private Integer isReal;
	/**
	 * 交易加权分红(同级)
	 */
	private BigDecimal transShare;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人
	 */
	private Long createUserId;
	/**
	 * 等级顺序-由低到高
	 */
	private Long orderNum;

}
