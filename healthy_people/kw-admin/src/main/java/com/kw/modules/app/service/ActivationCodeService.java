package com.kw.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.modules.app.entity.ActivationCodeEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
public interface ActivationCodeService extends IService<ActivationCodeEntity> {

    PageUtils queryPage(Map<String, Object> params);


    /**
     * 通过被邀请人查询激活码信息
     * @param userId
     * @return
     */
    ActivationCodeEntity findByInvitedUserId(Long userId);

    /**
     *
     * @param userId
     * @return
     */
    List<ActivationCodeEntity> queryAll(Long userId);

    ActivationCodeEntity queryByCode(String code);
}

