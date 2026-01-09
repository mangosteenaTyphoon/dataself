package com.shanzhu.dataself.framework.websocket.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

/**
 * <p>
 * 默认的纯文本消息处理器
 * </p>
 *
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @date 2025-08-11
 */
public class CustomPlanTextMessageHandler implements PlanTextMessageHandler {

	private final static Logger log = LoggerFactory.getLogger(CustomPlanTextMessageHandler.class);

	/**
	 * 处理纯文本 WebSocket 消息。
	 * <p>
	 * 默认实现仅将接收到的消息内容和会话 ID 记录到日志中。
	 * </p>
	 * @param session 当前接收消息的 WebSocket 会话。
	 * @param message 接收到的文本消息。
	 */
	@Override
	public void handle(WebSocketSession session, String message) {
		log.info("sessionId {} ,msg {}", session.getId(), message);
	}

}
