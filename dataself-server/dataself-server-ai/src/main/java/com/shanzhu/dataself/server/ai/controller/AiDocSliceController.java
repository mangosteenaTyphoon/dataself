package com.shanzhu.dataself.server.ai.controller;

import com.shanzhu.dataself.ai.domain.AiDocSlice;
import com.shanzhu.dataself.ai.domain.dto.AiDocSliceDTO;
import com.shanzhu.dataself.framework.core.application.controller.TWTController;
import com.shanzhu.dataself.framework.core.application.domain.JsonResult;
import com.shanzhu.dataself.framework.core.application.page.TableDataInfo;
import com.shanzhu.dataself.framework.jdbc.web.utils.PageUtils;
import com.shanzhu.dataself.server.ai.service.IAiDocSliceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.dreamlu.mica.xss.core.XssCleanIgnore;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AI知识库文档分片Controller
 *
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @date 2024-11-16
 */
@Tag(description = "AiDocSliceController", name = "AI知识库文档分片")
@RestController
@RequestMapping("/slice")
public class AiDocSliceController extends TWTController {

	private final IAiDocSliceService aiDocSliceService;

	public AiDocSliceController(IAiDocSliceService aiDocSliceService) {
		this.aiDocSliceService = aiDocSliceService;
	}

	/**
	 * 查询AI知识库文档分片分页
	 */
	@Operation(summary = "查询AI知识库文档分片分页")
	@PreAuthorize("@role.hasPermi('ai:slice:list')")
	@GetMapping("/pageQuery")
	public JsonResult<TableDataInfo<AiDocSlice>> pageQuery(AiDocSlice aiDocSlice) {
		PageUtils.startPage();
		List<AiDocSlice> list = aiDocSliceService.selectAiDocSliceList(aiDocSlice);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 查询AI知识库文档分片详情
	 */
	@Operation(summary = "查询AI知识库文档分片详情")
	@PreAuthorize("@role.hasPermi('ai:slice:query')")
	@GetMapping("/{sliceId}")
	public JsonResult<AiDocSlice> getInfo(@PathVariable Long sliceId) {
		return JsonResult.success(aiDocSliceService.selectAiDocSliceBySliceId(sliceId));
	}

	/**
	 * 修改AI知识库文档分片
	 */
	@XssCleanIgnore
	@Operation(summary = "修改AI知识库文档分片")
	@PreAuthorize("@role.hasPermi('ai:slice:edit')")
	@PutMapping
	public JsonResult<String> pageQuery(@RequestBody @Validated AiDocSliceDTO aiDocSliceDTO) {
		return json(aiDocSliceService.updateAiDocSlice(aiDocSliceDTO));
	}

}
