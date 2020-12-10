package com.kw.modules.app.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kw.common.validator.ValidatorUtils;
import com.kw.modules.app.entity.LtcConfigEntity;
import com.kw.modules.app.form.Award;
import com.kw.modules.app.service.LtcConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kw.modules.app.entity.UserLtcEntity;
import com.kw.modules.app.service.UserLtcService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;



/**
 * 用户矿机表
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@RestController
@RequestMapping("app/userltc")
public class UserLtcController {
    @Autowired
    private UserLtcService userLtcService;


    @Autowired
    private LtcConfigService ltcConfigService;

    /**
     * 列表
     */
    @RequestMapping("/award")
    public R award(@RequestBody Award award){
        ValidatorUtils.validateEntity(award);
       // PageUtils page = userLtcService.queryPage(params);
        LtcConfigEntity ltcConfigEntity  = ltcConfigService.getById(award.getAwardLtcId());
        if(ltcConfigEntity.getId()!=null){
            //增加用户矿机
            UserLtcEntity userLtcEntity = new UserLtcEntity(award.getUserId(),ltcConfigEntity, "用户购买") ;
            userLtcEntity.setIsAward(1);
            userLtcService.save(userLtcEntity);

        }


        return R.ok();
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:userltc:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userLtcService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/reportlist")
    @RequiresPermissions("app:userltc:list")
    public R reportlist(@RequestParam Map<String, Object> params){
        List<HashMap> list = userLtcService.queryReportList(params);
        int sum = 0;
        for(HashMap map : list){
            sum =sum+ Integer.parseInt(map.get("reportCount").toString());
        }

        return R.ok().put("list", list).put("sum",sum);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("app:userltc:info")
    public R info(@PathVariable("id") Integer id){
		UserLtcEntity userLtc = userLtcService.getById(id);

        return R.ok().put("userLtc", userLtc);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:userltc:save")
    public R save(@RequestBody UserLtcEntity userLtc){
		userLtcService.save(userLtc);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:userltc:update")
    public R update(@RequestBody UserLtcEntity userLtc){
		userLtcService.updateById(userLtc);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:userltc:delete")
    public R delete(@RequestBody Integer[] ids){
		userLtcService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
