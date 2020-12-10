package com.kw.modules.app.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kw.modules.app.entity.LevelConfiigAwrdLtcEntity;
import com.kw.modules.app.service.LevelConfiigAwrdLtcService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;



/**
 * 级别配置奖励
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 15:30:54
 */
@RestController
@RequestMapping("app/levelconfiigawrdltc")
public class LevelConfiigAwrdLtcController {
    @Autowired
    private LevelConfiigAwrdLtcService levelConfiigAwrdLtcService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:levelconfiigawrdltc:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = levelConfiigAwrdLtcService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("app:levelconfiigawrdltc:info")
    public R info(@PathVariable("id") Integer id){
		LevelConfiigAwrdLtcEntity levelConfiigAwrdLtc = levelConfiigAwrdLtcService.getById(id);

        return R.ok().put("levelConfiigAwrdLtc", levelConfiigAwrdLtc);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:levelconfiigawrdltc:save")
    public R save(@RequestBody LevelConfiigAwrdLtcEntity levelConfiigAwrdLtc){
        levelConfiigAwrdLtc.setCreateTime(new Date());
		levelConfiigAwrdLtcService.save(levelConfiigAwrdLtc);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:levelconfiigawrdltc:update")
    public R update(@RequestBody LevelConfiigAwrdLtcEntity levelConfiigAwrdLtc){
		levelConfiigAwrdLtcService.updateById(levelConfiigAwrdLtc);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:levelconfiigawrdltc:delete")
    public R delete(@RequestBody Integer[] ids){
		levelConfiigAwrdLtcService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
