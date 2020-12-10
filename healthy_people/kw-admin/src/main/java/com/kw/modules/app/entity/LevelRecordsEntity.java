package com.kw.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 级别升级审核
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-21 03:21:00
 */
@Data
@TableName("kw_level_records")
public class LevelRecordsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;
	/**
	 * 升级人
	 */
	private Long userId;
	/**
	 * 当前等级
	 */
	private Long levelId;
	/**
	 * 直推人数
	 */
	private Integer inviterCount;
	/**
	 * 团队动力值
	 */
	private Long power;
	/**
	 * 团队动力源
	 */
	private Long powerSource;
	/**
	 * 直推好友同等级数
	 */
	private Integer eqLevelCount;
	/**
	 * 
	 */
	private Date updateTime;
	/**
	 * 大区动力值
	 */
	private Long dqPower;
	/**
	 * 大区动力源
	 */
	private Long dqPowerSource;
	/**
	 * 审核状态 -1 失败 0 待审核 1 成功
	 */
	private Integer status;

	@TableField(exist = false)
	private String levelName;
	@TableField(exist = false)
	private String mobile;


}
