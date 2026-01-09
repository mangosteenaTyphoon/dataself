package com.shanzhu.dataself.server.ai.mapper;

import com.shanzhu.dataself.ai.domain.AiChatHistory;
import com.shanzhu.dataself.ai.domain.dto.SearchAiChatHistoryDTO;
import com.shanzhu.dataself.ai.domain.vo.AiChatHistoryPageVO;
import com.shanzhu.dataself.ai.domain.vo.AiChatHistoryVO;

import java.util.List;

/**
 * AI聊天记录Mapper接口
 *
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @date 2024-12-10
 */
public interface AiChatHistoryMapper {

	/**
	 * 查询AI聊天记录列表
	 * @param searchAiChatHistoryDTO 搜索聊天记录
	 * @return AI聊天记录集合
	 */
	public List<AiChatHistoryVO> selectAiChatHistoryListByUserId(SearchAiChatHistoryDTO searchAiChatHistoryDTO);

	/**
	 * 查询指定知识库的用户AI聊天记录列表
	 * @param aiChatHistory 搜索聊天记录
	 * @return AI聊天记录集合
	 */
	public List<AiChatHistoryPageVO> selectKnowledgeAiChatHistoryListByUserId(AiChatHistory aiChatHistory);

	/**
	 * 新增AI聊天记录
	 * @param aiChatHistory AI聊天记录
	 * @return 结果
	 */
	public int insertAiChatHistory(AiChatHistory aiChatHistory);

}
