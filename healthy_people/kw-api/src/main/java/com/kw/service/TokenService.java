package com.kw.service;

/**
 * 会员Token

 */
public interface TokenService  {

	Long queryByToken(String token);

	/**
	 * 生成token
	 * @param userId  用户ID
	 * @return        返回token信息
	 */
	String createToken(long userId);

	/**
	 * 设置token过期
	 * @param userId 用户ID
	 */
	void expireToken(String token, long userId);

}
