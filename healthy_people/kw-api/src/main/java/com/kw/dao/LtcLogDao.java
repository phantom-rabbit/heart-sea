package com.kw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kw.entity.LtcLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 矿机日志
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-02 11:25:25
 */
@Mapper
public interface LtcLogDao extends BaseMapper<LtcLogEntity> {

    IPage<LtcLogEntity> queryList(IPage<LtcLogEntity> page, @Param("params") Map<String, Object> params);

    /**
     * OPC待收益总额
     * @param userId
     * @return
     */
    BigDecimal findLtcIncomeTotal(Long userId);
    /**
     * MAC待收益总额
     * @param userId
     * @return
     */
    BigDecimal findLtcIncomeCoinTotal(Long userId);

    int updateLtcIncomeStatus(Long userId);
}
