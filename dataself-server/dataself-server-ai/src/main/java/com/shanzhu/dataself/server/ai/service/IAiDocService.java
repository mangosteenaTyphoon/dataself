package com.shanzhu.dataself.server.ai.service;

import com.shanzhu.dataself.ai.domain.AiDoc;
import com.shanzhu.dataself.ai.domain.dto.AiDocDTO;

import java.util.List;

/**
 * AI知识库文档Service接口
 *
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @date 2024-11-16
 */
public interface IAiDocService {

	/**
	 * 查询AI知识库文档
	 * @param docId AI知识库文档主键
	 * @return AI知识库文档
	 */
	public AiDoc selectAiDocByDocId(Long docId);

	/**
	 * 查询AI知识库文档列表
	 * @param aiDoc AI知识库文档
	 * @return AI知识库文档集合
	 */
	public List<AiDoc> selectAiDocList(AiDoc aiDoc);

	/**
	 * 新增AI知识库文档
	 * @param aiDocDTO AI知识库文档
	 * @return 结果
	 */
	public Boolean insertAiDoc(AiDocDTO aiDocDTO);

	/**
	 * 批量删除AI知识库文档
	 * @param docIds 需要删除的AI知识库文档主键集合
	 * @return 结果
	 */
	public int deleteAiDocByDocIds(Long[] docIds);

}
