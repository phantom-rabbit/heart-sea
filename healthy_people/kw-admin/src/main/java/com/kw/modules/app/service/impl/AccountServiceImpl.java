package com.kw.modules.app.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.kw.common.enums.LogType;
import com.kw.common.enums.RealStatus;
import com.kw.common.exception.RRException;
import com.kw.common.utils.*;
import com.kw.modules.app.entity.*;
import com.kw.modules.app.form.OperaAccount;
import com.kw.modules.app.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.kw.modules.app.dao.AccountDao;
import org.springframework.transaction.annotation.Transactional;


@Service("accountService")
public class AccountServiceImpl extends ServiceImpl<AccountDao, AccountEntity> implements AccountService {



    @Autowired
    private ActivationCodeService activationCodeService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountLogService accountLogService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private LevelConfiigAwrdLtcService levelConfiigAwrdLtcService;
    @Autowired
    private LevelConfiigService levelConfiigService;
    @Autowired
    private VipConfigService vipConfigService;

    @Autowired
    private LtcConfigService ltcConfigService;
    @Autowired
    private UserLtcService userLtcService;

    @Autowired
    private AccountMoneyService accountMoneyService;
    @Autowired
    private UserTeamService userTeamService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    //    params.put("sd_status",params.get("sdStatus"));
        IPage<AccountEntity> page = this.baseMapper.selectPageVo(new Query<AccountEntity>().getPage(params), params);
        return new PageUtils(page);
    }


    @Override
    public AccountEntity queryByUserId(Long userId) {
        return baseMapper.queryByUserId(userId);
    }

    @Override
    public int countRealNumByUserId(Long userId) {
        return baseMapper.countRealNumByUserId(userId);
    }

    @Override
    public Map<String,Object> queryTeamPower(Long userId) {
        return baseMapper.queryTeamPower(userId);
    }

    @Override
    public int querySameLevelCountInTeam(Long userId, Long levelId) {
        return baseMapper.querySameLevelCountInTeam(userId, levelId);
    }

    @Override
    public int share(Long userId, BigDecimal shareMoney) {
        return baseMapper.share(userId,shareMoney);
    }

    @Override
    public int canCleOrder(Long sellerId, BigDecimal money) {
        return baseMapper.canCleOrder(sellerId,money);
    }

    @Override
    public int shareSon(Long userId, BigDecimal shareMoney) {
        return baseMapper.shareSon(userId,shareMoney);

    }


    @Override
    @Transactional
    public void authUser(AccountEntity account, Integer realStatus) {
        //查状态
        if (RealStatus.SUCCESS.getValue().equals(account.getRealStatus())) {
            throw new RRException("您的账户已认证成功");
        }
        if (RealStatus.WAIT_AUTH.getValue().equals(account.getRealStatus())) {
            throw new RRException("审核中，请耐心等待");
        }

        Long userId = account.getUserId();
        if(!RealStatus.SUCCESS.getValue().equals(realStatus)){
           account.setRealStatus(-1);
            if(!update(account,new UpdateWrapper<AccountEntity>().eq("id",account.getId()).in("real_status",0))){
                throw  new RRException("不要重复提交");
            }

        }else{
            account.setRealStatus(2);

            //VIP升级检测
            ActivationCodeEntity actCode = activationCodeService.findByInvitedUserId(account.getUserId());
            if (actCode != null) {
                AccountEntity inviter = queryByUserId(actCode.getUserId());
                if (inviter != null && inviter.getVipId() != null) {
                    //邀请人VIP奖励
                    VipConfigEntity vipConfigEntity = vipConfigService.getById(inviter.getVipId());
                    if (vipConfigEntity != null) {
                        //邀请人VIP升级检测
                        int count = countRealNumByUserId(actCode.getUserId());
                        List<VipConfigEntity> vipList = vipConfigService.queryAll();
                        if (vipList != null && vipList.size() > 0) {
                            for (int i = 0; i < vipList.size(); i++) {
                                if (count >= vipList.get(i).getInviteNumber()) {
                                    inviter.setVipId(vipList.get(i).getId());
                                    updateVip(inviter.getUserId(), vipList.get(i).getId());
                                    break;
                                }
                            }
                        }
                        //动力源奖励
//                        accountService.awardPowerSource(actCode.getUserId(), vipConfigEntity.getAwrdPower());
//                        accountLogService.logAccount(actCode.getUserId(), vipConfigEntity.getAwrdPower(), "直推用户" + account.getUserId() + "实名认证成功", LogType.INVITE_POWER_SOURE.getValue(), 4);
                        //OPC奖励
                        accountMoneyService.addParentMoney(actCode.getUserId(), vipConfigEntity.getAwrdParent());
                        accountLogService.logAccount(actCode.getUserId(), vipConfigEntity.getAwrdParent(), "直推用户" + account.getUserId() + "实名认证成功", LogType.INVITE_PARENT_MONEY.getValue(), 1);

                    }
                }
            }
            //注册认证成功默认VIP
            VipConfigEntity vipConfig = vipConfigService.getVipConfigAtAuthFinished();
            account.setVipId(vipConfig.getId());
            //注册认证等级升级及奖励
            LevelConfiigEntity levelConfiig = levelConfiigService.getLevelConfigAtAuthFinished();
            if (levelConfiig != null) {
                LevelConfiigAwrdLtcEntity levelConfiigAwrdLtc = levelConfiigAwrdLtcService.getOne(
                        new QueryWrapper<LevelConfiigAwrdLtcEntity>().eq("config_id", levelConfiig.getId()));

                account.setLevelId(levelConfiig.getId());
                if (levelConfiigAwrdLtc != null) {

                    LtcConfigEntity config = ltcConfigService.getById(levelConfiigAwrdLtc.getAwardLtcId());
                    UserLtcEntity userLt = new UserLtcEntity(userId, config, "实名认证奖励");
                    //实名奖励矿机不奖励激活码
                    userLt.setIsAward(1);
                    userLt.setIsActiveCode(1);
                    userLtcService.save(userLt);
                }
            }
            if(!update(account,new UpdateWrapper<AccountEntity>().eq("id",account.getId()).in("real_status",0,-1))){
                throw  new RRException("不要重复提交");
            }
         //   updateById(account);
            //动力值 值是实名认证通过一个账户加一个值，如果有入金上矿机的，按上矿机的实际投资币量，给1/10的值
            //TODO 动力源 源是包括自己账户在内以下所有注册实名认证通过的账户数量，每认证通过一个加一个源
            UserTeamEntity team = userTeamService.queryByUserId(userId);
            if (team == null) {
                log.error("用户User_Team信息错误====>UserId=" + userId);
            } else {
                String mark = team.getMark();
                if (StringUtils.isNotBlank(mark)) {
                    mark = mark.replaceAll("^,*|,*$", "");
                    String[] userIds = mark.split(",");
                    if (userIds.length > 0) {
                        accountMoneyService.addPowerSource(userIds, BigDecimal.ONE);
                        //记录日志
                        for (String uid : userIds) {
                            AccountEntity auth = queryByUserId(Long.valueOf(uid));
                            if(auth == null || !RealStatus.SUCCESS.getValue().equals(auth.getRealStatus())){
                                continue;
                            }
                            accountLogService.logAccount(Long.valueOf(uid), BigDecimal.ONE, "用户" + account.getUserId() + "实名认证成功", LogType.INVITE_POWER_SOURE.getValue(), 4);
                        }
                    }
                }
            }

            //TODO 市代奖励
            UserEntity user = userService.getById(userId);
            if (user != null) {
                String province = "", city = "";
                if (StringUtils.isNotBlank(user.getProvince()) && StringUtils.isNotBlank(user.getCity())) {
                    province = user.getProvince();
                    city = user.getCity();
                } else {
                    /**
                     * "result":{
                     *     "province":"浙江",
                     *     "city":"杭州",
                     *     "areacode":"0571",
                     *     "zip":"310000",
                     *     "company":"中国移动",
                     *     "card":""
                     * }
                     */
                    String key = redisUtils.get(Constant.JUHE_MOBILE_INFO, String.class);
                    JSONObject result = JuheUtils.getMobileInfo(key, user.getMobile());
                    if (result != null) {
                        province = result.getString("province");
                        city = result.getString("city");
                        user.setProvince(province);
                        user.setCity(city);
                        userService.updateById(user);
                    }
                }
                if (StringUtils.isNotBlank(city)) {
                    BigDecimal coin = redisUtils.get(Constant.SD_AWARD_AT_USER_AUTH, BigDecimal.class);
                    if (coin == null || coin.signum() < 0) {
                        log.error("市代奖励配置错误");
                        throw new RRException("系统错误");
                    }
                    AccountEntity sd = querySdFromCity(province, city);
                    if (sd != null) {
                        accountMoneyService.addParentMoney(sd.getUserId(),coin);
                        accountLogService.logAccount(sd.getUserId(), coin, "下级实名市代奖励", LogType.USER_AUTH_SD.getValue(), 1);
                    }
                }
            }
        }
    }

    @Override
    public AccountEntity querySdFromCity(String province, String city) {
        return baseMapper.querySdFromCity(province, city);
    }

    @Override
    @Transactional
    public void updateVip(Long userId, Long vipId) {
        baseMapper.updateVip(userId, vipId);
    }


    @Override
    public int querySameLevelCountInSonTeam(Long userId, Long levelId) {
        return baseMapper.querySameLevelCountInSonTeam(userId, levelId);
    }

    @Override
    public BigDecimal queryMaxPowerSourceFromSonTeam(Long userId) {
        return baseMapper.queryMaxPowerSourceFromSonTeam(userId);
    }

    @Override
    public int deductFrezen(Long sellerId, BigDecimal amount) {
        return this.baseMapper.deductFrezen(sellerId,amount);
    }

    @Override
    @Transactional
    public int addParentMoney(Long userId, BigDecimal amount) {
        return this.baseMapper.addParentMoney(userId,amount);
    }

    @Override
    public void LogDaytrans(BuyTransEntity buyTransEntity) {
        String date = DateUtils.format(new Date());
        String date_key = date+"-"+buyTransEntity.getSellerId();
        this.baseMapper.updateEveryDaySell(buyTransEntity.getSellerId(),buyTransEntity.getFee(),buyTransEntity.getAmount(),date,date_key);
        date_key = date+"-"+buyTransEntity.getBuyerId();
        this.baseMapper.updateEveryBuy(buyTransEntity.getBuyerId(),buyTransEntity.getAmount(),date,date_key);

    }

    @Override
    @Transactional
    public void opera(OperaAccount operaAccount) {
        if(operaAccount.getPayType().equals("decute_manager")){

            for(int i=0;i<operaAccount.getCt();i++) {
                //减少用户余额
                if (this.baseMapper.reduceAmount(operaAccount.getUserId(), new BigDecimal(operaAccount.getMoney())) < 1) {
                    throw new RRException("金额不足");
                }
                //记录日志
                accountLogService.logAccount(operaAccount.getUserId(), new BigDecimal(operaAccount.getMoney()), LogType.DECUTE_BACK.getName(), LogType.DECUTE_BACK.getValue(), 1);
            }
        }else{

           // for(int i=0;i<operaAccount.getCt();i++) {

                List<AccountEntity> entities = list(new QueryWrapper<AccountEntity>().eq("sd_status",4));

                for(AccountEntity accountEntity:entities) {
                    int sdcount = new Random().nextInt(operaAccount.getCt());
                    for(int i=0;i<sdcount;i++) {
                        this.baseMapper.addParentMoney(accountEntity.getUserId(), new BigDecimal(operaAccount.getMoney()));
                        accountLogService.logAccount(accountEntity.getUserId(), new BigDecimal(operaAccount.getMoney()), LogType.USER_AUTH_SD.getName(), LogType.USER_AUTH_SD.getValue(), 1);
                    }
                    }
           // }
        }

    }
}