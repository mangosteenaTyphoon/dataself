package com.shanzhu.dataself.framework.log.event.listener;

import com.shanzhu.dataself.api.system.domain.SysOperationLog;
import com.shanzhu.dataself.api.system.feign.RemoteLogService;
import com.shanzhu.dataself.framework.log.event.SysOperationLogEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 异步监听系统登录日志事件
 */
public class SysSysOperationLogEventLogListener {

	private final RemoteLogService remoteLogService;

	public SysSysOperationLogEventLogListener(RemoteLogService remoteLogService) {
		this.remoteLogService = remoteLogService;
	}

	@Async
	@Order
	@EventListener(SysOperationLogEvent.class)
	public void saveSysLog(SysOperationLogEvent event) {
		SysOperationLog sysOperationLog = (SysOperationLog) event.getSource();
		remoteLogService.saveLog(sysOperationLog);
	}

}
