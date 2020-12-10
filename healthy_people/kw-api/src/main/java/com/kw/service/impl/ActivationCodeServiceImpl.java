package com.kw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.exception.RRException;
import com.kw.common.utils.InviteCodeUtil;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;
import com.kw.dao.ActivationCodeDao;
import com.kw.entity.ActivationCodeEntity;
import com.kw.service.ActivationCodeService;
import com.kw.service.UserLtcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service("activationCodeService")
public class ActivationCodeServiceImpl extends ServiceImpl<ActivationCodeDao, ActivationCodeEntity> implements ActivationCodeService {


    @Autowired
    private UserLtcService userLtcService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ActivationCodeEntity> page = this.page(
                new Query<ActivationCodeEntity>().getPage(params),
                new QueryWrapper<ActivationCodeEntity>()
        );

        return new PageUtils(page);
    }


    @Override
    public ActivationCodeEntity queryByCode(String code) {
        return baseMapper.queryByCode(code);
    }


    @Override
    @Transactional
    public boolean generate(Long userId, long activeCodeCt) {
        if (activeCodeCt == 0) {
            throw new RRException("您没有可领取的激活码");
        }
        for (int i = 0; i < activeCodeCt; i++) {
            ActivationCodeEntity entity = new ActivationCodeEntity();
            entity.setCreateTime(new Date());
            entity.setActivationCode(InviteCodeUtil.encry_RC4_string(String.format("%07d", i), UUID.randomUUID().toString().toUpperCase()));
            entity.setStatus(0);
            entity.setUserId(userId);
            entity.setCodeType(2);
            save(entity);
        }
        //更新领取状态
       if(userLtcService.updateActiveCodeStatusByUserId(userId) == 0){
           throw new RRException("领取激活码失败");
       };
        return true;
    }


    @Override
    public ActivationCodeEntity findByInvitedUserId(Long userId) {
        return baseMapper.findByInvitedUserId(userId);
    }

    @Override
    public List<ActivationCodeEntity> queryAll(Long userId) {
        return baseMapper.queryAll(userId);
    }
}