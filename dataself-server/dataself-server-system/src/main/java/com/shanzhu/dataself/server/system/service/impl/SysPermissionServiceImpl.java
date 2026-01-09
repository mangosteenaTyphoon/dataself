package com.shanzhu.dataself.server.system.service.impl;

import com.shanzhu.dataself.api.system.domain.SysUser;
import com.shanzhu.dataself.server.system.service.ISysMenuService;
import com.shanzhu.dataself.server.system.service.ISysPermissionService;
import com.shanzhu.dataself.server.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 权限服务
 */
@Service
public class SysPermissionServiceImpl implements ISysPermissionService {

	@Autowired
	private ISysRoleService roleService;

	@Autowired
	private ISysMenuService menuService;

	/**
	 * 获取角色数据权限
	 * @param userId 用户Id
	 * @return 角色权限信息
	 */
	@Override
	public Set<String> getRolePermission(Long userId) {
		Set<String> roles = new HashSet<>();
		// 管理员拥有所有权限
		if (SysUser.isAdmin(userId)) {
			roles.add("admin");
		}
		else {
			roles.addAll(roleService.selectRolePermissionByUserId(userId));
		}
		return roles;
	}

	/**
	 * 获取菜单数据权限
	 * @param userId 用户Id
	 * @return 菜单权限信息
	 */
	@Override
	public Set<String> getMenuPermission(Long userId) {
		Set<String> perms = new HashSet<>();
		// 管理员拥有所有权限
		if (SysUser.isAdmin(userId)) {
			perms.add("*:*:*");
		}
		else {
			perms.addAll(menuService.selectMenuPermsByUserId(userId));
		}
		return perms;
	}

}
