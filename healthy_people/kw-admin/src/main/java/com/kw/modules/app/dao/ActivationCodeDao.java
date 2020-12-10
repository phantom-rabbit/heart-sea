package com.kw.modules.app.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kw.modules.app.entity.ActivationCodeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Mapper
@Repository
public interface ActivationCodeDao extends BaseMapper<ActivationCodeEntity> {

    IPage<?> queryList(IPage<ActivationCodeEntity> page, @Param("params")Map<String, Object> params);


    ActivationCodeEntity queryByCode(@Param("code") String code);

    ActivationCodeEntity findByInvitedUserId(Long userId);

    List<ActivationCodeEntity> queryAll(Long userId);
}
