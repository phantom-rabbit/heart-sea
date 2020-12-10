package com.kw.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 求购记录
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Data
@TableName("kw_purpose_buy")
public class PurposeBuyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 挂买人id
	 */
	private Long buyerId;
	/**
	 * 挂买总量
	 */
	private BigDecimal total;
	/**
	 * 起量
	 */
	private BigDecimal buyStart;
	/**
	 * 已购买
	 */
	private BigDecimal hasBuy;
	/**
	 * 支付方式(支付宝/usdt/银行卡)
	 */
	private Integer payType;
	/**
	 * 预购买(别人点击确定 我尚未付款前 金额增加最红 , pre_buy+has_buy<=total)
	 */
	private BigDecimal preBuy;
	/**
	 *
	 */
	private Integer status;
	/**
	 *
	 */
	private Date createTime;


	private BigDecimal price;
	@TableField(exist = false)
	private  String mobile;

}
