package com.shanzhu.dataself.server.ai.mq;

import org.springframework.messaging.MessageChannel;

/**
 * RAG MQ通道
 *
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @date 2025-01-04
 */
public interface RAGChannel {

	/**
	 * 添加rag文档
	 */
	String ADD_RAG_DOC = "addRAGDocChannel-out-0";

}
