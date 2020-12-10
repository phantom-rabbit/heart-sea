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

import com.kw.modules.app.entity.LtcConfigEntity;
import com.kw.modules.app.service.LtcConfigService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;



/**
 * 矿机配置表
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@RestController
@RequestMapping("app/ltcconfig")
public class LtcConfigController extends AbstractController {
    @Autowired
    private LtcConfigService ltcConfigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:ltcconfig:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = ltcConfigService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("app:ltcconfig:info")
    public R info(@PathVariable("id") Integer id){
		LtcConfigEntity ltcConfig = ltcConfigService.getById(id);

        return R.ok().put("ltcConfig", ltcConfig);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:ltcconfig:save")
    public R save(@RequestBody LtcConfigEntity ltcConfig){
        ltcConfig.setCreateTime(new Date());
        ltcConfig.setCreateUserId(getUserId());
		ltcConfigService.save(ltcConfig);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:ltcconfig:update")
    public R update(@RequestBody LtcConfigEntity ltcConfig){
		ltcConfigService.updateById(ltcConfig);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:ltcconfig:delete")
    public R delete(@RequestBody Integer[] ids){
		ltcConfigService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
