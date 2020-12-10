package com.kw.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.PageUtils;
import com.kw.modules.app.entity.AccountEntity;
import com.kw.modules.app.entity.BuyTransEntity;
import com.kw.modules.app.form.OperaAccount;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 账户表
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
public interface AccountService extends IService<AccountEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 通过UserID 查询
     * @param userId
     * @return
     */
    AccountEntity queryByUserId(Long userId);

    /**
     * 有效直推人数
     * @param userId
     * @return
     */
    int countRealNumByUserId(Long userId);


    /**
     * 团队动力值
     * @param userId
     * @return
     */
    Map<String,Object> queryTeamPower(Long userId);




    int share(Long userId, BigDecimal shareMoney);

    int canCleOrder(Long sellerId, BigDecimal money);

    int shareSon(Long userId, BigDecimal shareMoney);

    void authUser(AccountEntity a, Integer realStatus);




    /**
     * 通过城市查找市代
     * @param city
     * @return
     */
    AccountEntity querySdFromCity(String province, String city);

    void updateVip(Long userId, Long id);


    /**
     * 直推好友
     * @param userId
     * @return
     */
    int querySameLevelCountInTeam(Long userId, Long levelId);

    /**
     * 我的团队直推部门中满足的部门数量 不限直推
     * @param userId
     * @return
     */
    int querySameLevelCountInSonTeam(Long userId, Long levelId);


    /**
     * 查询直推好友团队中最大的一个值
     * @param userId
     * @return
     */
    BigDecimal queryMaxPowerSourceFromSonTeam(@Param("userId") Long userId);

    int deductFrezen(Long sellerId, BigDecimal amount);

    int addParentMoney(Long buyerId, BigDecimal amount);

    void LogDaytrans(BuyTransEntity buyTransEntity);

    void opera(OperaAccount operaAccount);
}

