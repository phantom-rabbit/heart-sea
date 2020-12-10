

package com.kw.common.sysconfig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.kw.common.exception.RRException;
import com.kw.common.sysconfig.dao.SysConfigDao;
import com.kw.common.sysconfig.entity.SysConfigEntity;
import com.kw.common.sysconfig.redis.SysConfigRedis;
import com.kw.common.sysconfig.service.SysConfigService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;
import com.kw.common.utils.RedisUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;

@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {
	@Autowired
	private SysConfigRedis sysConfigRedis;

	@Autowired
	private RedisUtils redisUtils;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String paramKey = (String)params.get("paramKey");

		IPage<SysConfigEntity> page = this.page(
			new Query<SysConfigEntity>().getPage(params),
			new QueryWrapper<SysConfigEntity>()
				.like(StringUtils.isNotBlank(paramKey),"param_key", paramKey)
				.eq("status", 1)
		);

		return new PageUtils(page);
	}

	@Override
	public void saveConfig(SysConfigEntity config) {
		this.save(config);
		sysConfigRedis.saveOrUpdate(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysConfigEntity config) {
		this.updateById(config);
		sysConfigRedis.saveOrUpdate(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateValueByKey(String key, String value) {
		SysConfigEntity entity = baseMapper.queryByKey(key);
		if (entity == null) {
			entity = new SysConfigEntity();
			entity.setParamValue(value);
			entity.setParamKey(key);
			save(entity);
		} else {
			entity.setParamValue(value);
			update(entity);
		}
		sysConfigRedis.saveOrUpdate(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBatch(Long[] ids) {
		for(Long id : ids){
			SysConfigEntity config = this.getById(id);
			sysConfigRedis.delete(config.getParamKey());
		}

		this.removeByIds(Arrays.asList(ids));
	}

	@Override
	public String getValue(String key) {
		SysConfigEntity config = baseMapper.queryByKey(key);
		return config == null ? null : config.getParamValue();
	}
	
	@Override
	public <T> T getConfigObject(String key, Class<T> clazz) {
		String value = redisUtils.get(key);
		if(StringUtils.isBlank(value)){
			value = getValue(key);
		}
		if(StringUtils.isNotBlank(value)){
			return new Gson().fromJson(value, clazz);
		}
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RRException("获取参数失败");
		}
	}
}
