package com.shanzhu.dataself.server.system.controller;

import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import com.shanzhu.dataself.api.system.domain.SysLoginInfo;
import com.shanzhu.dataself.framework.core.application.controller.TWTController;
import com.shanzhu.dataself.framework.core.application.domain.JsonResult;
import com.shanzhu.dataself.framework.core.application.page.TableDataInfo;
import com.shanzhu.dataself.framework.jdbc.web.utils.PageUtils;
import com.shanzhu.dataself.framework.log.annotation.Log;
import com.shanzhu.dataself.framework.log.enums.BusinessType;
import com.shanzhu.dataself.server.system.service.ISysLoginInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 系统登录日志
 */
@Tag(description = "SysLoginInfoController", name = "系统登录日志")
@RestController
@RequestMapping("/loginInfo")
public class SysLoginInfoController extends TWTController {

	@Autowired
	private ISysLoginInfoService iSysLoginInfoService;

	/**
	 * 登录日志查询
	 * @param loginInfo SysLoginInfo
	 * @return 查询数据
	 */
	@Operation(summary = "登录日志查询")
	@GetMapping("/pageQuery")
	@PreAuthorize("@role.hasPermi('system:logininfor:list')")
	public JsonResult<TableDataInfo<SysLoginInfo>> pageQuery(SysLoginInfo loginInfo) {
		PageUtils.startPage();
		List<SysLoginInfo> list = iSysLoginInfoService.selectLoginInfoList(loginInfo);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 批量删除日志
	 * @param infoIds 日志Id list
	 * @return 操作结果
	 */
	@Operation(summary = "批量删除日志")
	@DeleteMapping("/{infoIds}")
	@PreAuthorize("@role.hasPermi('system:logininfor:remove')")
	public JsonResult<String> remove(@PathVariable Long[] infoIds) {
		return json(iSysLoginInfoService.deleteLoginInfoByIds(infoIds));
	}

	/**
	 * 清空登录日志
	 * @return JsonResult<String>
	 */
	@Operation(summary = "清空登录日志")
	@Log(service = "登陆日志", businessType = BusinessType.CLEAN)
	@DeleteMapping("/clean")
	@PreAuthorize("@role.hasPermi('system:logininfor:remove')")
	public JsonResult<String> clean() {
		iSysLoginInfoService.cleanLoginInfo();
		return JsonResult.success();
	}

	/**
	 * 导出Excel
	 * @param loginInfo SysLoginInfo
	 * @return List<SysLoginInfo>
	 */
	@ResponseExcel(name = "登陆日志")
	@Operation(summary = "导出Excel")
	@Log(service = "登陆日志", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	@PreAuthorize("@role.hasPermi('system:logininfor:export')")
	public List<SysLoginInfo> export(@RequestBody SysLoginInfo loginInfo) {
		return iSysLoginInfoService.selectLoginInfoList(loginInfo);
	}

}
