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
import com.kw.modules.app.entity.AccountMoneyEntity;
import com.kw.modules.app.service.AccountMoneyService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;



/**
 * 账户表
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-22 23:09:51
 */
@RestController
@RequestMapping("app/accountmoney")
public class AccountMoneyController extends AbstractController{
    @Autowired
    private AccountMoneyService accountMoneyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:accountmoney:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = accountMoneyService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("app:accountmoney:info")
    public R info(@PathVariable("id") Integer id){
		AccountMoneyEntity accountMoney = accountMoneyService.getById(id);

        return R.ok().put("accountMoney", accountMoney);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:accountmoney:save")
    public R save(@RequestBody AccountMoneyEntity accountMoney){
		accountMoneyService.save(accountMoney);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:accountmoney:update")
    public R update(@RequestBody AccountMoneyEntity accountMoney){
		accountMoneyService.updateById(accountMoney);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:accountmoney:delete")
    public R delete(@RequestBody Integer[] ids){
		accountMoneyService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
