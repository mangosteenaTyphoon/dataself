package com.shanzhu.dataself.server.gen.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import com.shanzhu.dataself.framework.log.annotation.Log;
import com.shanzhu.dataself.framework.log.enums.BusinessType;
import com.shanzhu.dataself.framework.core.application.page.TableDataInfo;
import com.shanzhu.dataself.api.gen.domain.GenFieldType;
import com.shanzhu.dataself.server.gen.service.IGenFieldTypeService;
import com.shanzhu.dataself.framework.core.application.controller.TWTController;
import com.shanzhu.dataself.framework.core.application.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.shanzhu.dataself.framework.jdbc.web.utils.PageUtils;

/**
 * 字段类型管理Controller
 *
 * @author shanzhu
 * @WebSite shanzhu.cloud
 */
@Tag(description = "GenFieldTypeController", name = "字段类型管理")
@RestController
@RequestMapping("/type")
public class GenFieldTypeController extends TWTController {

	@Autowired
	private IGenFieldTypeService genFieldTypeService;

	/**
	 * 查询字段类型管理列表
	 */
	@Operation(summary = "查询字段类型管理分页")
	@PreAuthorize("@role.hasPermi('gen:FieldType:list')")
	@GetMapping("/pageQuery")
	public JsonResult<TableDataInfo<GenFieldType>> pageQuery(GenFieldType genFieldType) {
		PageUtils.startPage();
		List<GenFieldType> list = genFieldTypeService.selectGenFieldTypeList(genFieldType);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 获取字段类型管理详细信息
	 */
	@Operation(summary = "获取字段类型管理详细信息")
	@PreAuthorize("@role.hasPermi('gen:FieldType:query')")
	@GetMapping(value = "/{id}")
	public JsonResult<GenFieldType> getInfo(@PathVariable("id") Long id) {
		return JsonResult.success(genFieldTypeService.selectGenFieldTypeById(id));
	}

	/**
	 * 新增字段类型管理
	 */
	@Operation(summary = "新增字段类型管理")
	@PreAuthorize("@role.hasPermi('gen:FieldType:add')")
	@Log(service = "字段类型管理", businessType = BusinessType.INSERT)
	@PostMapping
	public JsonResult<String> add(@RequestBody GenFieldType genFieldType) {
		return json(genFieldTypeService.insertGenFieldType(genFieldType));
	}

	/**
	 * 修改字段类型管理
	 */
	@Operation(summary = "修改字段类型管理")
	@PreAuthorize("@role.hasPermi('gen:FieldType:edit')")
	@Log(service = "字段类型管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public JsonResult<String> edit(@RequestBody GenFieldType genFieldType) {
		return json(genFieldTypeService.updateGenFieldType(genFieldType));
	}

	/**
	 * 删除字段类型管理
	 */
	@Operation(summary = "删除字段类型管理")
	@PreAuthorize("@role.hasPermi('gen:FieldType:remove')")
	@Log(service = "字段类型管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public JsonResult<String> remove(@PathVariable Long[] ids) {
		return json(genFieldTypeService.deleteGenFieldTypeByIds(ids));
	}

}
