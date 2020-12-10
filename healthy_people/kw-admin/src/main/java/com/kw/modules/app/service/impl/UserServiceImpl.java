package com.kw.modules.app.service.impl;

import com.kw.modules.app.entity.*;
import com.kw.modules.app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;

import com.kw.modules.app.dao.UserDao;
import org.springframework.transaction.annotation.Transactional;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private LevelConfiigService levelConfiigService;
    @Autowired
    private UserTeamService userTeamService;
    @Autowired
    private LevelRecordsService levelRecordsService;
    @Autowired
    private UserLtcService userLtcService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        return new PageUtils(this.baseMapper.queryList(new Query<UserEntity>().getPage(params), params));
    }


    @Override
    @Transactional
    public void userLevelChangeCheck(Long userId) {


        AccountEntity account = accountService.queryByUserId(userId);
        if (account == null) {
            log.error("用户账户不存在，UserID=" + userId + ",执行用户等级升级检测");
            return;
        }
        /**
         * 只有注册认证才有等级，默认0则未实名，未实名则不处理
         */
        if (account.getRealStatus() != 2 && account.getLevelId() == 0) {
            log.error(userId + ">>该用户未实名");
            return;
        }

        //TODO
        LevelRecordsEntity levelRecords = new LevelRecordsEntity();

        //用户当前等级
        LevelConfiigEntity currLevelConfig = levelConfiigService.getById(account.getLevelId());
        if (currLevelConfig == null) {
            log.error(userId + ">>该用户无等级");
            return;
        }
        //获取下一等级
        List<LevelConfiigEntity> configs =
                levelConfiigService.list(
                        new QueryWrapper<LevelConfiigEntity>()
                                .gt("order_num", currLevelConfig.getOrderNum())
                                .orderByAsc("order_num"));
        if (configs == null || configs.size() == 0) {
            log.error(userId + ">>无等级配置记录");
            return;
        }
        LevelConfiigEntity next = configs.get(0);
        //要求实名
        if (next.getIsReal() == 1 && account.getRealStatus() != 2) {
            log.error(userId + ">>未满足实名要求");
            return;
        }


        //有效直推人数
        int count = accountService.countRealNumByUserId(userId);
        if (count < next.getInviteCount()) {
            log.error(userId + ">>未满足直推人数" + count);
            return;
        }

        //TODO
        levelRecords.setInviterCount(count);

        //团队动力值
        Map<String, Object> teamAm = accountService.queryTeamPower(userId);
        BigDecimal teamPowerSource = BigDecimal.valueOf(Double.valueOf(String.valueOf(teamAm.get("powerSource"))));
        BigDecimal teamPower = userLtcService.queryHyd(userId);
        if (teamPower.compareTo(next.getTeamPower()) < 0 && teamPowerSource.compareTo(next.getTeamPowerSource()) < 0) {
            log.error(userId + ">>未满动力值或动力源" + String.format("，活跃度=%s,源=%s", teamPower, teamPowerSource));
            return;
        }
        //TODO
        levelRecords.setPower(teamPower.longValue());
        levelRecords.setPowerSource(teamPowerSource.longValue());


        //小区 大区
        if (next.getIsLowerAnyTeam() == 1) {
            BigDecimal lowerAnyTeamPower = next.getLowerAnyTeamPower();
            /**
             * 大区：所有直推好友中必须一个满足要求
             */
            BigDecimal maxHydFromSon = userLtcService.queryMaxHydFromSon(userId, lowerAnyTeamPower);
            BigDecimal maxPowerSourceFromSon = accountService.queryMaxPowerSourceFromSonTeam(userId);
            if (maxHydFromSon.compareTo(lowerAnyTeamPower) < 0 && maxPowerSourceFromSon.compareTo(lowerAnyTeamPower) < 0) {
                log.error(userId + ">>大区要求未满足,MAX活跃度="+maxHydFromSon+",MAX能量源="+maxPowerSourceFromSon);
                return;
            }
            levelRecords.setDqPowerSource(maxPowerSourceFromSon.longValue());
            levelRecords.setDqPower(maxHydFromSon.longValue());
        }

        if (next.getIsDirect() == 1) {
            //限制直推
            int lowerCount = accountService.querySameLevelCountInTeam(userId, currLevelConfig.getId());
            if (lowerCount < next.getLowerCount()) {
                log.error(userId + ">>直推同级人数未满足");
                return;
            }
            levelRecords.setEqLevelCount(lowerCount);
        } else {
            //不限制直推 直推N个部门满足
            int lowerCount = accountService.querySameLevelCountInSonTeam(userId, currLevelConfig.getId());
            if (lowerCount < next.getLowerCount()) {
                log.error(userId + ">>直推部门同级数未满足");
                return;
            }
            levelRecords.setEqLevelCount(lowerCount);
        }

        levelRecords.setUserId(userId);
        levelRecords.setUpdateTime(new Date());
        levelRecords.setLevelId(next.getId());
        LevelRecordsEntity levelRecordsX =
                levelRecordsService.getOne(
                        new QueryWrapper<LevelRecordsEntity>()
                                .eq("user_id", userId)
                                .eq("status", 0));
        if (levelRecordsX == null) {
            levelRecordsService.save(levelRecords);
        } else {
            levelRecords.setId(levelRecordsX.getId());
            levelRecordsService.updateById(levelRecords);
        }
    }

    @Override
    public List<HashMap> queryReportList(Map<String, Object> params) {
        return baseMapper.queryReportList(params);
    }
}