package com.kw.modules.app.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kw.modules.app.entity.AccountEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 账户表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Mapper
public interface AccountDao extends BaseMapper<AccountEntity> {

    AccountEntity queryByUserId(Long userId);

    int countRealNumByUserId(@Param("userId")Long userId);

    Map<String,Object> queryTeamPower(@Param("userId") Long userId);


    int querySameLevelCountInTeam(@Param("userId")Long userId, @Param("levelId")Long levelId);
    int querySameLevelCountInSonTeam(@Param("userId")Long userId, @Param("levelId")Long levelId);

    int share(@Param("userId") Long userId, @Param("amount")BigDecimal shareMoney);

    int canCleOrder(@Param("userId") Long sellerId, @Param("amount") BigDecimal money);

    IPage<AccountEntity> selectPageVo(IPage<AccountEntity> page, @Param("params") Map<String, Object> accountEntityQueryWrapper);

    int shareSon(@Param("userId") Long userId, @Param("amount")BigDecimal shareMoney);

    void updateVip(@Param("userId")Long userId, @Param("vipId") Long vipId);

    AccountEntity querySdFromCity(@Param("province")String province, @Param("city")String city);


    /**
     * 查询直推好友团队中最大的一个值
     * @param userId
     * @return
     */
    BigDecimal queryMaxPowerSourceFromSonTeam(@Param("userId") Long userId);

    void updateEveryDaySell(@Param("userId") Long sellerId, @Param("fee")BigDecimal fee,@Param("sellAmount") BigDecimal amount,@Param("date") String date,@Param("dateKey")  String date_key);

    void updateEveryBuy(@Param("userId") Long buyerId,@Param("buyAmount") BigDecimal amount, @Param("date") String date,@Param("dateKey") String date_key);

    int addParentMoney(@Param("userId") Long userId,@Param("amount")  BigDecimal amount);

    int deductFrezen(@Param("userId") Long userId,@Param("amount") BigDecimal amount);

    int reduceAmount(@Param("userId") Long userId,@Param("parentPrice")  BigDecimal parentPrice);
}
