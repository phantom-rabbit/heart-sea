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

import com.kw.modules.app.entity.TeamConfigEntity;
import com.kw.modules.app.service.TeamConfigService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;



/**
 * 战队配置表
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@RestController
@RequestMapping("app/teamconfig")
public class TeamConfigController extends AbstractController {
    @Autowired
    private TeamConfigService teamConfigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:teamconfig:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = teamConfigService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("app:teamconfig:info")
    public R info(@PathVariable("id") Integer id){
		TeamConfigEntity teamConfig = teamConfigService.getById(id);

        return R.ok().put("teamConfig", teamConfig);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:teamconfig:save")
    public R save(@RequestBody TeamConfigEntity teamConfig){
        teamConfig.setCreateTime(new Date());
        teamConfig.setCreateUserId(getUserId());
		teamConfigService.save(teamConfig);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:teamconfig:update")
    public R update(@RequestBody TeamConfigEntity teamConfig){
		teamConfigService.updateById(teamConfig);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:teamconfig:delete")
    public R delete(@RequestBody Integer[] ids){
		teamConfigService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
