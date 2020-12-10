package com.kw.modules.app.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kw.modules.app.entity.SmsLogEntity;
import com.kw.modules.app.service.SmsLogService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;



/**
 * 短信记录
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-03-31 23:37:43
 */
@RestController
@RequestMapping("app/smslog")
public class SmsLogController {
    @Autowired
    private SmsLogService smsLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:smslog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = smsLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("app:smslog:info")
    public R info(@PathVariable("id") Long id){
        SmsLogEntity smsLog = smsLogService.getById(id);

        return R.ok().put("smsLog", smsLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:smslog:save")
    public R save(@RequestBody SmsLogEntity smsLog){
        smsLogService.save(smsLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:smslog:update")
    public R update(@RequestBody SmsLogEntity smsLog){
        smsLogService.updateById(smsLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:smslog:delete")
    public R delete(@RequestBody Long[] ids){
        smsLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
