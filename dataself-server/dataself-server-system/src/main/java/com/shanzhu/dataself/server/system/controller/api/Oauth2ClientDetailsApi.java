package com.shanzhu.dataself.server.system.controller.api;

import com.shanzhu.dataself.api.system.domain.SysClientDetails;
import com.shanzhu.dataself.framework.core.application.controller.TWTController;
import com.shanzhu.dataself.framework.core.domain.R;
import com.shanzhu.dataself.framework.security.annotation.AuthIgnore;
import com.shanzhu.dataself.server.system.service.ISysClientDetailsService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: OAuth2 API
 */
@Hidden
@Tag(description = "Oauth2ClientDetailsApi", name = "OAuth2 API")
@RestController
@RequestMapping("/api/client")
public class Oauth2ClientDetailsApi extends TWTController {

	@Autowired
	private ISysClientDetailsService sysClientDetailsService;

	/**
	 * 获取终端配置详细信息
	 * @param clientId 终端ID
	 * @return R<SysClientDetails>
	 */
	@Operation(summary = "获取终端配置详细信息")
	@AuthIgnore
	@GetMapping(value = "/{clientId}")
	public R<SysClientDetails> getClientDetailsById(@PathVariable("clientId") String clientId) {
		return R.ok(sysClientDetailsService.selectSysClientDetailsById(clientId));
	}

}
