package com.kw.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 交易表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Data
@TableName("kw_buy_trans")
public class BuyTransEntity implements Serializable {
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
	private BigDecimal amount;
	/**
	 * 售卖人id
	 */
	private Long sellerId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 支付方式(支付宝/usdt/银行卡)
	 */
	private Integer payType;
	/**
	 * 状态 (0待上传交易凭证1完成 2完成)
	 */
	private Integer status;


	private BigDecimal price;

	private BigDecimal  fee;



	private Integer settleStatus;

	private String photo;

	private Long purposeId;

	private Date buyerSubmitTime;
	private Date sellerSubmitTime;

	@TableField(exist = false)
	private String sellerMobile;
	@TableField(exist = false)
	private String buyerMobile;


}
