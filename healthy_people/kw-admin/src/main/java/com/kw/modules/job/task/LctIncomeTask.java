package com.kw.modules.job.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kw.common.utils.DateUtils;
import com.kw.modules.app.entity.LtcConfigEntity;
import com.kw.modules.app.entity.LtcLogEntity;
import com.kw.modules.app.entity.UserLtcEntity;
import com.kw.modules.app.service.LtcConfigService;
import com.kw.modules.app.service.LtcLogService;
import com.kw.modules.app.service.UserLtcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component("lctIncomeTask")
public class LctIncomeTask implements ITask{
    @Autowired
    UserLtcService userLtcService;
    @Autowired
    LtcConfigService ltcConfigService;
    @Autowired
    LtcLogService ltcLogService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void run(String params) {
        logger.debug("矿机收益定时任务，开始时间："+ LocalDateTime.now());
        List<UserLtcEntity> uList = userLtcService.list(new QueryWrapper<UserLtcEntity>().eq("ltc_status", 1));
        //List<UserLtcEntity> unActiveList = userLtcService.list(new QueryWrapper<UserLtcEntity>().eq("lct_status", 0));
        if(uList != null && uList.size() > 0){
            for (int i = 0; i < uList.size(); i++) {
                UserLtcEntity userLtcEntity = uList.get(i);
                Integer ltcId = uList.get(i).getLtcId();
                Long userId = uList.get(i).getUserId();

                LtcLogEntity ltcLog = new LtcLogEntity();
                ltcLog.setIncome(userLtcEntity.getIncomeParentPerday());
                ltcLog.setIncomeCoin(userLtcEntity.getIncomeSonPerday());
                ltcLog.setLtcId(uList.get(i).getId());
                ltcLog.setUserId(userId);
                ltcLog.setCreateDate(new Date());
                userLtcEntity.setRunDays(userLtcEntity.getRunDays()+1);
                if(userLtcEntity.getRunDays()>= userLtcEntity.getDays()){
                    userLtcEntity.setLtcStatus(-1);
                }
                userLtcService.updateById(userLtcEntity);
                ltcLogService.save(ltcLog);
            }
        }
        logger.debug("矿机收益定时任务，结束时间："+ LocalDateTime.now());

    }
}
