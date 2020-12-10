package com.kw.modules.app.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.kw.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kw.modules.app.entity.VipConfigEntity;
import com.kw.modules.app.service.VipConfigService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;



/**
 * Vip配置表
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@RestController
@RequestMapping("app/vipconfig")
public class VipConfigController extends AbstractController {
    @Autowired
    private VipConfigService vipConfigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:vipconfig:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = vipConfigService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("app:vipconfig:info")
    public R info(@PathVariable("id") Integer id){
		VipConfigEntity vipConfig = vipConfigService.getById(id);

        return R.ok().put("vipConfig", vipConfig);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:vipconfig:save")
    public R save(@RequestBody VipConfigEntity vipConfig){
        vipConfig.setCreateTime(new Date());
        vipConfig.setCreateUserId(getUserId());
		vipConfigService.save(vipConfig);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:vipconfig:update")
    public R update(@RequestBody VipConfigEntity vipConfig){
		vipConfigService.updateById(vipConfig);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:vipconfig:delete")
    public R delete(@RequestBody Integer[] ids){
		vipConfigService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
