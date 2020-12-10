

package com.kw.modules.job.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kw.modules.app.entity.LevelShareDetailEntity;
import com.kw.modules.app.entity.LevelShareEntity;
import com.kw.modules.app.service.LevelShareDetailService;
import com.kw.modules.app.service.LevelShareService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 测试定时任务(演示Demo，可删除)
 *
 * testTask为spring bean的名称
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component("feeGatherTask")
public class FeeGatherTask implements ITask {

	@Autowired
	LevelShareService levelShareService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void run(String params){
		levelShareService.gather();
	}
}
