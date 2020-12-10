package com.kw.modules.app.controller;




import java.util.Arrays;
import java.util.Map;

import com.kw.modules.app.entity.LevelShareDetailEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.kw.modules.sys.controller.AbstractController;

import com.kw.modules.app.service.LevelShareDetailService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;



/**
 * 分红表
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-09 23:01:59
 */
@RestController
@RequestMapping("app/levelsharedetail")
public class LevelShareDetailController extends AbstractController{
    @Autowired
    private LevelShareDetailService levelShareDetailService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:levelsharedetail:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = levelShareDetailService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("app:levelsharedetail:info")
    public R info(@PathVariable("id") Long id){
		LevelShareDetailEntity levelShareDetail = levelShareDetailService.getById(id);

        return R.ok().put("levelShareDetail", levelShareDetail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:levelsharedetail:save")
    public R save(@RequestBody LevelShareDetailEntity levelShareDetail){
		levelShareDetailService.save(levelShareDetail);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:levelsharedetail:update")
    public R update(@RequestBody LevelShareDetailEntity levelShareDetail){
		levelShareDetailService.updateById(levelShareDetail);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:levelsharedetail:delete")
    public R delete(@RequestBody Long[] ids){
		levelShareDetailService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
