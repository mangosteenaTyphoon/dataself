package com.shanzhu.dataself.server.system.mapper;

import com.shanzhu.dataself.api.system.domain.SysRoleMenu;

import java.util.List;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 岗位信息操作处理
 */
public interface SysRoleMenuMapper {

	/**
	 * 查询菜单使用数量
	 * @param menuId 菜单ID
	 * @return 结果
	 */
	int checkMenuExistRole(Long menuId);

	/**
	 * 通过角色ID删除角色和菜单关联
	 * @param roleId 角色ID
	 * @return 结果
	 */
	int deleteRoleMenuByRoleId(Long roleId);

	/**
	 * 批量新增角色菜单信息
	 * @param roleMenuList 角色菜单列表
	 * @return 结果
	 */
	int batchRoleMenu(List<SysRoleMenu> roleMenuList);

}
