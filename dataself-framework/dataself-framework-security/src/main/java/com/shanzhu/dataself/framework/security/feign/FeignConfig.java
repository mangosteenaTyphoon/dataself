package com.shanzhu.dataself.framework.security.feign;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: Feign 配置注册
 */
public class FeignConfig {

	@Bean
	public RequestInterceptor requestInterceptor() {
		return new FeignRequestInterceptor();
	}

}
