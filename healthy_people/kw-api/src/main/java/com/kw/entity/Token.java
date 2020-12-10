package com.kw.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;



/**
 * 用户Token

 */
@Data

public class Token implements Serializable {
	private static final long serialVersionUID = 1L;


	private Long userId;
	private String token;
	/**
	 * 过期时间
	 */
	private Date expireTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;

}
