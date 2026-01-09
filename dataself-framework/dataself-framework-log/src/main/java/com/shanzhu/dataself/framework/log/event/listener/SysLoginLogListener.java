package com.shanzhu.dataself.framework.log.event.listener;

import com.shanzhu.dataself.api.system.domain.SysLoginInfo;
import com.shanzhu.dataself.api.system.feign.RemoteLogService;
import com.shanzhu.dataself.framework.log.event.SysLoginLogEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 异步监听系统登录日志事件
 */
public class SysLoginLogListener {

	private final RemoteLogService remoteLogService;

	public SysLoginLogListener(RemoteLogService remoteLogService) {
		this.remoteLogService = remoteLogService;
	}

	@Async
	@Order
	@EventListener(SysLoginLogEvent.class)
	public void saveSysLog(SysLoginLogEvent event) {
		SysLoginInfo sysLoginInfo = (SysLoginInfo) event.getSource();
		remoteLogService.saveLoginInfo(sysLoginInfo);
	}

}
