package com.shanzhu.dataself.api.system.feign;

import com.shanzhu.dataself.api.system.feign.factory.RemoteUserFallbackFactory;
import com.shanzhu.dataself.api.system.model.UserInfo;
import com.shanzhu.dataself.framework.core.constants.SecurityConstants;
import com.shanzhu.dataself.framework.core.constants.ServiceNameConstants;
import com.shanzhu.dataself.framework.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 用户信息服务
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.SYSTEM_SERVICE,
		fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService {

	/**
	 * 通过用户名查询用户信息
	 * @param username 用户名称
	 * @return R<UserInfo>
	 */
	@GetMapping(value = "/api/user/info/{username}", headers = SecurityConstants.HEADER_FROM_IN)
	R<UserInfo> getUserInfo(@PathVariable("username") String username);

}
