package com.shanzhu.dataself.api.system.feign.factory;

import com.shanzhu.dataself.api.system.feign.RemoteUserService;
import com.shanzhu.dataself.framework.core.domain.R;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 用户信息服务降级处理
 */
@Component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService> {

	private static final Logger log = LoggerFactory.getLogger(RemoteUserFallbackFactory.class);

	@Override
	public RemoteUserService create(Throwable throwable) {
		log.error("用户服务调用失败:{}", throwable.getMessage());
		return (username) -> R.fail();
	}

}
