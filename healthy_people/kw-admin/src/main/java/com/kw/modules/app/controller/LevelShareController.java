package com.kw.modules.app.controller;




import java.util.*;

import com.kw.modules.app.entity.LevelShareEntity;
import com.kw.modules.app.service.AccountLogService;
import com.kw.modules.app.service.AccountService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.kw.modules.sys.controller.AbstractController;

import com.kw.modules.app.service.LevelShareService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;



/**
 * 分红汇总表
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-09 23:01:59
 */
@RestController
@RequestMapping("app/levelshare")
public class LevelShareController extends AbstractController{
    @Autowired
    private LevelShareService levelShareService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountLogService accountLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:levelshare:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = levelShareService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("app:levelshare:info")
    public R info(@PathVariable("id") Long id){
		LevelShareEntity levelShare = levelShareService.getById(id);

        return R.ok().put("levelShare", levelShare);
    }


    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:levelshare:save")
    public R save(@RequestBody LevelShareEntity levelShare){

        levelShare.setCreateDate(new Date());
		levelShareService.save(levelShare);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:levelshare:update")
    public R update(@RequestBody LevelShareEntity levelShare){
		levelShareService.updateById(levelShare);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:levelshare:delete")
    public R delete(@RequestBody Long[] ids){
		levelShareService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/share")
    @RequiresPermissions("app:levelshare:save")
    public R share(@RequestBody Long[] ids){


        levelShareService.share(ids);



        return R.ok();
    }



}
