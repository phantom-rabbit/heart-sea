package com.kw.modules.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户战队层级关系
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Data
@TableName("kw_user_team")
public class UserTeamEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@TableId(type= IdType.INPUT)
	private Long userId;
	/**
	 * 邀请人ID
	 */
	private Integer inviterUserId;
	/**
	 * 树结构如(user_id=6 inviter_id=7,mark=,1,6,7,) 可以清晰反应战队里所有人的id
	 */
	private String mark;



	/**
	 * 
	 */
	private Date addtime;

}
