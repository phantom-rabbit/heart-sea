package com.kw.modules.app.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kw.common.utils.Query;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kw.modules.app.entity.PurposeBuyEntity;
import com.kw.modules.app.service.PurposeBuyService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;



/**
 * 求购记录
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@RestController
@RequestMapping("app/purposebuy")
public class PurposeBuyController {
    @Autowired
    private PurposeBuyService purposeBuyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:purposebuy:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = purposeBuyService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("app:purposebuy:info")
    public R info(@PathVariable("id") Integer id){
		PurposeBuyEntity purposeBuy = purposeBuyService.getById(id);

        return R.ok().put("purposeBuy", purposeBuy);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:purposebuy:save")
    public R save(@RequestBody PurposeBuyEntity purposeBuy){
		purposeBuyService.save(purposeBuy);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:purposebuy:update")
    public R update(@RequestBody PurposeBuyEntity purposeBuy){
		purposeBuyService.updateById(purposeBuy);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:purposebuy:delete")
    public R delete(@RequestBody Integer[] ids){
		purposeBuyService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
