package com.kw.controller;

/**
 * Created by Medivh on 2020/4/2.
 * 愿我出走半生,归来仍是少年
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kw.common.annotation.Login;
import com.kw.common.annotation.LoginUser;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;
import com.kw.entity.*;
import com.kw.service.AccountService;
import com.kw.service.LtcConfigService;
import com.kw.service.LtcLogService;
import com.kw.service.UserLtcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.Map;

/**
 * 登录接口

 */
@RestController
@RequestMapping("/api")
@Api(tags="转换器接口")
public class ApiTransferController {

    @Autowired
    private LtcConfigService ltcConfigService;

    @Autowired
    private UserLtcService userLtcService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private LtcLogService ltcLogService;

    /**
     * 转换器列表
     */
    @RequestMapping("/ltcConfig/list")
    @ApiOperation(value="获取矿机配置信息", response=LtcConfigEntity.class)
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = ltcConfigService.queryPage(params);
        return R.ok().put("page", page);
    }

    @Login
    @PostMapping("/ltc/buy")
    @ApiOperation(value="兑换矿机")
    public R buy(@ApiIgnore @LoginUser UserEntity user,String configId){
       return  accountService.buyLtc(user.getId(),configId);
    }
    @Login
    @PostMapping("/ltc/start")
    @ApiOperation(value="启动矿机")
    public R start(@ApiIgnore @LoginUser UserEntity user,String id){
        UserLtcEntity userLtcEntity = userLtcService.getById(id);
        if(!userLtcEntity.getUserId().equals(user.getId())){
            return R.error("不能启动不是你的矿机");
        }
        if(userLtcEntity.getLtcStatus()!=0){
            return R.error("矿机不是可以启动状态");
        }

        userLtcEntity.setStartTime(new Date());
        userLtcEntity.setLtcStatus(1);
        userLtcEntity.setStartTime( new Date());
        LtcLogEntity ltcLog = new LtcLogEntity();
        ltcLog.setIncome(userLtcEntity.getIncomeParentPerday());
        ltcLog.setIncomeCoin(userLtcEntity.getIncomeSonPerday());
        ltcLog.setLtcId(userLtcEntity.getId());
        ltcLog.setUserId(userLtcEntity.getUserId());
        ltcLog.setCreateDate(new Date());
        userLtcEntity.setRunDays(userLtcEntity.getRunDays()+1);
        userLtcService.start(ltcLog,userLtcEntity);
        return R.ok("启动成功");

    }

    @Login
    @RequestMapping("/ltc/list")
    @ApiOperation(value="获取用户矿机列表信息", response=UserLtcEntity.class)
    public R userInfo(@ApiIgnore @LoginUser UserEntity user,@RequestParam Map<String, Object> params){
        params.put("user_id",user.getId());
        PageUtils page = userLtcService.queryPage(params);
        return R.ok().put("page", page);
    }
}
