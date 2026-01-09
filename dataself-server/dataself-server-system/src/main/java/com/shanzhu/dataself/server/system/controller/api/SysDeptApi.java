package com.shanzhu.dataself.server.system.controller.api;

import com.shanzhu.dataself.api.system.domain.SysDept;
import com.shanzhu.dataself.framework.core.application.controller.TWTController;
import com.shanzhu.dataself.framework.core.domain.R;
import com.shanzhu.dataself.server.system.service.ISysDeptService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 部门管理api
 */
@Hidden
@Tag(description = "SysDeptApi", name = "部门管理api")
@RestController
@RequestMapping("/api/dept")
public class SysDeptApi extends TWTController {

	@Autowired
	private ISysDeptService deptService;

	/**
	 * 获取当前用户持有的权限列表
	 * @return JsonResult<List < SysDept>>
	 */
	/**
	 * 获取当前用户持有的权限列表
	 * @return JsonResult<List < SysDept>>
	 */
	@Operation(summary = "获取当前用户持有的权限列表")
	@GetMapping("/current/user/ids")
	public R<Set<Long>> selectDeptIdListByUser() {
		SysDept sysDept = new SysDept();
		return R.ok(deptService.selectDeptIdListByUser(sysDept));
	}

}
