package com.shanzhu.dataself.server.job.util;

import com.shanzhu.dataself.api.job.domain.SysJob;
import org.quartz.JobExecutionContext;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 定时任务工具类
 */
public class QuartzJobExecution extends AbstractQuartzJob {

	@Override
	protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception {
		JobInvokeUtil.invokeMethod(sysJob);
	}

}
