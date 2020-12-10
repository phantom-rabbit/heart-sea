package com.kw.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 银行表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-06 21:58:43
 */
@Data
@TableName("kw_banks")
public class BanksEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String text;
	/**
	 * 
	 */
	private String value;

}
