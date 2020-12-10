package com.kw.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * Vip配置表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Data
@TableName("kw_vip_config")
public class VipConfigEntity implements Serializable {
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
	/**
	 * 推荐人数
	 */
	private Integer inviteNumber;
	/**
	 * 邀请奖励OPC
	 */
	private BigDecimal awrdParent;
	/**
	 * 邀请奖励动力
	 */
	private BigDecimal awrdPower;
	/**
	 * 交易手续费
	 */
	private String transFee;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人id
	 */
	private Long createUserId;

}
