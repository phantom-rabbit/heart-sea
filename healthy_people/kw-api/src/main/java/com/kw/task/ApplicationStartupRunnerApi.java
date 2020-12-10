package com.kw.task;




import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kw.common.sysconfig.entity.SysConfigEntity;
import com.kw.common.sysconfig.redis.SysConfigRedis;
import com.kw.common.sysconfig.service.SysConfigService;
import com.kw.common.task.TaskService;
import com.kw.common.utils.Constant;
import com.kw.common.utils.DateUtils;
import com.kw.common.utils.RedisUtils;
import com.kw.entity.BuyTransEntity;
import com.kw.service.BuyTransService;
import lombok.extern.java.Log;
import net.sf.json.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 启动加载配置
 */
@Component
@Log
public class ApplicationStartupRunnerApi implements ApplicationRunner {

    @Autowired
    private BuyTransService buyTransService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void run(ApplicationArguments args) throws Exception {
       List<BuyTransEntity> waitSellers= buyTransService.list(new QueryWrapper<BuyTransEntity>().eq("status",0).apply("create_time<NOW()-INTERVAL 2 HOUR"));
        log.info("waitSellers:"+ com.alibaba.fastjson.JSON.toJSONString(waitSellers));
        for(BuyTransEntity buyTransEntity:waitSellers){
            Long time = (DateUtils.addDateHours(buyTransEntity.getCreateTime(), redisUtils.get(Constant.BUSINESS_TIME,Integer.class)).getTime() - System.currentTimeMillis());
            if(time<0){
                time = 0L;
            }
            if(buyTransEntity.getStatus()==0) {
                WaitBuyerSubmitTask waitBuyerSubmitTask = new WaitBuyerSubmitTask(buyTransEntity.getId(), time);
                taskService.addTask(waitBuyerSubmitTask);
            }
        }

    }
}