package com.kw.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 级别配置
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-21 02:21:26
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
	/**
	 * 邀请人数要求
	 */
	private Integer inviteCount;
	/**
	 * 团队动力值要求
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
	private String createUserId;
	/**
	 * 下级直推要求
	 */
	private Integer isDirect;
	/**
	 * 
	 */
	private String imgSrc;
	/**
	 * 等级顺序-越大级别越高
	 */
	private Integer orderNum;
	/**
	 * 是否限制任意一小区动力值达标（小区即直推好友的团队）
	 */
	private Integer isLowerAnyTeam;
	/**
	 * 任意一小区动力值限制
	 */
	private BigDecimal lowerAnyTeamPower;
	/**
	 * 动力源
	 */
	private BigDecimal teamPowerSource;

}
