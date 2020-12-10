package com.kw.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kw.common.annotation.Login;
import com.kw.common.annotation.LoginUser;
import com.kw.common.enums.LogType;
import com.kw.common.enums.SdStatus;
import com.kw.common.exception.RRException;
import com.kw.common.group.AlipayGroup;
import com.kw.common.group.BankGroup;
import com.kw.common.group.USDTGroup;
import com.kw.common.sms.config.SmsConstant;
import com.kw.common.sms.config.SmsResult;
import com.kw.common.utils.*;
import com.kw.common.validator.ValidatorUtils;
import com.kw.entity.*;
import com.kw.form.*;
import com.kw.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiUserController {

    @Autowired
    private AccountService accountService;


    @Autowired
    private AccountMoneyService accountMoneyService;

    @Autowired
    private GenesisService genesisService;

    @Autowired
    private UserTeamService userTeamService;

    @Autowired
    private AccountLogService accountLogService;
    @Autowired
    private UserService userService;
    @Autowired
    private LtcLogService ltcLogService;
    @Autowired
    private UserLtcService userLtcService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private SmsService smsService;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private BanksService banksService;


    @Autowired
    private ActivationCodeService activationCodeService;

    @Autowired
    private UserDaybillsService userDaybillsService;


    @Autowired
    private UserDaytransService userDaytransService;
    @Autowired
    private TeamConfigService teamConfigService;

    @Autowired
    private LevelConfiigService levelConfiigService;


    @RequestMapping("banks")
    public R banks() {
        return R.ok().put("page", banksService.list());
    }

    @Login
    @RequestMapping("directs")
    public R directs(@RequestAttribute("userId") Long userId) {
        List<UserTeamEntity> userTeamEntities = userTeamService.queryByInviterId(userId);
        List<SelectEntity> selectEntities = new ArrayList<SelectEntity>();


        for (UserTeamEntity userTeamEntity : userTeamEntities) {
            SelectEntity selectEntity = new SelectEntity();
            selectEntity.setText("用户ID:" + userTeamEntity.getUserId() + "");
            selectEntity.setValue(userTeamEntity.getUserId() + "");
            selectEntities.add(selectEntity);
        }
        return R.ok().put("directs", selectEntities);
    }


    @Login
    @RequestMapping("user/invitecode/award")
    public R awardInvitecode(@RequestAttribute("userId") Long userId, @RequestBody AwardForm awardForm) {
        ValidatorUtils.validateEntity(awardForm);

        List<ActivationCodeEntity> list = activationCodeService.list(new QueryWrapper<ActivationCodeEntity>().eq("user_id", userId).eq("status", 0));
        if (list.size() < awardForm.getNum()) {
            return R.error("激活码数量不足,请检查是否有尚未领取激活码");
        }
        if (!userTeamService.queryByUserId(awardForm.getAwardUserId()).getInviterUserId().equals(userId)) {
            return R.error("不满足直推关系");
        }
        for (int i = 0; i < awardForm.getNum(); i++) {
            ActivationCodeEntity activationCodeEntity = list.get(i);
            activationCodeEntity.setUserId(awardForm.getAwardUserId());
            activationCodeService.updateById(activationCodeEntity);
        }
        return R.ok("发放成功");
        // return R.ok().put("directs", userTeamService.queryByInviterId(userId));
    }


    @Login
    @PostMapping("user/payment/save")
    public R saveAlipay(@RequestAttribute("userId") Long userId, @RequestBody PaymentForm paymentForm) {
        AccountEntity accountEntity = accountService.queryByUserId(userId);
        if (accountEntity.getRealStatus() == 0) {
            return R.error("请先进行实名认证");
        }
        switch (paymentForm.getType()) {
            case "aliPay":
                if (accountEntity.getAlipayStatus() == 1) {
                    return R.error("您已经设置过支付宝账号了");
                }
                ValidatorUtils.validateEntity(paymentForm, AlipayGroup.class);
                accountEntity.setAlipayNumber(paymentForm.getAliPayNumber());
                accountEntity.setAlipayStatus(1);
                break;
            case "bank":
                if (accountEntity.getBankStatus() == 1) {
                    return R.error("您已经设置过银行了");
                }
                ValidatorUtils.validateEntity(paymentForm, BankGroup.class);
                accountEntity.setBank(paymentForm.getBank());
                accountEntity.setBranch(paymentForm.getBranch());
                accountEntity.setBankCardno(paymentForm.getCardNo());
                accountEntity.setBankStatus(1);
                break;
            case "usdt":
                if (accountEntity.getUsdtStatus() == 1) {
                    return R.error("您已经设置过USDT了");
                }
                ValidatorUtils.validateEntity(paymentForm, USDTGroup.class);
                accountEntity.setUsdtToken(paymentForm.getUsdtToken());
                accountEntity.setUsdtStatus(1);
                break;
            default:
                return R.error("选择种类错误");
        }
        accountService.updateById(accountEntity);
        return R.ok("保存成功");
    }


    @Login
    @GetMapping("userInfo")
    public R userInfo(@RequestAttribute("userId") Long userId) {
        AccountEntity account = accountService.queryByUserId(userId);
        if (account != null) {
            AccountMoneyEntity accountMoney = accountMoneyService.queryByUserId(userId);
            account.setParentMoney(accountMoney.getParentMoney());
            account.setSonMoney(accountMoney.getSonMoney());
            account.setPower(accountMoney.getPower());
            account.setPowerSource(accountMoney.getPowerSource());

            account.setHyd(userLtcService.queryHydPerson(userId));
            if (account.getRealStatus() != null && account.getRealStatus() != 0) {
                account.setIdCard(StringUtils.substring(account.getIdCard(), 0, 4) + "*******" + StringUtils.substring(account.getIdCard(), 15));
            }
        }
        List<AccountLogEntity> aList = accountLogService.queryListByUserId(userId);
        UserEntity user = userService.getById(userId);

        long count = teamConfigService.count(new QueryWrapper<TeamConfigEntity>().eq("leader_id", userId).eq("status", 1));

        String secretKey = "";
        if (user.getBindAuthCode() == 0) {
            secretKey = user.getSecretKey();
        }

        return R.ok().put("account", account)
                .put("user", user)
                .put("sk", secretKey)
                .put("aList", aList)
                .put("gAuth",redisUtils.get(Constant.GOOGLE_AUTH,Integer.class))
                .put("leader", count > 0)
                .put("cw", genesisService.getOne(new QueryWrapper<GenesisEntity>().eq("user_id", userId)));
    }

    /**
     * 领取矿机收益
     *
     * @param userId
     * @return
     */
    @Login
    @PostMapping("user/income")
    public R income(@RequestAttribute("userId") Long userId) {
        ltcLogService.income(userId);
        return R.ok();
    }

    /**
     * 身份证认证
     *
     * @param userId
     * @param form
     * @return
     */
    @Login
    @PostMapping("user/auth")
    public R auth(@RequestAttribute("userId") Long userId, @RequestBody UserAuthForm form) {
        ValidatorUtils.validateEntity(form);
        userService.userAuth(userId, form);
        return R.ok();
    }

    /**
     * 谷歌验证码绑定
     *
     * @param userId
     * @param form
     * @return
     */
    @Login
    @PostMapping("user/bindAuthCode")
    public R googleAuthCode(@RequestAttribute("userId") Long userId, @RequestBody GoogleAuthCodeForm form) {
        ValidatorUtils.validateEntity(form);
        UserEntity entity = userService.getById(userId);
        if (entity.getBindAuthCode() == 1) {
            return R.error("您已绑过了");
        }
        if (!GoogleAuthenticatorUtils.verify(entity.getSecretKey(), form.getCode())) {
            return R.error("验证码无效");
        }
        entity.setBindAuthCode(1);
        userService.updateById(entity);
        return R.ok();
    }

    /**
     * 绑定谷歌验证码发送短信
     *
     * @param userId
     * @return
     */
    @Login
    @PostMapping("user/authCode/sms")
    public R googleAuthCodeSms(@RequestAttribute("userId") Long userId) {
        UserEntity entity = userService.getById(userId);
        if (entity.getBindAuthCode() == 1) {
            return R.error("您已绑过了");
        }
        if (StringUtils.isBlank(entity.getMobile())) {
            return R.error("请您先绑定手机号码");
        }
        String mark = "_AUTH_CODE_LIMIT_SMS";
        String val = redisUtils.get(entity.getMobile() + mark);
        if (StringUtils.isNotBlank(val)) {
            return R.error("请不要频繁发送短信");
        }
        //防止重复刷短信
        redisUtils.set(entity.getMobile() + mark, true, 60);
        String code = CharUtil.getRandomNum(6);
        log.error(code);
        //TODO 发送短信
        SmsResult result = smsService.sendWithTemplate(entity.getMobile(), SmsConstant.SMS_CAPTCHA_ALI_TEMPLATE_ID, new String[]{code});
        if (!result.isSuccessful()) {
            redisUtils.delete(entity.getMobile() + mark);
            return R.error("发送失败");
        }
        //缓存
        redisUtils.set(entity.getMobile() + "_" + code, true, 600);
        return R.ok("发送成功");
    }

    /**
     * 谷歌二维码
     *
     * @param response
     * @return
     */
    @Login
    @GetMapping("/qr")
    public R qr(@RequestAttribute("userId") Long userId, HttpServletResponse response) {
        UserEntity entity = userService.getById(userId);
        if (entity.getBindAuthCode() == 1) {
            return R.error("您已绑过了");
        }
        String k = entity.getSecretKey();
        String url = GoogleAuthenticatorUtils.createGoogleAuthQRCodeData("1" + String.format("%09d", userId), "环境保护人人有责", k);
        try {
            QRCodeUtils.writeToStream(url, response.getOutputStream(), 300, 300);
        } catch (Exception e) {
            log.error("生成二维码失败");
        }
        return R.ok();
    }


    /**
     * 忘记密码发送短信
     *
     * @return
     */
    @PostMapping("user/forget/sms")
    public R forgetSms(@RequestBody ForgetSmsForm form) {

        ValidatorUtils.validateEntity(form);
        //图形验证码
        String captcha = redisUtils.get(form.getUuid());
        if (StringUtils.isBlank(captcha)) {
            throw new RRException("图形验证码无效");
        }
        if (!captcha.equals(form.getCaptcha())) {
            throw new RRException("验证码错误");
        }
        UserEntity entity = userService.queryByMobile(form.getMobile());
        if (entity == null) {
            return R.error("账号不存在");
        }
        String mark = "_FORGET_LIMIT_SMS";
        String val = redisUtils.get(entity.getMobile() + mark);
        if (StringUtils.isNotBlank(val)) {
            return R.error("请不要频繁发送短信");
        }
        //防止重复刷短信
        redisUtils.set(entity.getMobile() + mark, true, 60);
        String code = CharUtil.getRandomNum(6);
        log.error(code);
        //TODO 发送短信
        SmsResult result = smsService.sendWithTemplate(entity.getMobile(), SmsConstant.SMS_CAPTCHA_ALI_TEMPLATE_ID, new String[]{code});
        if (!result.isSuccessful()) {
            redisUtils.delete(entity.getMobile() + mark);
            return R.error("发送失败");
        }
        //缓存
        redisUtils.set(entity.getMobile() + "_" + DigestUtils.md5Hex(code), true, 600);
        return R.ok("发送成功");
    }


    /**
     * 忘记密码第一步验证短信
     *
     * @param form
     * @return
     */
    @PostMapping("user/forget/s1")
    public R forgetS1(@RequestBody ForgetStep1Form form) {
        ValidatorUtils.validateEntity(form);
        //图形验证码
        String captcha = redisUtils.get(form.getUuid());
        if (StringUtils.isBlank(captcha)) {
            throw new RRException("图形验证码无效");
        }
        if (!captcha.equals(form.getCaptcha())) {
            throw new RRException("验证码错误");
        }
        String code = redisUtils.get(form.getMobile() + "_" + DigestUtils.md5Hex(form.getCode()));
        if (!"true".equals(code)) {
            throw new RRException("短信验证码无效或超时");
        }
        UserEntity entity = userService.queryByMobile(form.getMobile());
        if (entity == null) {
            return R.error("账号不存在");
        }
        String uuid = DigestUtils.md5Hex(RandomStringUtils.random(10));
        redisUtils.set(uuid, entity.getId(), 60 * 5);
        redisUtils.set(DigestUtils.md5Hex(form.getMobile()), uuid, 60 * 5);
        return R.ok().put("uuid", uuid).put("auth", entity.getBindAuthCode() == 1);
    }


    /**
     * 忘记密码第二步重置密码
     *
     * @param form
     * @return
     */
    @PostMapping("user/forget/s2")
    public R forgetS2(@RequestBody ForgetStep2Form form) {
        ValidatorUtils.validateEntity(form);
        //图形验证码
        String uid = redisUtils.get(form.getUuid());
        if (StringUtils.isBlank(uid)) {
            throw new RRException("重置密码超时，请返回重试");
        }
        Long userId = Long.valueOf(uid);
        UserEntity entity = userService.getById(userId);
        if (entity == null) {
            return R.error("账号不存在");
        }
//        if(entity.getBindAuthCode() == 1){
//            if(form.getCode() == null){
//               return R.error("请输入谷歌验证码");
//            }
//            if(!GoogleAuthenticatorUtils.verify(entity.getSecretKey(), form.getCode())){
//                return R.error("谷歌验证码错误");
//            }
//        }
        String uuid = redisUtils.get(DigestUtils.md5Hex(entity.getMobile()));
        if (StringUtils.isBlank(uuid) || !uuid.equals(form.getUuid())) {
            throw new RRException("重置密码失败，请返回重试");
        }
        entity.setPassword(DigestUtils.sha256Hex(form.getPassword()));
        userService.updateById(entity);
        String token = redisUtils.get(DigestUtils.md5Hex(userId + "_"));
        if (StringUtils.isNotBlank(token)) {
            tokenService.expireToken(token, userId);
        }
        return R.ok("重置密码成功");
    }

    /**
     * 修改支付密码
     *
     * @param user
     * @param form
     * @return
     */
    @Login
    @PostMapping("user/pay/password")
    public R alterPayPassword(@LoginUser UserEntity user, @RequestBody PayPasswordForm form) {
        ValidatorUtils.validateEntity(form);
//        Integer auth = redisUtils.get(Constant.GOOGLE_AUTH,Integer.class);
//        if(auth != null && auth == 1) {
//            //图形验证码
//            if (user.getBindAuthCode() == 1) {
//                if (form.getCode() == null) {
//                    return R.error("请输入谷歌验证码");
//                }
//                if (!GoogleAuthenticatorUtils.verify(user.getSecretKey(), form.getCode())) {
//                    return R.error("谷歌验证码错误");
//                }
//            }
//        }
        if (form.getType() == null || form.getType() == 0) {
            if(StringUtils.isBlank(form.getOldPassword())){
                return R.error("原密码不能为空");
            }

            if (!user.getPayPassword().equals(DigestUtils.sha256Hex(form.getOldPassword()))) {
                return R.error("原密码错误");
            }

            if (form.getOldPassword().equals(form.getPassword())) {
                return R.error("原密码与新密码不可相同");
            }
        } else {
            //验证短信验证码
            String code = redisUtils.get(user.getMobile() + "_" + form.getCaptcha());
            if (!"true".equals(code)) {
                throw new RRException("短信验证码无效或超时");
            }

            if (user.getPayPassword().equals(DigestUtils.sha256Hex(form.getPassword()))) {
                return R.error("原密码与新密码不可相同");
            }


            redisUtils.delete(user.getMobile() + "_" + form.getCaptcha());
        }

        user.setPayPassword(DigestUtils.sha256Hex(form.getPassword()));
        userService.updateById(user);
        return R.ok("操作成功");
    }


    /**
     * 登录密码
     *
     * @param user
     * @param form
     * @return
     */
    @Login
    @PostMapping("user/password")
    public R alterPassword(@LoginUser UserEntity user, @RequestBody LoginPasswordForm form) {
        ValidatorUtils.validateEntity(form);

//        Integer auth = redisUtils.get(Constant.GOOGLE_AUTH,Integer.class);
//        if(auth != null && auth == 1) {
//            //图形验证码
//            if (user.getBindAuthCode() == 1) {
//                if (form.getCode() == null) {
//                    return R.error("请输入谷歌验证码");
//                }
//                if (!GoogleAuthenticatorUtils.verify(user.getSecretKey(), form.getCode())) {
//                    return R.error("谷歌验证码错误");
//                }
//            }
//        }

        if (!user.getPassword().equals(DigestUtils.sha256Hex(form.getOldPassword()))) {
            return R.error("原密码错误");
        }

        if (form.getOldPassword().equals(form.getPassword())) {
            return R.error("原密码与新密码不可相同");
        }

        user.setPassword(DigestUtils.sha256Hex(form.getPassword()));
        userService.updateById(user);
        return R.ok("操作成功");
    }

    @Login
    @RequestMapping("user/getPowerList")
    public R getPowerList(@ApiIgnore @LoginUser UserEntity user, @RequestParam Map<String, Object> params) {

        IPage<AccountLogEntity> page = accountLogService.page(
                new Query<AccountLogEntity>().getPage(params),
                new QueryWrapper<AccountLogEntity>().eq("money_kind", 4).eq("user_id", user.getId())
        );
        return R.ok().put("page", new PageUtils(page));
    }

    @Login
    @RequestMapping("user/getAccountLogList")
    public R getAccountLogList(@RequestAttribute Long userId, @RequestParam Map<String, Object> params) {
        params.put("userId", userId);
        PageUtils page = accountLogService.queryPage(params);
        return R.ok().put("page", page).put("type", LogType.getMap());
    }

    /**
     * 申请世代
     *
     * @param userId
     * @return
     */
    @Login
    @PostMapping("user/sd/publish")
    public R sdpublish(@RequestAttribute("userId") Long userId, @RequestBody SdFormStep1 sdFormStep1) {
        ValidatorUtils.validateEntity(sdFormStep1);
        AccountEntity accountEntity = accountService.queryByUserId(userId);
        if (accountEntity.getSdStatus() != SdStatus.NONE.getValue() && accountEntity.getSdStatus() != SdStatus.FAIL.getValue()) {
            return R.error("不是可申请状态");
        }


        if (accountService.getOne(new QueryWrapper<AccountEntity>().eq("sd_province_city", sdFormStep1.getProvinceCity())) != null) {
            return R.error("已经有人选择该地");
        }

        accountEntity.setSdProvinceCity(sdFormStep1.getProvinceCity());

        accountEntity.setSdStatus(SdStatus.WAIT_VERIFY1.getValue());


        accountService.updateById(accountEntity);

        return R.ok("申请市代" + sdFormStep1.getProvinceCity() + "成功");
    }

    /**
     * 开始打款上传图片
     *
     * @param userId
     * @return
     */
    @Login
    @PostMapping("user/sd/photo")
    public R photo(@RequestAttribute("userId") Long userId, @RequestBody SdFormStep2 sdFormStep2) {
        ValidatorUtils.validateEntity(sdFormStep2);
        AccountEntity accountEntity = accountService.queryByUserId(userId);
        if (accountEntity.getSdStatus() != SdStatus.WAIT_USDT.getValue()) {
            return R.error("不是可申请状态");
        }

        accountEntity.setSdPhoto(sdFormStep2.getPhoto());

        accountEntity.setSdStatus(SdStatus.WAIT_VERITY2.getValue());

        accountService.updateById(accountEntity);

        return R.ok("上传成功,待后台确认");
    }

    /**
     * 获取我的激活码列表
     *
     * @param userId
     * @return
     */
    @Login
    @GetMapping("user/activeCode/list")
    public R activeCodeList(@RequestAttribute("userId") Long userId) {
        List<ActivationCodeEntity> list = activationCodeService.queryAll(userId);
        Integer days = redisUtils.get(Constant.ACTIVATION_CODE_VALID_TIME, Integer.class);

        list.forEach(item -> {
            if (DateUtils.addDateDays(item.getCreateTime(), days).before(new Date())) {
                if(item.getStatus()==0){
                    item.setStatus(-1);
                    activationCodeService.updateById(item);
                }
            }
        });
        list = activationCodeService.queryAll(userId);
        long activeCodeCt = getActiveCodeCount(userId);
        return R.ok().put("list", list).put("activeCodeCt", activeCodeCt);
    }


    /**
     *
     * @param userId
     * @return
     */
    @Login
    @GetMapping("user/downUserDaytrans")
    public R downuserdaytrans(@RequestAttribute("userId") Long userId, @RequestParam Map<String, Object> params) {
        List<UserTeamEntity> list = userTeamService.list(new QueryWrapper<UserTeamEntity>().like("mark", "%," + userId + ",%"));
        List<Long> ids = new ArrayList<Long>();
        for (UserTeamEntity userTeamEntity : list) {
            if (!userId.equals(userTeamEntity.getUserId())) {
                ids.add(userTeamEntity.getUserId());
            }
        }
        if (ids.size() == 0) {
            return R.error("暂无数据");
        }

        IPage<UserDaytransEntity> page = userDaytransService.selectPageVo(new Query<UserDaytransEntity>().getPage(params), ids.toArray());
        return R.ok().put("page", page);

    }

    /**
     *
     * @param userId
     * @return
     */
    @Login
    @GetMapping("user/downUserDaybills")
    public R downuserdaybills(@RequestAttribute("userId") Long userId, @RequestParam Map<String, Object> params) {
        List<UserTeamEntity> list = userTeamService.list(new QueryWrapper<UserTeamEntity>().like("mark", "," + userId + ","));
        List<Long> ids = new ArrayList<Long>();
        for (UserTeamEntity userTeamEntity : list) {
            if (!userId.equals(userTeamEntity.getUserId())) {
                ids.add(userTeamEntity.getUserId());
            }
        }
        if (ids.size() == 0) {
            return R.error("暂无数据");
        }


        IPage<UserDaybillsEntity> page = userDaybillsService.selectPageVo(new Query<UserDaybillsEntity>().getPage(params), ids.toArray());
        return R.ok().put("page", page);
    }

    /**
     *
     * @param userId
     * @return
     */
    @Login
    @GetMapping("user/downUserList")
    public R downUserList(@RequestAttribute("userId") Long userId, @RequestParam Map<String, Object> params) {
        List<UserTeamEntity> list = userTeamService.list(new QueryWrapper<UserTeamEntity>().like("mark", "," + userId + ","));
        List<Long> ids = new ArrayList<Long>();
        for (UserTeamEntity userTeamEntity : list) {
            if (!userId.equals(userTeamEntity.getUserId())) {
                ids.add(userTeamEntity.getUserId());
            }
        }
        if (ids.size() == 0) {
            return R.error("暂无数据");
        }

        IPage<AccountEntity> page = accountService.selectPageVo(new Query<AccountEntity>().getPage(params), ids.toArray());

        //   accountEntity.setHyd(userLtcService.queryHydPerson(accountEntity.getUserId()));

        return R.ok().put("page", page);
    }

    @GetMapping("test")
    public List<AccountEntity> test(String userId) {
        String mark = ",5,12";
        String[] inviterUserIds = mark.substring(1).split(",");
        return accountService.list(new QueryWrapper<AccountEntity>().in("user_id", inviterUserIds).orderByDesc("user_id"));

    }

    @Login
    @RequestMapping("user/teamList")
    public R userTeamList(@RequestAttribute("userId") Long userId, @RequestParam Map<String, Object> params) {
        //  Long teamId = accountService.queryByUserId(userId).getTeamId();
        TeamConfigEntity teamConfigEntity = teamConfigService.getOne(new QueryWrapper<TeamConfigEntity>().eq("leader_id", userId).eq("status", 1));
        List<TeamConfigEntity> teamConfigEntitys = teamConfigService.list();
        //设置战队队长为null
        AccountEntity accountLeader = null;
        //先查询战队长
        if (teamConfigEntity == null) {
            //查询用户邀请树
            UserTeamEntity userTeamEntity = userTeamService.queryByUserId(userId);
            String[] inviterUserIds = userTeamEntity.getMark().substring(1).split(",");
            //倒叙 这样查询出来的人离他最近 这里根据注册id来 生产裤不会出错
            List<AccountEntity> accountEntities = accountService.list(new QueryWrapper<AccountEntity>().in("user_id", inviterUserIds).orderByDesc("user_id"));
            look:
            for (int i = 0; i < accountEntities.size(); i++) {
                AccountEntity accountEntity = accountEntities.get(i);
                for (TeamConfigEntity ConfigEntity : teamConfigEntitys) {
                    if (ConfigEntity.getLeaderId().equals(accountEntity.getUserId())) {
                        //第一个就是战队队长
                        accountLeader = accountEntity;
                        break look;
                    }
                }
            }
            //往上查第一个战队队长
        } else {
            accountLeader = accountService.queryByUserId(userId);
        }
        if (accountLeader == null) {
            return R.error("并无战队");
        }
        teamConfigEntity = teamConfigService.getOne(new QueryWrapper<TeamConfigEntity>().eq("leader_id", accountLeader.getUserId()).eq("status", 1));
        if (teamConfigEntity == null) {
            return R.error("并无战队");
        }
        accountLeader = accountService.queryByUserId(accountLeader.getUserId());
        if (teamConfigEntity.getLeaderId().equals(accountLeader.getUserId())) {
            teamConfigEntity.setLeaderLevelName(accountLeader.getLevelName());
        }
        //  List<AccountEntity> teamAccountList = accountService.list(new QueryWrapper<AccountEntity>().in
        //通过树结构往下查邀请的所有人
        List<UserTeamEntity> list = userTeamService.list(new QueryWrapper<UserTeamEntity>().like("mark", "," + userId + ","));
        if (list.size() == 0) {
            return R.error("没有战队");
        }
        //必要处理
        List<Long> ids = new ArrayList<Long>();
        for (UserTeamEntity userTeamEntity : list) {
            if (!userId.equals(userTeamEntity.getUserId())) {
                ids.add(userTeamEntity.getUserId());
            }
        }
        //根据所有id查询这些人account信息
        ids.add(userId);
        List<AccountEntity> teamAccountList = accountService.queryByIds(ids.toArray());
        // List<AccountEntity> userAccount = accountService.queryByIds(userId);
        //数据组装
        List<LevelConfiigEntity> levelConfiigs = levelConfiigService.list();
        for (AccountEntity accountEntity : teamAccountList) {
            accountEntity.setHyd(userLtcService.queryHydPerson(accountEntity.getUserId()));
            accountEntity.setWorkingLtc(userLtcService.queryWorkingLtc(accountEntity.getUserId()));
            for (LevelConfiigEntity levelConfiigEntity : levelConfiigs) {
                if (accountEntity.getLevelId().equals(levelConfiigEntity.getId())) {
                    accountEntity.setLevelName(levelConfiigEntity.getName());
                }
            }
            teamConfigEntity.setZhyd(teamConfigEntity.getZhyd().add(accountEntity.getHyd()));
            teamConfigEntity.setTeamCount(teamConfigEntity.getTeamCount() + 1);
            accountEntity.setDirectUserCount(userTeamService.count(new QueryWrapper<UserTeamEntity>().eq("inviter_user_id", accountEntity.getUserId())));
        }
        return R.ok().put("team", teamConfigEntity).put("teamUserList", teamAccountList);
    }

    /**
     * 领取激活码
     *
     * @param userId
     * @return
     */
    @Login
    @GetMapping("user/activeCode/get")
    public R execActiveCode(@RequestAttribute("userId") Long userId) {
        long activeCodeCt = getActiveCodeCount(userId);
        if (activeCodeCt == 0) {
            throw new RRException("您没有可领取的激活码");
        }
        return activationCodeService.generate(userId, activeCodeCt) ? R.ok("操作成功") : R.error("操作失败");
    }

    private long getActiveCodeCount(Long userId) {
        long activeCodeCt = 0;
        Double rate = redisUtils.get(Constant.BUY_LTC_GET_ACTIVE_CODE_CT, Double.class);
        if (rate == null) {
            throw new RRException("请联系客服，参数配置错误");
        }
        BigDecimal amount = userLtcService.getNotActiveCodeTotalAmount(userId);
        if (amount != null && amount.signum() > 0) {
            activeCodeCt = amount.multiply(BigDecimal.valueOf(rate)).longValue();
        }
        return activeCodeCt;
    }

    /**
     * 我的战队申请
     *
     * @param userId
     * @param form
     * @return
     */
    @Login
    @PostMapping("user/apply/team/captain")
    public R applyTeamCaptain(@RequestAttribute("userId") Long userId, @RequestBody ApplyTeamCaptainForm form) {

        AccountEntity account = accountService.queryByUserId(userId);
        if (account == null) {
            return R.error("账户异常");
        }

        if (account.getLevelId() < 2) {
            return R.error("抱歉您的等级未满足申请条件");
        }
        TeamConfigEntity teamConfig = teamConfigService.queryByUserId(userId);
        if (teamConfig != null) {
            return R.error("您已经申请过了");
        }

        teamConfig = new TeamConfigEntity();
        teamConfig.setLeaderId(userId);
        teamConfig.setCreateTime(new Date());
        teamConfig.setTeam(form.getName());
        teamConfig.setStatus(0);
        teamConfig.setCreateUserId(userId);
        teamConfig.setSlogan(form.getSlogan());
        teamConfig.setTeamWechat(form.getWechat());

        return teamConfigService.save(teamConfig) ? R.ok("操作成功") : R.error("操作失败");
    }

    @Login
    @GetMapping("user/my/team/captain")
    public R myTeamCaptain(@RequestAttribute("userId") Long userId) {
        return R.ok().put("info", teamConfigService.queryByUserId(userId));
    }

    @Login
    @PostMapping("user/trade/sms")
    public R tradeSms(@RequestAttribute("userId") Long userId) {
        UserEntity entity = userService.getById(userId);
        if (StringUtils.isBlank(entity.getMobile())) {
            return R.error("请您先绑定手机号码");
        }
        String mark = "_TRADE_PASS_SMS";
        String val = redisUtils.get(entity.getMobile() + mark);
        if (StringUtils.isNotBlank(val)) {
            return R.error("请不要频繁发送短信");
        }
        //防止重复刷短信
        redisUtils.set(entity.getMobile() + mark, true, 60);
        String code = CharUtil.getRandomNum(6);
        log.error(code);
        SmsResult result = smsService.sendWithTemplate(entity.getMobile(), SmsConstant.SMS_CAPTCHA_ALI_TEMPLATE_ID, new String[]{code});
        if (!result.isSuccessful()) {
            redisUtils.delete(entity.getMobile() + mark);
            return R.error("发送失败");
        }
        //缓存
        redisUtils.set(entity.getMobile() + "_" + code, true, 600);
        return R.ok("发送成功");
    }

}
