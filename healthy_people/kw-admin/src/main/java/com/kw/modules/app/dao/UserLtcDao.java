package com.kw.modules.app.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kw.modules.app.entity.UserEntity;
import com.kw.modules.app.entity.UserLtcEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户矿机表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Mapper
public interface UserLtcDao extends BaseMapper<UserLtcEntity> {

    IPage<?> queryList(IPage<UserLtcEntity> page, @Param("params") Map<String, Object> params);

    BigDecimal queryHyd(@Param("userId") Long userId,@Param("dl")  int dl);

    /**
     * 查询userId所有直推好友部门满足的，部门就是直推好友的团队
     *
     * @param userId
     * @param configHyd 配置值
     * @return
     */
    BigDecimal queryMaxHydFromSon(@Param("userId") Long userId, @Param("dl")  int dl,@Param("configHyd") BigDecimal configHyd);

    List<HashMap> queryReportList(@Param("params") Map<String,Object> params);
}
