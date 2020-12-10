package com.kw.modules.app.controller;

import java.util.Arrays;
import java.util.Map;

import com.kw.common.enums.SdStatus;
import com.kw.common.validator.ValidatorUtils;
import com.kw.modules.app.form.FirstVerifyForm;
import com.kw.modules.app.form.LastForm;
import com.kw.modules.app.form.OperaAccount;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kw.modules.app.entity.AccountEntity;
import com.kw.modules.app.service.AccountService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;



/**
 * 账户表
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@RestController
@RequestMapping("app/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:account:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = accountService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/opera")
    public R opera(@RequestBody OperaAccount operaAccount){
        ValidatorUtils.validateEntity(operaAccount);

       // if(operaAccount.)
        accountService.opera(operaAccount);

        return R.ok().put("account", null);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("app:account:info")
    public R info(@PathVariable("id") Integer id){
		AccountEntity account = accountService.getById(id);

        return R.ok().put("account", account);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:account:save")
    public R save(@RequestBody AccountEntity account){
		accountService.save(account);

        return R.ok();
    }


    @RequestMapping("/auth")
    @RequiresPermissions("app:account:save")
    public R auth(@RequestBody AccountEntity account){
        AccountEntity a = accountService.getById(account.getId());
        if( a.getRealStatus() == 2){
            return R.error("审核通过不能再修改");
        }
        accountService.authUser(a, account.getRealStatus());
        return R.ok();
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:account:update")
    public R update(@RequestBody AccountEntity account){
		accountService.updateById(account);

        return R.ok();
    }



    /**
     * 初审
     */
    @RequestMapping("/firstverify")
    @RequiresPermissions("app:account:save")
    public R firstverify(@RequestBody FirstVerifyForm firstVerifyForm){

        AccountEntity accountEntity = accountService.getById(firstVerifyForm.getId());
        if(accountEntity.getSdStatus()!=SdStatus.WAIT_VERIFY1.getValue()){
            return R.error("状态不是等待初审状态");
        }


        //accountService.removeByIds(Arrays.asList(ids));
        if(firstVerifyForm.getStatus()==0){
            accountEntity.setSdStatus(SdStatus.FAIL.getValue());
            accountEntity.setSdProvinceCity("");
        }else{
            accountEntity.setSdStatus(SdStatus.WAIT_USDT.getValue());

        }

        accountService.updateById(accountEntity);
        return R.ok();
    }
    /**
     * 初审
     */
    @RequestMapping("/lastverify")
    @RequiresPermissions("app:account:save")
    public R lastverify(@RequestBody LastForm lastForm){
        if(lastForm.getStatus()==-2){
            AccountEntity accountEntity = accountService.getById(lastForm.getId());
            if(accountEntity.getSdStatus()!=SdStatus.SUCCESS.getValue()){
                return R.error("状态不是成功状态");
            }
            accountEntity.setSdProvinceCity("");
            accountEntity.setSdPhoto("");
            accountEntity.setSdStatus(-1);
            accountService.updateById(accountEntity);
        }else{
            AccountEntity accountEntity = accountService.getById(lastForm.getId());
            if(accountEntity.getSdStatus()!=SdStatus.WAIT_VERITY2.getValue()){
                return R.error("状态不是等待终审状态");
            }
            if(lastForm.getStatus()==0){
                accountEntity.setSdStatus(SdStatus.WAIT_USDT.getValue());
                accountEntity.setSdPhoto("");
            }
            else if(lastForm.getStatus()==-1){
                accountEntity.setSdStatus(SdStatus.FAIL.getValue());
                accountEntity.setSdProvinceCity("");
                accountEntity.setSdPhoto("");
            }
            else{
                accountEntity.setSdStatus(4);
            }
            accountService.updateById(accountEntity);
        }
        return R.ok();
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:account:delete")
    public R delete(@RequestBody Integer[] ids){
		accountService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
