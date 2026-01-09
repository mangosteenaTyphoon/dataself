package com.shanzhu.dataself.server.gen.controller;

import java.util.List;

import com.shanzhu.dataself.api.gen.domain.GenTemplate;
import com.shanzhu.dataself.api.gen.domain.dto.GenGroupDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import com.shanzhu.dataself.framework.log.annotation.Log;
import com.shanzhu.dataself.framework.log.enums.BusinessType;
import com.shanzhu.dataself.framework.core.application.page.TableDataInfo;
import com.shanzhu.dataself.api.gen.domain.GenGroup;
import com.shanzhu.dataself.server.gen.service.IGenGroupService;
import com.shanzhu.dataself.framework.core.application.controller.TWTController;
import com.shanzhu.dataself.framework.core.application.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.shanzhu.dataself.framework.jdbc.web.utils.PageUtils;

/**
 * 模板分组Controller
 *
 * @author shanzhu
 * @WebSite shanzhu.cloud
 */
@Tag(description = "GenGroupController", name = "模板分组")
@RestController
@RequestMapping("/templateGroup")
public class GenGroupController extends TWTController {

	@Autowired
	private IGenGroupService genGroupService;

	/**
	 * 查询代码生成业务所有模板列表
	 */
	@Operation(summary = "查询代码生成业务所有模板列表")
	@PreAuthorize("@role.hasPermi('gen:group:list')")
	@GetMapping("/queryTemplateList")
	public JsonResult<List<GenTemplate>> selectGenTemplateAll() {
		return JsonResult.success(genGroupService.selectGenTemplateAll());
	}

	/**
	 * 查询模板分组列表
	 */
	@Operation(summary = "查询模板分组分页")
	@PreAuthorize("@role.hasPermi('gen:group:list')")
	@GetMapping("/pageQuery")
	public JsonResult<TableDataInfo<GenGroup>> pageQuery(GenGroup genGroup) {
		PageUtils.startPage();
		List<GenGroup> list = genGroupService.selectGenGroupList(genGroup);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 获取模板分组详细信息
	 */
	@Operation(summary = "获取模板分组详细信息")
	@PreAuthorize("@role.hasPermi('gen:group:query')")
	@GetMapping(value = "/{id}")
	public JsonResult<GenGroupDTO> getInfo(@PathVariable("id") Long id) {
		return JsonResult.success(genGroupService.selectGenGroupById(id));
	}

	/**
	 * 新增模板分组
	 */
	@Operation(summary = "新增模板分组")
	@PreAuthorize("@role.hasPermi('gen:group:add')")
	@Log(service = "模板分组", businessType = BusinessType.INSERT)
	@PostMapping
	public JsonResult<String> add(@RequestBody GenGroupDTO genGroupDTO) {
		return json(genGroupService.insertGenGroup(genGroupDTO));
	}

	/**
	 * 修改模板分组
	 */
	@Operation(summary = "修改模板分组")
	@PreAuthorize("@role.hasPermi('gen:group:edit')")
	@Log(service = "模板分组", businessType = BusinessType.UPDATE)
	@PutMapping
	public JsonResult<String> edit(@RequestBody GenGroupDTO genGroupDTO) {
		return json(genGroupService.updateGenGroup(genGroupDTO));
	}

	/**
	 * 删除模板分组
	 */
	@Operation(summary = "删除模板分组")
	@PreAuthorize("@role.hasPermi('gen:group:remove')")
	@Log(service = "模板分组", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public JsonResult<String> remove(@PathVariable Long[] ids) {
		return json(genGroupService.deleteGenGroupByIds(ids));
	}

}
