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
import com.kw.modules.app.entity.LtcLogEntity;
import com.kw.modules.app.service.LtcLogService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;



/**
 * 矿机日志
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-02 11:25:25
 */
@RestController
@RequestMapping("app/ltclog")
public class LtcLogController extends AbstractController{
    @Autowired
    private LtcLogService ltcLogService;




    /**
     * 列表
     */
    @RequestMapping("/reportlist")
    @RequiresPermissions("app:ltclog:list")
    public R reportlist(@RequestParam Map<String, Object> params){
        List<HashMap> ltcLogEntities   = ltcLogService.queryReportList(params);
        double sum = 0;
        for(HashMap map : ltcLogEntities){
            sum =sum+ Double.valueOf(map.get("income").toString());
        }
        return R.ok().put("list", ltcLogEntities).put("sum",sum);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:ltclog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = ltcLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("app:ltclog:info")
    public R info(@PathVariable("id") Long id){
		LtcLogEntity ltcLog = ltcLogService.getById(id);

        return R.ok().put("ltcLog", ltcLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:ltclog:save")
    public R save(@RequestBody LtcLogEntity ltcLog){
		ltcLogService.save(ltcLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:ltclog:update")
    public R update(@RequestBody LtcLogEntity ltcLog){
		ltcLogService.updateById(ltcLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:ltclog:delete")
    public R delete(@RequestBody Long[] ids){
		ltcLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
