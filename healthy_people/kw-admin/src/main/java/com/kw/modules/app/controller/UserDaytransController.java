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
import com.kw.modules.sys.controller.AbstractController;
import com.kw.modules.app.entity.UserDaytransEntity;
import com.kw.modules.app.service.UserDaytransService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;



/**
 * 用户日买卖
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-12 08:07:04
 */
@RestController
@RequestMapping("app/userdaytrans")
public class UserDaytransController extends AbstractController{
    @Autowired
    private UserDaytransService userDaytransService;


    /**
     * 列表
     */
    @RequestMapping("/reportlist")
    @RequiresPermissions("app:ltclog:list")
    public R reportlist(@RequestParam Map<String, Object> params){
        List<HashMap> ltcLogEntities   = userDaytransService.queryReportList(params);
        double buyAmount = 0 ;
        double fee = 0 ;
        double sellAmount = 0 ;
        for(HashMap map :ltcLogEntities){
            buyAmount= buyAmount+  Double.valueOf(map.get("buyAmount").toString());
            fee= fee+  Double.valueOf(map.get("fee").toString());
            sellAmount= sellAmount+  Double.valueOf(map.get("sellAmount").toString());
        }


        return R.ok().put("list", ltcLogEntities);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:userdaytrans:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userDaytransService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{dateKey}")
    @RequiresPermissions("app:userdaytrans:info")
    public R info(@PathVariable("dateKey") String dateKey){
		UserDaytransEntity userDaytrans = userDaytransService.getById(dateKey);

        return R.ok().put("userDaytrans", userDaytrans);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:userdaytrans:save")
    public R save(@RequestBody UserDaytransEntity userDaytrans){
		userDaytransService.save(userDaytrans);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:userdaytrans:update")
    public R update(@RequestBody UserDaytransEntity userDaytrans){
		userDaytransService.updateById(userDaytrans);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:userdaytrans:delete")
    public R delete(@RequestBody String[] dateKeys){
		userDaytransService.removeByIds(Arrays.asList(dateKeys));

        return R.ok();
    }

}
