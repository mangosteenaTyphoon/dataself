package com.shanzhu.dataself.framework.redis.service.annotation;

import java.lang.annotation.*;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 分布式锁（不支持重入）
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TwSynchronized {

	/**
	 * 唯一锁名称
	 */
	String value();

}
