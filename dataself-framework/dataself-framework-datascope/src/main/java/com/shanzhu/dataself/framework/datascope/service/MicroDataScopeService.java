package com.shanzhu.dataself.framework.datascope.service;

import java.util.Set;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 分布式权限服务
 */
public interface MicroDataScopeService {

	/**
	 * 获取权限
	 * @return Set<Long>
	 */
	Set<Long> getPermission();

}
