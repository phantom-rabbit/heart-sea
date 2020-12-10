package com.kw.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.modules.app.entity.UserDaytransEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户日买卖
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-12 08:07:04
 */
public interface UserDaytransService extends IService<UserDaytransEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<HashMap> queryReportList(Map<String,Object> params);
}

