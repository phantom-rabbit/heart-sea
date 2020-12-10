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
import com.kw.modules.app.entity.NoticeEntity;
import com.kw.modules.app.service.NoticeService;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.R;



/**
 * 通告管理
 *
 * @author kw
 * @email kw@163.com
 * @date 2020-04-03 18:03:32
 */
@RestController
@RequestMapping("app/notice")
public class NoticeController extends AbstractController{
    @Autowired
    private NoticeService noticeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:notice:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = noticeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("app:notice:info")
    public R info(@PathVariable("id") Long id){
		NoticeEntity notice = noticeService.getById(id);

        return R.ok().put("notice", notice);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:notice:save")
    public R save(@RequestBody NoticeEntity notice){
        notice.setCreateDate(new Date());
        notice.setUpdateDate(new Date());
		noticeService.save(notice);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:notice:update")
    public R update(@RequestBody NoticeEntity notice){
        notice.setUpdateDate(new Date());
		noticeService.updateById(notice);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:notice:delete")
    public R delete(@RequestBody Long[] ids){
		noticeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
