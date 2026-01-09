package com.shanzhu.dataself.server.system.controller.api;

import com.shanzhu.dataself.api.system.domain.SysUser;
import com.shanzhu.dataself.api.system.model.UserInfo;
import com.shanzhu.dataself.framework.core.application.controller.TWTController;
import com.shanzhu.dataself.framework.core.domain.R;
import com.shanzhu.dataself.framework.security.annotation.AuthIgnore;
import com.shanzhu.dataself.server.system.service.ISysPermissionService;
import com.shanzhu.dataself.server.system.service.ISysUserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Set;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 用户信息
 */
@Hidden
@Tag(description = "SysUserApi", name = "用户信息API")
@RestController
@RequestMapping("/api/user")
public class SysUserApi extends TWTController {

	@Autowired
	private ISysUserService iSysUserService;

	@Autowired
	private ISysPermissionService iSysPermissionService;

	/**
	 * 获取当前用户信息(认证中心服务专用)
	 * @param username String
	 * @return R<UserInfo>
	 */
	@Operation(summary = "获取当前用户信息(认证中心服务专用)")
	@AuthIgnore
	@GetMapping("/info/{username}")
	public R<UserInfo> info(@PathVariable("username") String username) {
		SysUser sysUser = iSysUserService.selectUserByUserName(username, false);
		if (Objects.isNull(sysUser)) {
			return R.fail("用户名或密码错误");
		}
		// 角色集合
		Set<String> roles = iSysPermissionService.getRolePermission(sysUser.getUserId());
		// 权限集合
		Set<String> permissions = iSysPermissionService.getMenuPermission(sysUser.getUserId());
		UserInfo sysUserVo = new UserInfo();
		sysUserVo.setSysUser(sysUser);
		sysUserVo.setRoles(roles);
		sysUserVo.setPermissions(permissions);
		return R.ok(sysUserVo);
	}

}
