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
import com.kw.modules.sys.controller.AbstractController;
import com.kw.modules.app.entity.GenesisEntity;
import com.kw.modules.app.service.GenesisService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;



/**
 * 合伙人
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-12 09:41:09
 */
@RestController
@RequestMapping("app/genesis")
public class GenesisController extends AbstractController{
    @Autowired
    private GenesisService genesisService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:genesis:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = genesisService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("app:genesis:info")
    public R info(@PathVariable("id") Long id){
		GenesisEntity genesis = genesisService.getById(id);

        return R.ok().put("genesis", genesis);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:genesis:save")
    public R save(@RequestBody GenesisEntity genesis){
        genesis.setCreateDate(new Date());
		genesisService.save(genesis);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:genesis:update")
    public R update(@RequestBody GenesisEntity genesis){
		genesisService.updateById(genesis);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:genesis:delete")
    public R delete(@RequestBody Long[] ids){
		genesisService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
