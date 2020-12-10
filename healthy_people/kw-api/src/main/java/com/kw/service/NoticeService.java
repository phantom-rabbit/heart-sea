package com.kw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.entity.NoticeEntity;

import java.util.Map;

/**
 * 通告管理
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-03 18:03:32
 */
public interface NoticeService extends IService<NoticeEntity> {

    PageUtils queryPage(Map<String, Object> params);


}

