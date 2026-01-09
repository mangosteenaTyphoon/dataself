package com.shanzhu.dataself.server.ai.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.yitter.idgen.YitIdHelper;
import com.shanzhu.dataself.ai.domain.AiChatHistory;
import com.shanzhu.dataself.ai.domain.AiChatHistoryContent;
import com.shanzhu.dataself.ai.domain.dto.AiChatHistoryDTO;
import com.shanzhu.dataself.ai.domain.dto.SearchAiChatHistoryDTO;
import com.shanzhu.dataself.ai.domain.vo.AiChatHistoryVO;
import com.shanzhu.dataself.server.ai.mapper.AiChatHistoryContentMapper;
import com.shanzhu.dataself.server.ai.mapper.AiChatHistoryMapper;
import com.shanzhu.dataself.server.ai.service.IAiChatHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * AI聊天记录Service业务层处理
 *
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @date 2024-12-10
 */
@Service
public class AiChatHistoryServiceImpl implements IAiChatHistoryService {

	private final static Logger log = LoggerFactory.getLogger(AiChatHistoryServiceImpl.class);

	private final AiChatHistoryMapper aiChatHistoryMapper;

	private final AiChatHistoryContentMapper aiChatHistoryContentMapper;

	public AiChatHistoryServiceImpl(AiChatHistoryMapper aiChatHistoryMapper,
			AiChatHistoryContentMapper aiChatHistoryContentMapper) {
		this.aiChatHistoryMapper = aiChatHistoryMapper;
		this.aiChatHistoryContentMapper = aiChatHistoryContentMapper;
	}

	/**
	 * 查询AI聊天记录列表
	 * @param searchAiChatHistoryDTO 搜索聊天记录
	 * @return AI聊天记录集合
	 */
	@Override
	public List<AiChatHistoryVO> selectAiChatHistoryListByUserId(SearchAiChatHistoryDTO searchAiChatHistoryDTO) {
		if (StrUtil.isBlank(searchAiChatHistoryDTO.getUserId())
				|| Objects.isNull(searchAiChatHistoryDTO.getKnowledgeId())
				|| Objects.isNull(searchAiChatHistoryDTO.getMultiRound())) {
			log.error("搜索参数不完整：{}", searchAiChatHistoryDTO);
			return CollUtil.newArrayList();
		}
		if (searchAiChatHistoryDTO.getMultiRound() <= 0) {
			return CollUtil.newArrayList();
		}
		return aiChatHistoryMapper.selectAiChatHistoryListByUserId(searchAiChatHistoryDTO);
	}

	/**
	 * 新增AI聊天记录
	 * @param aiChatHistoryDTO AI聊天记录
	 * @return 结果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Long insertAiChatHistory(AiChatHistoryDTO aiChatHistoryDTO) {
		// 插入聊天信息
		AiChatHistory aiChatHistory = new AiChatHistory();
		aiChatHistory.setChatHistoryId(YitIdHelper.nextId());
		aiChatHistory.setMsgId(aiChatHistoryDTO.getMsgId());
		aiChatHistory.setUserId(aiChatHistoryDTO.getUserId());
		aiChatHistory.setKnowledgeId(aiChatHistoryDTO.getKnowledgeId());
		aiChatHistory.setSendUserId(aiChatHistoryDTO.getSendUserId());
		aiChatHistory.setSendUserName(aiChatHistoryDTO.getSendUserName());
		aiChatHistory.setCreateByType(aiChatHistoryDTO.getCreateByType());
		aiChatHistory.setCreateTime(aiChatHistoryDTO.getCreateTime());
		aiChatHistory.setDelFlag(Boolean.FALSE);
		aiChatHistoryMapper.insertAiChatHistory(aiChatHistory);

		// 插入聊天内容
		AiChatHistoryContent aiChatHistoryContent = new AiChatHistoryContent();
		aiChatHistoryContent.setChatHistoryContentId(YitIdHelper.nextId());
		aiChatHistoryContent.setChatHistoryId(aiChatHistory.getChatHistoryId());
		aiChatHistoryContent.setContent(aiChatHistoryDTO.getContent());
		aiChatHistoryContentMapper.insertAiChatHistoryContent(aiChatHistoryContent);

		return aiChatHistory.getChatHistoryId();
	}

}
