package com.kw.modules.app.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kw.modules.app.entity.UserEntity;
import com.kw.modules.app.service.UserService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;



/**
 * 会员记录
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:15:46
 */
@RestController
@RequestMapping("app/user")
public class UserController {
    @Autowired
    private UserService userService;


    /**
     * 列表
     */
    @RequestMapping("/reportlist")
    @RequiresPermissions("app:userltc:list")
    public R reportlist(@RequestParam Map<String, Object> params){
        List<HashMap> list = userService.queryReportList(params);


        return R.ok().put("list", list);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:user:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("app:user:info")
    public R info(@PathVariable("id") Long id){
		UserEntity user = userService.getById(id);

        return R.ok().put("user", user);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:user:save")
    public R save(@RequestBody UserEntity user){
		userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:user:update")
    public R update(@RequestBody UserEntity user){
		userService.updateById(user);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:user:delete")
    public R delete(@RequestBody Long[] ids){
		userService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 所有用户信息
     * @return
     */
    @RequestMapping("/all")
    @RequiresPermissions("app:user:all")
    public R list(){
        List<UserEntity> list = userService.list();
        return R.ok().put("list", list);
    }

}
