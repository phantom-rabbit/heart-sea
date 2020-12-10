package com.kw.modules.app.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.kw.common.constant.CacheConstants;
import com.kw.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kw.modules.app.entity.LevelConfiigEntity;
import com.kw.modules.app.service.LevelConfiigService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;



/**
 * 级别配置
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@RestController
@RequestMapping("app/levelconfiig")
public class LevelConfiigController extends AbstractController {
    @Autowired
    private LevelConfiigService levelConfiigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:levelconfiig:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = levelConfiigService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("app:levelconfiig:info")
    public R info(@PathVariable("id") Integer id){
		LevelConfiigEntity levelConfiig = levelConfiigService.getById(id);

        return R.ok().put("levelConfiig", levelConfiig);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:levelconfiig:save")
  //  @CacheEvict(value = CacheConstants.LEVEL_CONFIG_LIST,allEntries = true)
    public R save(@RequestBody LevelConfiigEntity levelConfiig){
        levelConfiig.setCreateTime(new Date());
        levelConfiig.setCreateUserId(getUserId()+"");
		levelConfiigService.save(levelConfiig);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:levelconfiig:update")

    public R update(@RequestBody LevelConfiigEntity levelConfiig){
		levelConfiigService.updateById(levelConfiig);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:levelconfiig:delete")
    public R delete(@RequestBody Integer[] ids){
		levelConfiigService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
