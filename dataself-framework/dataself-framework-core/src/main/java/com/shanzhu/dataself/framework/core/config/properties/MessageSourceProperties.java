package com.shanzhu.dataself.framework.core.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 国际化路径配置
 *
 * @author shanzhu
 */
@ConfigurationProperties(prefix = "spring.messages")
public class MessageSourceProperties {

	/**
	 * 基础路径
	 */
	private String basename;

	/**
	 * 缓存持续时间（秒），默认3600秒（1小时）
	 */
	private Long cacheDuration = 3600L;

	public String getBasename() {
		return basename;
	}

	public void setBasename(String basename) {
		this.basename = basename;
	}

	public Long getCacheDuration() {
		return cacheDuration;
	}

	public void setCacheDuration(Long cacheDuration) {
		this.cacheDuration = cacheDuration;
	}

}
