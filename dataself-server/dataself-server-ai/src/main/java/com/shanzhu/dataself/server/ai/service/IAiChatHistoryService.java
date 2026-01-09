package com.shanzhu.dataself.server.ai.service;

import com.shanzhu.dataself.ai.domain.dto.AiChatHistoryDTO;
import com.shanzhu.dataself.ai.domain.dto.SearchAiChatHistoryDTO;
import com.shanzhu.dataself.ai.domain.vo.AiChatHistoryVO;

import java.util.List;

/**
 * AI聊天记录Service接口
 *
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @date 2024-12-10
 */
public interface IAiChatHistoryService {

	/**
	 * 查询AI聊天记录列表
	 * @param searchAiChatHistoryDTO 搜索聊天记录
	 * @return AI聊天记录集合
	 */
	public List<AiChatHistoryVO> selectAiChatHistoryListByUserId(SearchAiChatHistoryDTO searchAiChatHistoryDTO);

	/**
	 * 新增AI聊天记录
	 * @param aiChatHistoryDTO AI聊天记录
	 * @return 聊天ID
	 */
	public Long insertAiChatHistory(AiChatHistoryDTO aiChatHistoryDTO);

}
