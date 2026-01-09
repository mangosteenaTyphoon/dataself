package com.shanzhu.dataself.server.system.utils;

import com.shanzhu.dataself.framework.redis.service.RedisUtils;
import com.shanzhu.dataself.framework.redis.service.constants.CacheConstants;

import java.util.Collection;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 字典工具类
 */
public class DictUtils {

	/**
	 * 清空字典缓存
	 */
	public static void clearDictCache() {
		Collection<String> keys = RedisUtils.keys(CacheConstants.SYS_DICT_KEY + "*");
		RedisUtils.deleteObject(keys);
	}

	/**
	 * 获取cache key
	 * @param configKey 参数键
	 * @return 缓存键key
	 */
	public static String getCacheKey(String configKey) {
		return CacheConstants.SYS_DICT_KEY + "::" + configKey;
	}

}
