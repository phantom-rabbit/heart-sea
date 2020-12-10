package com.kw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kw.entity.AccountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 账户表
 * 
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@Mapper
@Repository
public interface AccountDao extends BaseMapper<AccountEntity> {

    int reduceAmount(@Param("userId") Long userId,@Param("parentPrice")  BigDecimal parentPrice);

    void addPower(@Param("userId") Long userId,@Param("power") BigDecimal power);

    AccountEntity queryByUserId(Long userId);

    int frenZen(@Param("userId") Long userId, @Param("amount")String amount);

    int deductFrezen(@Param("userId") Long userId,@Param("amount") BigDecimal amount);

    int addParentMoney(@Param("userId") Long userId,@Param("amount")  BigDecimal amount);


    int updateParentMoneyByMap(@Param("params") Map<Object, Object> map);

    int countRealNumByUserId(@Param("userId")Long userId);

    BigDecimal queryTeamPower(@Param("userId") Long userId);

    int querySameLevelCountInTeam(@Param("userId")Long userId, @Param("levelId")Long levelId);

    int awardPowerSource(@Param("userId") Long userId,@Param("amount") BigDecimal awrdPower);

    void updateEveryDayPower(@Param("dateKey") String date_key,@Param("date") String date,@Param("power") BigDecimal powerSource,@Param("userId") Long userId);

    void updateEveryDayPowerSource(@Param("dateKey") String date_key,@Param("date")String date,@Param("powerSource") BigDecimal power, @Param("userId") Long userId);

    void updateEveryDaySell(@Param("userId") Long sellerId, @Param("fee")BigDecimal fee,@Param("sellAmount") BigDecimal amount,@Param("date") String date,@Param("dateKey")  String date_key);

    void updateEveryBuy(@Param("userId") Long buyerId,@Param("buyAmount") BigDecimal amount, @Param("date") String date,@Param("dateKey") String date_key);

    AccountEntity querySdFromCity(@Param("province")String province, @Param("city")String city);

    IPage<AccountEntity> selectPageVo(IPage<AccountEntity> page, @Param("ids") Object[] ids);

    long addPowerSource(@Param("userIds") String[] userIds, @Param("source") BigDecimal source);

    void updateVip(@Param("userId")Long userId, @Param("vipId") Long vipId);

    void addSonMoney(@Param("userId") Long userId,@Param("amount")  BigDecimal amount);

    AccountEntity queryByIdcard(@Param("idcard")String idcard);

    List<AccountEntity> queryByIds(@Param("ids")Object[] ids);

    int canCleOrder(@Param("userId") Long sellerId, @Param("amount") BigDecimal money);
}
