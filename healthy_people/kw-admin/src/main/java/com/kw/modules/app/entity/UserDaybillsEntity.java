package com.kw.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户日业绩
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-11 18:29:17
 */
@Data
@TableName("kw_user_daybills")
public class UserDaybillsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String dateTime;
	/**
	 * 
	 */
	private BigDecimal power;
	/**
	 * 
	 */
	private BigDecimal powerSource;
	/**
	 * 
	 */
	private Long userId;

	@TableField(exist = false)
	private String mobile;
	/**
	 * date_time+"_"+user_id
	 */
	@TableId
	private String dateKey;




}
