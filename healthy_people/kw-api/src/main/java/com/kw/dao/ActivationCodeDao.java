package com.kw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kw.entity.ActivationCodeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Mapper
public interface ActivationCodeDao extends BaseMapper<ActivationCodeEntity> {

    ActivationCodeEntity queryByCode(@Param("code") String code);

    ActivationCodeEntity findByInvitedUserId(Long userId);

    List<ActivationCodeEntity> queryAll(Long userId);
}
