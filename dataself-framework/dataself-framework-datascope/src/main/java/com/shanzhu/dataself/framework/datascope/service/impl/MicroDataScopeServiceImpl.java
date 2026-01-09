package com.shanzhu.dataself.framework.datascope.service.impl;

import com.shanzhu.dataself.api.system.domain.SysRole;
import com.shanzhu.dataself.api.system.feign.RemoteDeptService;
import com.shanzhu.dataself.framework.core.domain.R;
import com.shanzhu.dataself.framework.datascope.constant.DataScopeConstants;
import com.shanzhu.dataself.framework.datascope.service.MicroDataScopeService;
import com.shanzhu.dataself.framework.redis.service.RedisUtils;
import com.shanzhu.dataself.framework.redis.service.constants.CacheConstants;
import com.shanzhu.dataself.framework.security.domain.LoginUser;
import com.shanzhu.dataself.framework.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 分布式权限服务
 */
@Service
public class MicroDataScopeServiceImpl implements MicroDataScopeService {

	@Autowired
	private RemoteDeptService remoteDeptService;

	/**
	 * 获取权限
	 * @return Set<Long>
	 */
	@Override
	public Set<Long> getPermission() {
		// 获取当前的用户
		LoginUser loginUser = SecurityUtils.getLoginUser();
		Set<Long> permissionSet = new HashSet<>();

		String cacheKey = String.format(CacheConstants.DATA_SCOPE_CACHE, loginUser.getUserId());

		Set<Long> permissionSetCache = RedisUtils.getCacheObject(cacheKey);
		if (Objects.nonNull(permissionSetCache)) { // 空数组也允许获取，取缓存权限
			return permissionSetCache;
		}

		for (SysRole role : loginUser.getRoles()) {
			String dataScope = role.getDataScope();

			if (DataScopeConstants.DATA_SCOPE_ALL.equals(dataScope)) { // 只要有一个可看所有数据跳出
				permissionSet = new HashSet<>();
				break;
			}
			else if ((DataScopeConstants.DATA_SCOPE_CUSTOM.equals(dataScope)
					|| DataScopeConstants.DATA_SCOPE_DEPT.equals(dataScope)
					|| DataScopeConstants.DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope))) { // 只使用一次dept
				R<Set<Long>> remote = remoteDeptService.selectDeptIdListByUser();
				permissionSet = remote.getData();
			}
			// 无需要获取自身数据权限
		}

		// 写入缓存
		RedisUtils.setCacheObject(cacheKey, permissionSet);

		return permissionSet;
	}

}
