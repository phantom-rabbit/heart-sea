package com.kw.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 战队配置表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Data
@TableName("kw_team_config")
public class TeamConfigEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 战队名
	 */
	private String team;
	/**
	 * 战队领导人id
	 */
	private Integer leaderId;
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
	/**
	 * 战队领导人手机号
	 */
	@TableField(exist = false)
	private String leaderMobile;
	/**
	 * 创建人手机号
	 */
	@TableField(exist = false)
	private String createrMobile;

}
