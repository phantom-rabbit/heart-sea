package com.kw.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.modules.app.entity.UserEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员记录
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:15:46
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);



    /**
     * 用户等级变化检测
     * @param userId
     */
    void userLevelChangeCheck(Long userId);

    List<HashMap> queryReportList(Map<String,Object> params);
}

