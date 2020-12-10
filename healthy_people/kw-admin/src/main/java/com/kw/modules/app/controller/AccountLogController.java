package com.kw.modules.app.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kw.modules.app.entity.AccountLogEntity;
import com.kw.modules.app.service.AccountLogService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;



/**
 * 资金记录表
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@RestController
@RequestMapping("app/accountlog")
public class AccountLogController {
    @Autowired
    private AccountLogService accountLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:accountlog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = accountLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("app:accountlog:info")
    public R info(@PathVariable("id") Integer id){
		AccountLogEntity accountLog = accountLogService.getById(id);

        return R.ok().put("accountLog", accountLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:accountlog:save")
    public R save(@RequestBody AccountLogEntity accountLog){
		accountLogService.save(accountLog);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:accountlog:update")
    public R update(@RequestBody AccountLogEntity accountLog){
		accountLogService.updateById(accountLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:accountlog:delete")
    public R delete(@RequestBody Integer[] ids){
		accountLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
