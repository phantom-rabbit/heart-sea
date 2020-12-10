package com.kw.modules.job.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kw.common.enums.SdStatus;
import com.kw.modules.app.entity.AccountEntity;
import com.kw.modules.app.entity.BuyTransEntity;
import com.kw.modules.app.entity.LevelConfiigEntity;
import com.kw.modules.app.service.AccountService;
import com.kw.modules.app.service.BuyTransService;
import com.kw.modules.app.service.LevelConfiigService;
import com.kw.modules.app.entity.LevelShareDetailEntity;
import com.kw.modules.app.service.LevelShareDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Medivh on 2020/4/9.
 * 愿我出走半生,归来仍是少年
 * 普通分红+世代分红详细
 */
@Component("feeShareTask")
public class FeeShareTask implements ITask {




    @Autowired
    LevelShareDetailService levelShareDetailService;




    @Override
    public void run(String params){
        levelShareDetailService.cacue();

    }
}
