

package com.kw.modules.job.task;

import com.kw.modules.app.entity.UserEntity;
import com.kw.modules.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 级别升级任务
 */
@Component("nextLevelTask")
public class NextLevelTask implements ITask {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserService userService;

	@Override
	public void run(String params){
		logger.debug("级别升级任务检测定时任务正在执行，参数为：{}", params);
		List<UserEntity>  list = userService.list();
		list.forEach(item->{
			try{
				userService.userLevelChangeCheck(item.getId());
			}catch (Exception e){
				logger.error("级别升级任务执行异常,UserId={}",item.getId(), e);
			}
		});
		logger.debug("级别升级任务检测定时任务正在执行完成");
	}
}
