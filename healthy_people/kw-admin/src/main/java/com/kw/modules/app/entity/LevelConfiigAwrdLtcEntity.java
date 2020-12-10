package com.kw.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 级别配置奖励
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 15:30:54
 */
@Data
@TableName("kw_level_confiig_awrd_ltc")
public class LevelConfiigAwrdLtcEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 配置id
	 */
	private Integer configId;
	/**
	 * 奖励矿机id
	 */
	private Integer awardLtcId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * id
	 */
	@TableId
	private Integer id;


	@TableField(exist = false)
	private String configName;

	@TableField(exist = false)
	private String ltcName;

}
