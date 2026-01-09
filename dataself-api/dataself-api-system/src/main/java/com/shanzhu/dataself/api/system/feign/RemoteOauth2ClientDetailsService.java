package com.shanzhu.dataself.api.system.feign;

import com.shanzhu.dataself.api.system.domain.SysClientDetails;
import com.shanzhu.dataself.api.system.feign.factory.RemoteOauth2ClientDetailsFallbackFactory;
import com.shanzhu.dataself.framework.core.constants.SecurityConstants;
import com.shanzhu.dataself.framework.core.constants.ServiceNameConstants;
import com.shanzhu.dataself.framework.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: Oauth2服务
 */
@FeignClient(contextId = "RemoteOauth2ClientDetailsService", value = ServiceNameConstants.SYSTEM_SERVICE,
		fallbackFactory = RemoteOauth2ClientDetailsFallbackFactory.class)
public interface RemoteOauth2ClientDetailsService {

	/**
	 * 获取终端配置详细信息
	 * @param clientId 终端ID
	 * @return JsonResult
	 */
	@GetMapping(value = "/api/client/{clientId}", headers = SecurityConstants.HEADER_FROM_IN)
	R<SysClientDetails> getClientDetailsById(@PathVariable("clientId") String clientId);

}
