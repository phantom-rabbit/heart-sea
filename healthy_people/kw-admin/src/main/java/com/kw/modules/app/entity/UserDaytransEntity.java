package com.kw.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户日买卖
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-12 08:07:04
 */
@Data
@TableName("kw_user_daytrans")
public class UserDaytransEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 日期
	 */
	private String dateTime;
	/**
	 * 购买金额
	 */
	private BigDecimal buyAmount;
	/**
	 * 出售金额
	 */
	private BigDecimal sellAmount;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * date_time+"_"+user_id
	 */
	@TableId
	private String dateKey;
	/**
	 * 手续费
	 */
	private BigDecimal fee;


	@TableField(exist = false)
	private String mobile;

}
