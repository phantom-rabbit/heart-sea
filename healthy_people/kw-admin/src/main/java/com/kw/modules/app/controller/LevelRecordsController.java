package com.kw.modules.app.controller;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kw.modules.app.entity.*;
import com.kw.modules.app.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.kw.modules.sys.controller.AbstractController;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;


/**
 * 级别升级审核
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-21 03:21:00
 */
@RestController
@RequestMapping("app/levelrecords")
public class LevelRecordsController extends AbstractController {
    @Autowired
    private LevelRecordsService levelRecordsService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private LevelConfiigAwrdLtcService levelConfiigAwrdLtcService;
    @Autowired
    private LtcConfigService ltcConfigService;
    @Autowired
    private UserLtcService userLtcService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:levelrecords:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = levelRecordsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("app:levelrecords:info")
    public R info(@PathVariable("id") Long id) {
        LevelRecordsEntity levelRecords = levelRecordsService.queryById(id);

        return R.ok().put("levelRecords", levelRecords);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:levelrecords:save")
    public R save(@RequestBody LevelRecordsEntity levelRecords) {
        levelRecordsService.save(levelRecords);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:levelrecords:update")
    public R update(@RequestBody LevelRecordsEntity levelRecords) {
        if (levelRecords.getStatus() == 1) {
            AccountEntity account = accountService.queryByUserId(levelRecords.getUserId());
            if (account.getLevelId() >= levelRecords.getLevelId()) {
                return R.error("该用户当前等级等于或高于该申请记录");
            }

            account.setLevelId(levelRecords.getLevelId());
            //奖励矿机
            List<LevelConfiigAwrdLtcEntity> list = levelConfiigAwrdLtcService.list(
                    new QueryWrapper<LevelConfiigAwrdLtcEntity>().eq("config_id", levelRecords.getLevelId()));
            if (list != null && list.size() > 0) {
                LevelConfiigAwrdLtcEntity  levelConfiigAwrdLtc = list.get(0);
                LtcConfigEntity ltcConfig = ltcConfigService.getById(levelConfiigAwrdLtc.getAwardLtcId());
                UserLtcEntity userLt = new UserLtcEntity(levelRecords.getUserId(), ltcConfig, "升级奖励");
                //实名奖励矿机不奖励激活码
                userLt.setIsActiveCode(1);
                userLt.setIsAward(1);
                userLtcService.save(userLt);
            }
            accountService.updateById(account);
            levelRecordsService.updateById(levelRecords);
        } else {
            levelRecordsService.updateById(levelRecords);
        }

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:levelrecords:delete")
    public R delete(@RequestBody Long[] ids) {
        levelRecordsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
