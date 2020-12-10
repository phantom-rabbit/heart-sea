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

import com.kw.modules.app.entity.UserTeamEntity;
import com.kw.modules.app.service.UserTeamService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;



/**
 * 用户战队层级关系
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@RestController
@RequestMapping("app/userteam")
public class UserTeamController {
    @Autowired
    private UserTeamService userTeamService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:userteam:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userTeamService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userid}")
    @RequiresPermissions("app:userteam:info")
    public R info(@PathVariable("userid") Integer userid){
		UserTeamEntity userTeam = userTeamService.getById(userid);

        return R.ok().put("userTeam", userTeam);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:userteam:save")
    public R save(@RequestBody UserTeamEntity userTeam){
		userTeamService.save(userTeam);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:userteam:update")
    public R update(@RequestBody UserTeamEntity userTeam){
		userTeamService.updateById(userTeam);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:userteam:delete")
    public R delete(@RequestBody Integer[] userids){
		userTeamService.removeByIds(Arrays.asList(userids));

        return R.ok();
    }

}
