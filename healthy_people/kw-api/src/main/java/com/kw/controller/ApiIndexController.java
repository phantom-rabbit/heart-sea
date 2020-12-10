package com.kw.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.kw.common.annotation.Login;
import com.kw.common.utils.*;
import com.kw.entity.AccountEntity;
import com.kw.entity.AccountMoneyEntity;
import com.kw.entity.NoticeEntity;
import com.kw.service.AccountMoneyService;
import com.kw.service.AccountService;
import com.kw.service.LtcLogService;
import com.kw.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiIndexController {

    @Autowired
    private LtcLogService ltcLogService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private AccountMoneyService accountMoneyService;

    /**
     * 平台usdttoken
     * @return
     */

    @RequestMapping("/sd/info")
    public R sdinfo(){
        HashMap map = new HashMap();

        map.put("sdOpen",redisUtils.get(Constant.SD_OPEN));;
        map.put("usdtToken",redisUtils.get(Constant.USDT_TOKEN));
        map.put("sdStep2",redisUtils.get(Constant.SD_STEP_2));
        map.put("sdStep3",redisUtils.get(Constant.SD_STEP_3));

        return R.ok().put("sdInfo",map);
       // minMaxMap.put("proposedPrice",redisUtils.get(RedisKeys.getSysConfigKey(Constant.PLAT_AVG_PRICE))==null?"暂无":redisUtils.get(RedisKeys.getSysConfigKey(Constant.PLAT_AVG_PRICE)));
    }

    /**
     * 查询用户的收益
     * @param userId
     * @return
     */
    @Login
    @GetMapping("home/index")
    public R index(@RequestAttribute("userId") Long userId){
        BigDecimal income = ltcLogService.findLtcIncomeTotal(userId);
        AccountEntity account = accountService.queryByUserId(userId);
        AccountMoneyEntity accountMoney = accountMoneyService.queryByUserId(userId);
        account.setParentMoney(accountMoney.getParentMoney());
        account.setSonMoney(accountMoney.getSonMoney());
        account.setPower(accountMoney.getPower());
        account.setPowerSource(accountMoney.getPowerSource());
        List<NoticeEntity> list = noticeService.list(new QueryWrapper<NoticeEntity>().eq("is_show", 1).orderByDesc("update_date"));
        //查询公告
        return R.ok().put("income", income).put("account", account).put("noticeList", list);
    }


    @RequestMapping("notice/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = noticeService.queryPage(params);
        return R.ok().put("page", page);
    }

}
