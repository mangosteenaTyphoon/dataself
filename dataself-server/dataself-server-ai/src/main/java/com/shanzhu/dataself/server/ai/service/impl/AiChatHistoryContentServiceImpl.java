package com.shanzhu.dataself.server.ai.service.impl;

import com.shanzhu.dataself.ai.domain.AiChatHistoryContent;
import com.shanzhu.dataself.server.ai.mapper.AiChatHistoryContentMapper;
import com.shanzhu.dataself.server.ai.service.IAiChatHistoryContentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AI会话内容详情Service业务层处理
 *
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @date 2024-12-10
 */
@Service
public class AiChatHistoryContentServiceImpl implements IAiChatHistoryContentService {

	private final AiChatHistoryContentMapper aiChatHistoryContentMapper;

	public AiChatHistoryContentServiceImpl(AiChatHistoryContentMapper aiChatHistoryContentMapper) {
		this.aiChatHistoryContentMapper = aiChatHistoryContentMapper;
	}

	/**
	 * 查询AI会话内容详情
	 * @param chatHistoryContentId AI会话内容详情主键
	 * @return AI会话内容详情
	 */
	@Override
	public AiChatHistoryContent selectAiChatHistoryContentByChatHistoryContentId(Long chatHistoryContentId) {
		return aiChatHistoryContentMapper.selectAiChatHistoryContentByChatHistoryContentId(chatHistoryContentId);
	}

	/**
	 * 查询AI会话内容详情列表
	 * @param aiChatHistoryContent AI会话内容详情
	 * @return AI会话内容详情
	 */
	@Override
	public List<AiChatHistoryContent> selectAiChatHistoryContentList(AiChatHistoryContent aiChatHistoryContent) {
		return aiChatHistoryContentMapper.selectAiChatHistoryContentList(aiChatHistoryContent);
	}

}
