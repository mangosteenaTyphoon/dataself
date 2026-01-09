package com.shanzhu.dataself.framework.datascope.annotation;

import java.lang.annotation.*;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 系统数据权限
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysDataScope {

	/**
	 * 部门表的别名
	 */
	String deptAlias() default "";

	/**
	 * 用户表的别名
	 */
	String userAlias() default "";

}
