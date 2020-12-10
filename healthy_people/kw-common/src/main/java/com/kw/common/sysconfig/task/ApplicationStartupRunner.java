package com.kw.common.sysconfig.task;




import com.kw.common.sysconfig.entity.SysConfigEntity;
import com.kw.common.sysconfig.redis.SysConfigRedis;
import com.kw.common.sysconfig.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 启动加载配置
 */
@Component
public class ApplicationStartupRunner implements ApplicationRunner {


    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private SysConfigRedis sysConfigRedis;

    @Override
    public void run(ApplicationArguments args)  {
        List<SysConfigEntity> list = sysConfigService.list();
        if(list != null || list.size() > 0){
            list.forEach(item -> sysConfigRedis.saveOrUpdate(item));
        }
          

    }
}