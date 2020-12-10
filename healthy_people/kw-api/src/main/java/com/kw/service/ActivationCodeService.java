package com.kw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.entity.ActivationCodeEntity;

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

    ActivationCodeEntity queryByCode(String code);

    /**
     * 生成激活码
     * @param userId
     * @param activeCodeCt
     * @return
     */
    boolean generate(Long userId, long activeCodeCt);

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
}

