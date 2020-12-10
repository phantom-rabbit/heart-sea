package com.kw.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kw.common.utils.R;
import com.kw.entity.AccountEntity;
import com.kw.entity.BuyTransEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * 账户表
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
public interface AccountService extends IService<AccountEntity> {


    /**
     * 通过UserID 查询
     * @param userId
     * @return
     */
    AccountEntity queryByUserId(Long userId);

    /**
     *
     * @param idcard
     * @return
     */
    AccountEntity queryByIdcard(String idcard);
    /**
     * 购买矿机
     * @param userId    用户id
     * @param ltcId    矿机配置实体
     * @return        返回成功信息
     */
    R buyLtc(Long userId, String ltcId);

    int frenZen( Long userId, String amount);

    int deductFrezen(Long userId, BigDecimal amount);

    int addParentMoney( Long userId, BigDecimal amount);



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
    BigDecimal queryTeamPower(Long userId);


    /**
     * 我的团队同级好友人数
     * @param userId
     * @return
     */
    int querySameLevelCountInTeam(Long userId, Long levelId);

    int awardPowerSource(Long userId, BigDecimal awrdPower);

    void LogDaytrans(BuyTransEntity buyTransEntity);

    /**
     * 通过城市查找市代
     * @param city
     * @return
     */
    AccountEntity querySdFromCity(String province, String city);

    IPage<AccountEntity> selectPageVo(IPage<AccountEntity> page, Object[] ids);

    /**
     * 添加动力源 源是包括自己账户在内以下所有注册实名认证通过的账户数量，每认证通过一个加一个源
     * @param userIds
     * @param one
     */
    long addPowerSource(String[] userIds, BigDecimal one);

    void updateVip(Long userId, Long id);

    void addSonMoney( Long userId, BigDecimal amount);

    List<AccountEntity> queryByIds(Object[] ids);

    int canCleOrder(Long sellerId, BigDecimal add);
}

