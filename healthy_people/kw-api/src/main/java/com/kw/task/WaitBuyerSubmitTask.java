package com.kw.task;

import com.kw.common.task.Task;
import com.kw.common.utils.RedisUtils;
import com.kw.common.utils.SpringContextUtils;
import com.kw.service.BuyTransService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Medivh on 2020/4/27.
 * 愿我出走半生,归来仍是少年
 */
public class WaitBuyerSubmitTask extends Task {

    @Autowired


    public WaitBuyerSubmitTask(Long id, long delayInMilliseconds) {

        super(id, delayInMilliseconds);
    }

    @Override
    public void run() {
        BuyTransService  buyTransService = SpringContextUtils.getBean("buyTransService", BuyTransService.class);
        buyTransService.cancle(getId());
    }
}
