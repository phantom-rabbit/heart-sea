package com.kw.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 短信记录
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 09:06:55
 */
@Data
@TableName("kw_sms_log")
public class SmsLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long id;
	/**
	 * 操作人
	 */
	private Long userId;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 返回消息
	 */
	private String returnMsg;
	/**
	 * 
	 */
	private Integer type;
	/**
	 * 
	 */
	private Integer success;

}
