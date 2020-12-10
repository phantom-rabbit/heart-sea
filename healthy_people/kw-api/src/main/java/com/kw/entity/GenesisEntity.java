package com.kw.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 合伙人
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-12 09:41:09
 */
@Data
@TableName("kw_genesis")
public class GenesisEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 创始人UserID
	 */
	@TableId
	private Long id;

	private Long userId;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 创世节点名称
	 */
	private String name;

	@TableField(exist = false)
	private String mobile;

}
