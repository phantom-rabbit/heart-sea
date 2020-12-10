package com.kw.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.modules.app.entity.LtcLogEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 矿机日志
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-02 11:25:25
 */
public interface LtcLogService extends IService<LtcLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<HashMap> queryReportList(Map<String,Object> params);
}

