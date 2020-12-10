package com.kw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.entity.VipConfigEntity;

import java.util.List;
import java.util.Map;

/**
 * Vip配置表
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
public interface VipConfigService extends IService<VipConfigEntity> {

    PageUtils queryPage(Map<String, Object> params);


    /**
     * 实名认证完成 更新默认VIP
     * @return
     */
    VipConfigEntity getVipConfigAtAuthFinished();


    /**
     * 查询全部，inviter_number倒叙查询
     * @return
     */
    List<VipConfigEntity> queryAll();
}

