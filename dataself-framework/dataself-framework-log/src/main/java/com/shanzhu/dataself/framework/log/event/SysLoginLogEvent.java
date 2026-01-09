package com.shanzhu.dataself.framework.log.event;

import com.shanzhu.dataself.api.system.domain.SysLoginInfo;
import org.springframework.context.ApplicationEvent;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 系统登录日志事件
 */
public class SysLoginLogEvent extends ApplicationEvent {

	public SysLoginLogEvent(SysLoginInfo source) {
		super(source);
	}

}
