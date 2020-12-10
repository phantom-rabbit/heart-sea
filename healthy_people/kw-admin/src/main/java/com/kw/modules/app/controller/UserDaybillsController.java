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
import com.kw.modules.sys.controller.AbstractController;
import com.kw.modules.app.entity.UserDaybillsEntity;
import com.kw.modules.app.service.UserDaybillsService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;



/**
 * 用户日业绩
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-11 18:29:17
 */
@RestController
@RequestMapping("app/userdaybills")
public class UserDaybillsController extends AbstractController{
    @Autowired
    private UserDaybillsService userDaybillsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:userdaybills:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userDaybillsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{dateKey}")
    @RequiresPermissions("app:userdaybills:info")
    public R info(@PathVariable("dateKey") String dateKey){
		UserDaybillsEntity userDaybills = userDaybillsService.getById(dateKey);

        return R.ok().put("userDaybills", userDaybills);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:userdaybills:save")
    public R save(@RequestBody UserDaybillsEntity userDaybills){
		userDaybillsService.save(userDaybills);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:userdaybills:update")
    public R update(@RequestBody UserDaybillsEntity userDaybills){
		userDaybillsService.updateById(userDaybills);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:userdaybills:delete")
    public R delete(@RequestBody String[] dateKeys){
		userDaybillsService.removeByIds(Arrays.asList(dateKeys));

        return R.ok();
    }

}
