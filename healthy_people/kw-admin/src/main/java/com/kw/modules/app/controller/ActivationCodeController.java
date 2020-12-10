package com.kw.modules.app.controller;

import java.util.*;

import com.kw.common.utils.InviteCodeUtil;
import com.kw.modules.app.entity.AccountEntity;
import com.kw.modules.app.entity.GenesisEntity;
import com.kw.modules.app.entity.UserEntity;
import com.kw.modules.app.service.AccountService;
import com.kw.modules.app.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kw.modules.app.entity.ActivationCodeEntity;
import com.kw.modules.app.service.ActivationCodeService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;


/**
 * @author kw
 * @email kw@163.com
 * @date 2020-04-01 10:39:57
 */
@RestController
@RequestMapping("app/activationcode")
public class ActivationCodeController {
    @Autowired
    private ActivationCodeService activationCodeService;

    @Autowired
    private AccountService accountService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:activationcode:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = activationCodeService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 生成邀请码
     */
    @RequestMapping("/general/{uid}/{ct}")
    @RequiresPermissions("app:activationcode:info")
    public R general(@PathVariable("uid") Long uid, @PathVariable("ct") Integer ct) {

        AccountEntity account = accountService.queryByUserId(uid);
        if(account != null && account.getRealStatus() != 2){
            return R.error("该用户未实名认证");
        }

        ArrayList<ActivationCodeEntity> list = new ArrayList();
        for (int i = 0; i < ct; i++) {
            ActivationCodeEntity entity = new ActivationCodeEntity();
            entity.setCodeType(1);
            entity.setCreateTime(new Date());
            entity.setActivationCode(InviteCodeUtil.encry_RC4_string(String.format("%07d", i), UUID.randomUUID().toString().toUpperCase()));
            entity.setStatus(0);
            entity.setUserId(uid);
            list.add(entity);
        }
        activationCodeService.saveBatch(list);
        return R.ok();
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("app:activationcode:info")
    public R info(@PathVariable("id") Integer id) {
        ActivationCodeEntity activationCode = activationCodeService.getById(id);

        return R.ok().put("activationCode", activationCode);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:activationcode:save")
    public R save(@RequestBody ActivationCodeEntity activationCode) {
        activationCodeService.save(activationCode);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:activationcode:update")
    public R update(@RequestBody ActivationCodeEntity activationCode) {
        activationCodeService.updateById(activationCode);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:activationcode:delete")
    public R delete(@RequestBody Integer[] ids) {
        activationCodeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
