package com.shanzhu.dataself.framework.log.event;

import com.shanzhu.dataself.api.system.domain.SysOperationLog;
import org.springframework.context.ApplicationEvent;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 系统操作日志事件
 */
public class SysOperationLogEvent extends ApplicationEvent {

	public SysOperationLogEvent(SysOperationLog source) {
		super(source);
	}

}
