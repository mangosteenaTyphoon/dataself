package com.shanzhu.dataself.server.system.controller;

import com.shanzhu.dataself.auth.feign.RemoteTokenService;
import com.shanzhu.dataself.auth.feign.domain.dto.TokenDTO;
import com.shanzhu.dataself.auth.feign.domain.vo.TokenVo;
import com.shanzhu.dataself.framework.core.application.controller.TWTController;
import com.shanzhu.dataself.framework.core.application.domain.JsonResult;
import com.shanzhu.dataself.framework.core.application.page.PageDomain;
import com.shanzhu.dataself.framework.core.application.page.TableDataInfo;
import com.shanzhu.dataself.framework.core.application.page.TableSupport;
import com.shanzhu.dataself.framework.core.domain.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 令牌管理
 */
@Tag(description = "TokenController", name = "令牌管理")
@RestController
@RequestMapping("/token")
public class TokenController extends TWTController {

	@Autowired
	private RemoteTokenService remoteTokenService;

	/**
	 * 分页token 信息
	 * @param username username
	 * @return 分页数据
	 */
	@Operation(summary = "分页token 信息")
	@PreAuthorize("@role.hasPermi('system:token:list')")
	@GetMapping("/pageQuery")
	public JsonResult<TableDataInfo<TokenVo>> token(String username) {
		TokenDTO tokenDTO = new TokenDTO();
		tokenDTO.setUsername(username);
		PageDomain pageDomain = TableSupport.buildPageRequest();
		Integer current = pageDomain.getCurrent();
		Integer pageSize = pageDomain.getPageSize();
		if (Objects.nonNull(current) && Objects.nonNull(pageSize)) {
			tokenDTO.setCurrent(current);
			tokenDTO.setPageSize(pageSize);
		}
		R<TableDataInfo<TokenVo>> tokenPage = remoteTokenService.getTokenPage(tokenDTO);
		return JsonResult.success(tokenPage.getData());
	}

	/**
	 * 删除token
	 * @param token token
	 * @return JsonResult<Void>
	 */
	@Operation(summary = "删除token")
	@PreAuthorize("@role.hasPermi('system:token:remove')")
	@DeleteMapping("/{token}")
	public JsonResult<Void> delete(@PathVariable String token) {
		remoteTokenService.removeToken(token);
		return JsonResult.success();
	}

}
