package com.kw.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.enums.LogType;
import com.kw.common.enums.RealStatus;
import com.kw.common.exception.RRException;
import com.kw.common.utils.*;
import com.kw.common.validator.Assert;
import com.kw.common.validator.ValidatorUtils;
import com.kw.dao.UserDao;
import com.kw.entity.*;
import com.kw.enums.UserStatus;
import com.kw.form.LoginForm;
import com.kw.form.RegisterForm;
import com.kw.form.UserAuthForm;
import com.kw.service.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ActivationCodeService activationCodeService;
    @Autowired
    private AccountService accountService;
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
    public UserEntity queryByMobile(String mobile) {
        return baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("mobile", mobile));
    }

    @Override
    public String login(LoginForm form, String ip) {
        //图形验证码
        String captcha = redisUtils.get(form.getUuid());
        if (StringUtils.isBlank(captcha)) {
            throw new RRException("图形验证码无效");
        }

        if (!captcha.equals(form.getCaptcha())) {
            throw new RRException("验证码错误");
        }

        UserEntity user = queryByMobile(form.getMobile());
        Assert.isNull(user, "手机号或密码错误");
        ValidatorUtils.validateEntity(form);

        if (!UserStatus.normal.getValue().equals(user.getStatus())) {
            throw new RRException("账号异常，不能登录");
        }

        //密码错误
        if (!user.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))) {
            throw new RRException("手机号或密码错误");
        }

        user.setLastLoginIp(ip);
        user.setLastLoginTime(new Date());
        updateById(user);

        redisUtils.delete(form.getUuid());
        //获取登录token
        return tokenService.createToken(user.getId());
    }

    @Override
    @Transactional
    public String register(RegisterForm form, String ip) {
        //TODO 验证短信
        //缓存
        String captcha = redisUtils.get(form.getMobile() + form.getCode());
        if (!form.getCaptcha().equals(captcha)) {
            throw new RRException("验证码无效或超时");
        }
        UserEntity userEntity = queryByMobile(form.getMobile());
        if (userEntity != null) {
            throw new RRException("该手机号码已注册");
        }
        //TODO 激活码是否存在
        ActivationCodeEntity activationCode = activationCodeService.queryByCode(form.getCode());
        if (activationCode == null || activationCode.getEndUser() != null) {
            throw new RRException("抱歉，您的激活码无效");
        }

        //注册
        UserEntity user = new UserEntity();
        user.setMobile(form.getMobile());
        user.setUserName(form.getMobile());
        user.setPassword(DigestUtils.sha256Hex(form.getPassword()));
        Date now = new Date();
        user.setLastLoginIp(ip);
        user.setLastLoginTime(now);
        user.setCreateDate(now);
        user.setCreateIp(ip);
        user.setPayPassword(DigestUtils.sha256Hex(form.getPayPassword()));
        user.setStatus(0);
        user.setSecretKey(GoogleAuthenticatorUtils.createSecretKey());
        save(user);
        //添加账户信息
        AccountEntity entity = new AccountEntity();
        entity.setUserId(user.getId());
        entity.setRealStatus(0);
        entity.setLevelId(0L);
        entity.setVipId(0L);
        AccountMoneyEntity accountMoneyEntity = new AccountMoneyEntity();
        accountMoneyEntity.setUserId(user.getId());
        accountMoneyEntity.setPower(BigDecimal.ZERO);
        accountMoneyEntity.setParentMoney(BigDecimal.ZERO);
        accountMoneyEntity.setSonMoney(BigDecimal.ZERO);
        accountMoneyEntity.setPowerSource(BigDecimal.ZERO);
        accountMoneyService.save(accountMoneyEntity);
        accountService.save(entity);
        //更新激活码状态已使用
        activationCode.setEndUser(user.getId());
        activationCode.setStatus(1);
        activationCode.setUseTime(now);
        activationCodeService.updateById(activationCode);

        String mark = "";
        Long inviteUserId = 0L;//邀请人ID
        //设置邀请关联关系
        if (activationCode.getUserId() != null) {
            UserTeamEntity parent = userTeamService.queryByUserId(activationCode.getUserId());
            if (parent != null) {
                mark = parent.getMark() + user.getId() + ",";
                inviteUserId = parent.getUserId();
            }
        }
        if (StringUtils.isBlank(mark)) {
            mark = "," + user.getId() + ",";
        }
        UserTeamEntity team = new UserTeamEntity();
        team.setUserId(user.getId());
        team.setInviterUserId(inviteUserId);
        team.setMark(mark);
        team.setAddtime(now);
        userTeamService.save(team);
        //生成Token
        return tokenService.createToken(user.getId());

//       List<ActivationCodeEntity> list = activationCodeService.queryAll(1L);
//
//       Long m = Long.valueOf(form.getMobile());
//       int i = 0;
//       for(ActivationCodeEntity activationCode: list){
//           if(activationCode.getStatus() == 0){
//               String mobile = m+"";
//               m = (m+1);
//               i++;
//               if(i >10) break;
//               if(queryByMobile(mobile)!=null) continue;
//               //注册
//               UserEntity user = new UserEntity();
//               user.setMobile(mobile);
//               user.setUserName(mobile);
//               user.setPassword(DigestUtils.sha256Hex("123456"));
//               Date now = new Date();
//               user.setLastLoginIp(ip);
//               user.setLastLoginTime(now);
//               user.setCreateDate(now);
//               user.setCreateIp(ip);
//               user.setPayPassword(DigestUtils.sha256Hex("123456"));
//               user.setStatus(0);
//               user.setSecretKey(GoogleAuthenticatorUtils.createSecretKey());
//               save(user);
//               //添加账户信息
//               AccountEntity entity = new AccountEntity();
//               entity.setUserId(user.getId());
//               entity.setRealStatus(0);
//               entity.setPower(BigDecimal.ZERO);
//               entity.setParentMoney(BigDecimal.ZERO);
//               entity.setSonMoney(BigDecimal.ZERO);
//               entity.setLevelId(0L);
//               entity.setVipId(0L);
//               accountService.save(entity);
//               //
//               AccountMoneyEntity accountMoneyEntity = new AccountMoneyEntity();
//               accountMoneyEntity.setUserId(user.getId());
//               accountMoneyEntity.setPower(BigDecimal.ZERO);
//               accountMoneyEntity.setParentMoney(BigDecimal.ZERO);
//               accountMoneyEntity.setSonMoney(BigDecimal.ZERO);
//               accountMoneyEntity.setPowerSource(BigDecimal.ZERO);
//               accountMoneyService.save(accountMoneyEntity);
//               //更新激活码状态已使用
//               activationCode.setEndUser(user.getId());
//               activationCode.setStatus(1);
//               activationCode.setUseTime(now);
//               activationCodeService.updateById(activationCode);
//               String mark = "";
//               Long inviteUserId = 0L;//邀请人ID
//
//               //设置邀请关联关系
//               if (activationCode.getUserId() != null) {
//                   UserTeamEntity parent = userTeamService.queryByUserId(activationCode.getUserId());
//                   if (parent != null) {
//                       mark = parent.getMark() + user.getId() + ",";
//                       inviteUserId = parent.getUserId();
//                   }
//               }
//               if (StringUtils.isBlank(mark)) {
//                   mark = "," + user.getId() + ",";
//               }
//               UserTeamEntity team = new UserTeamEntity();
//               team.setUserId(user.getId());
//               team.setInviterUserId(inviteUserId);
//               team.setMark(mark);
//               team.setAddtime(now);
//               userTeamService.save(team);
//
//               UserAuthForm form1= new UserAuthForm();
//
//               form1.setIdCard("21042119840316" + (2025 + i));
//               form1.setRealName("测试"+i);
//               userAuth(user.getId(), form1);
//           }
//       }
//
//       return tokenService.createToken(1);
    }


    @Override
    @Transactional
    public boolean userAuth(Long userId, UserAuthForm form) {

        int age = DateUtils.getAge(form.getIdCard());
        if(age>70||age<18){
            throw new RRException("抱歉，您的年龄不符合18-70岁");
        }
        if (accountService.queryByIdcard(form.getIdCard()) != null) {
            throw new RRException("抱歉，该身份证号码已使用");
        }
        //查状态
        AccountEntity account = accountService.queryByUserId(userId);
        if (RealStatus.SUCCESS.getValue().equals(account.getRealStatus())) {
            throw new RRException("您的账户已认证成功");
        }
        if (RealStatus.FAIL.getValue().equals(account.getRealStatus())) {
            throw new RRException("审核失败，如有疑问可联系客服处理");
        }
        if (RealStatus.WAIT_AUTH.getValue().equals(account.getRealStatus())) {
            throw new RRException("审核中，请耐心等待");
        }
        //待审核
     //   account.setRealStatus(1);
        account.setIdCard(form.getIdCard().toUpperCase());
        account.setRealName(form.getRealName());
       // accountService.updateById(account);
        //认证有效性
        String appkey = redisUtils.get(Constant.JUHE_REAL_NAME_KEY, String.class);

        if (!JuheUtils.auth(appkey, form.getRealName().trim(), form.getIdCard().trim())) {
            account.setRealStatus(-1);
            if(!accountService.update(account,new UpdateWrapper<AccountEntity>().eq("id",account.getId()).eq("real_status",0))){
                throw  new RRException("不要重复提交");
            }
            return false;
        } else {
            account.setRealStatus(2);

            //VIP升级检测
            ActivationCodeEntity actCode = activationCodeService.findByInvitedUserId(userId);
            if (actCode != null) {
                AccountEntity inviter = accountService.queryByUserId(actCode.getUserId());
                if (inviter != null && inviter.getVipId() != null) {
                    //邀请人VIP奖励
                    VipConfigEntity vipConfigEntity = vipConfigService.getById(inviter.getVipId());
                    if (vipConfigEntity != null) {
                        //邀请人VIP升级检测
                        int count = accountService.countRealNumByUserId(actCode.getUserId());
                        List<VipConfigEntity> vipList = vipConfigService.queryAll();
                        if (vipList != null && vipList.size() > 0) {
                            for (int i = 0; i < vipList.size(); i++) {
                                if (count >= vipList.get(i).getInviteNumber()) {
                                    inviter.setVipId(vipList.get(i).getId());
                                    accountService.updateVip(inviter.getUserId(), vipList.get(i).getId());
                                    break;
                                }
                            }
                        }
                        //动力源奖励
//                        accountService.awardPowerSource(actCode.getUserId(), vipConfigEntity.getAwrdPower());
//                        accountLogService.logAccount(actCode.getUserId(), vipConfigEntity.getAwrdPower(), "直推用户" + account.getUserId() + "实名认证成功", LogType.INVITE_POWER_SOURE.getValue(), 4);
                        //OPC奖励


                        accountService.addParentMoney(actCode.getUserId(), vipConfigEntity.getAwrdParent());
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
                account.setLevelId(levelConfiig.getId());
                LevelConfiigAwrdLtcEntity levelConfiigAwrdLtc = levelConfiigAwrdLtcService.getOne(
                        new QueryWrapper<LevelConfiigAwrdLtcEntity>().eq("config_id", levelConfiig.getId()));
                if (levelConfiigAwrdLtc != null) {
                    LtcConfigEntity config = ltcConfigService.getById(levelConfiigAwrdLtc.getAwardLtcId());
                    UserLtcEntity userLt = new UserLtcEntity(userId, config, "实名认证奖励");
                    //实名奖励矿机不奖励激活码
                    userLt.setIsActiveCode(1);
                    userLt.setIsAward(1);
                    userLtcService.save(userLt);
                }
            }
            if(!accountService.update(account,new UpdateWrapper<AccountEntity>().eq("id",account.getId()).eq("real_status",0))){
                throw  new RRException("不要重复提交");
            }
          //  accountService.updateById(account);
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

                        accountService.addPowerSource(userIds, BigDecimal.ONE);
                        //记录日志
                        for (String uid : userIds) {
                            AccountEntity auth = accountService.queryByUserId(Long.valueOf(uid));
                            if(auth == null || !RealStatus.SUCCESS.getValue().equals(auth.getRealStatus())){
                                continue;
                            }
                            accountLogService.logAccount(Long.valueOf(uid), BigDecimal.ONE, "用户" + account.getUserId() + "实名认证成功", LogType.INVITE_POWER_SOURE.getValue(), 4);
                        }
                    }
                }
            }

            //TODO 市代奖励
            UserEntity user = getById(userId);
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
                        updateById(user);
                    }
                }
                if (StringUtils.isNotBlank(city)) {
                    BigDecimal coin = redisUtils.get(Constant.SD_AWARD_AT_USER_AUTH, BigDecimal.class);
                    if (coin == null || coin.signum() < 0) {
                        log.error("市代奖励配置错误");
                        throw new RRException("系统错误");
                    }
                    AccountEntity sd = accountService.querySdFromCity(province, city);
                    if (sd != null) {
                        accountService.addParentMoney(sd.getUserId(), coin);
                        accountLogService.logAccount(sd.getUserId(), coin, "下级实名市代奖励", LogType.USER_AUTH_SD.getValue(), 1);
                    }
                }
            }

            return true;
        }

    }
}
