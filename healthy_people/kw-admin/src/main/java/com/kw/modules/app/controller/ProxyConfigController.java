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

import com.kw.modules.app.entity.ProxyConfigEntity;
import com.kw.modules.app.service.ProxyConfigService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;



/**
 * 代理表
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@RestController
@RequestMapping("app/proxyconfig")
public class ProxyConfigController {
    @Autowired
    private ProxyConfigService proxyConfigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:proxyconfig:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = proxyConfigService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("app:proxyconfig:info")
    public R info(@PathVariable("id") Integer id){
		ProxyConfigEntity proxyConfig = proxyConfigService.getById(id);

        return R.ok().put("proxyConfig", proxyConfig);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:proxyconfig:save")
    public R save(@RequestBody ProxyConfigEntity proxyConfig){
		proxyConfigService.save(proxyConfig);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:proxyconfig:update")
    public R update(@RequestBody ProxyConfigEntity proxyConfig){
		proxyConfigService.updateById(proxyConfig);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:proxyconfig:delete")
    public R delete(@RequestBody Integer[] ids){
		proxyConfigService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
