package com.kw.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
	private Integer isShow;
	/**
	 * 更新时间
	 */
	private Date updateDate;


	/**
	 * 分类
	 */
	private Long typeId;
}
