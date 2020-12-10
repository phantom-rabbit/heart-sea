package com.kw.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 战队配置表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-12 20:34:11
 */
@Data
@TableName("kw_team_config")
public class TeamConfigEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 战队名
	 */
	private String team;
	/**
	 * 战队领导人id
	 */
	private Long leaderId;

	/**
	 * 战队领导人级别名字
	 */
	@TableField(exist = false)
	private String leaderLevelName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人
	 */
	private Long createUserId;
	/**
	 * 微信
	 */
	private String teamWechat;
	/**
	 * 口号
	 */
	private String slogan;
	/**
	 * 审核状态： -1 失败 0 待审核 1 通过
	 */
	private Integer status;

	@TableField(exist = false)
	private int teamCount = 0;


	@TableField(exist = false)
	private BigDecimal zhyd = BigDecimal.ZERO;
}
