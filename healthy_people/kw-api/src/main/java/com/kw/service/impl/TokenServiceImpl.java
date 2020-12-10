package com.kw.service.impl;

import com.kw.common.utils.RedisUtils;
import com.kw.service.TokenService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service("tokenService")
public class TokenServiceImpl implements TokenService {

   	@Autowired
	private RedisUtils redisUtils;
	
	/**
	 * 12小时后过期
	 */
	private final static int EXPIRE = 3600 * 12;

	@Override
	public Long queryByToken(String token) {
		String uid = redisUtils.get(token);
		if(StringUtils.isBlank(uid)) return null;
		return Long.valueOf(uid);
	}

	@Override
	public String createToken(long userId) {
		//生成token
		String token = generateToken();
		redisUtils.set(token, userId, 60*60*24*7);
		redisUtils.set(DigestUtils.md5Hex(userId+"_"), token, 60*60*24*7);
		return token;
	}

	@Override
	public void expireToken(String token,long userId){
		String uid = redisUtils.get(token);
		if(StringUtils.isBlank(uid)) return ;
		if(Long.valueOf(uid) != userId){
			return;
		}
		redisUtils.delete(token);
	}

	private String generateToken(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
