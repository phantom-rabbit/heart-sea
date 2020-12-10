

package com.kw.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.modules.sys.entity.SysLogEntity;

import java.util.Map;


/**
 * 系统日志
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
