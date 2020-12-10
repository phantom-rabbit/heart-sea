package com.kw.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 通告管理
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-03 18:03:32
 */
@Data
@TableName("kw_notice")
public class NoticeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 是否显示
	 */
	@TableField("is_show")
	private Integer isShow;
	/**
	 * 更新时间
	 */
	private Date updateDate;


	private Long typeId;

}
