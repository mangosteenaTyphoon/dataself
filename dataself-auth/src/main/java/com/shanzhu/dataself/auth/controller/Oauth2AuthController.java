package com.shanzhu.dataself.auth.controller;

import com.shanzhu.dataself.auth.service.Oauth2AuthService;
import com.shanzhu.dataself.framework.core.application.controller.TWTController;
import com.shanzhu.dataself.framework.core.application.domain.JsonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 第三方登录
 * <p>
 *
 * @since 2025/1/16
 */
@Tag(description = "Oauth2AuthController", name = "OAuth2第三方登录管理")
@RestController
@RequestMapping("/login/oauth2")
public class Oauth2AuthController extends TWTController {

	@Autowired
	private Oauth2AuthService oauth2AuthService;

	/**
	 * 获取登录地址
	 * @return String
	 */
	@Operation(summary = "获取登录地址")
	@GetMapping("/{oauthCode}")
	public JsonResult<String> getAuthorize(@PathVariable String oauthCode) {
		return JsonResult.success(oauthCode, oauth2AuthService.getAuthorize(oauthCode));
	}

}
