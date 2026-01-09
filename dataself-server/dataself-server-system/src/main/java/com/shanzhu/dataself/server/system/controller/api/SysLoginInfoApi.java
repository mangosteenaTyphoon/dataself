package com.shanzhu.dataself.server.system.controller.api;

import com.shanzhu.dataself.api.system.domain.SysLoginInfo;
import com.shanzhu.dataself.framework.security.annotation.AuthIgnore;
import com.shanzhu.dataself.framework.core.application.controller.TWTController;
import com.shanzhu.dataself.server.system.service.ISysLoginInfoService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 系统操作/访问日志
 */
@Hidden
@Tag(description = "SysLoginInfoApi", name = "系统操作日志API")
@RestController
@RequestMapping("/api/loginInfo")
public class SysLoginInfoApi extends TWTController {

	@Autowired
	private ISysLoginInfoService iSysLoginInfoService;

	/**
	 * 记录登录信息
	 * @param sysLoginInfo SysLoginInfo
	 */
	@Operation(summary = "记录登录信息")
	@AuthIgnore
	@PostMapping
	public void insertLog(@RequestBody SysLoginInfo sysLoginInfo) {
		iSysLoginInfoService.insertLoginInfo(sysLoginInfo);
	}

}
