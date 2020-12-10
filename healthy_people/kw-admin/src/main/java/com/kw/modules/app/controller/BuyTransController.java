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

import com.kw.modules.app.entity.BuyTransEntity;
import com.kw.modules.app.service.BuyTransService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;



/**
 * 交易表
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@RestController
@RequestMapping("app/buytrans")
public class BuyTransController {
    @Autowired
    private BuyTransService buyTransService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:buytrans:list")
    public R list(@RequestParam Map<String, Object> params){


        PageUtils page = buyTransService.queryPage(params);


        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("app:buytrans:info")
    public R info(@PathVariable("id") Integer id){
		BuyTransEntity buyTrans = buyTransService.getById(id);

        return R.ok().put("buyTrans", buyTrans);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:buytrans:save")
    public R save(@RequestBody BuyTransEntity buyTrans){
		buyTransService.save(buyTrans);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:buytrans:update")
    public R update(@RequestBody BuyTransEntity buyTrans){
		buyTransService.updateById(buyTrans);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:buytrans:delete")
    public R delete(@RequestBody Integer[] ids){
		buyTransService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 删除
     */

    @RequestMapping("/cancle")
    @RequiresPermissions("app:buytrans:save")
    public R cancle(@RequestBody  Integer id){
        buyTransService.cancle(id);

        return R.ok();
    }


    /**
     * 删除
     */

    @RequestMapping("/confirm")
    @RequiresPermissions("app:buytrans:save")
    public R confirm(@RequestBody  Long id){
       return  buyTransService.confirm(id);

    }


}
