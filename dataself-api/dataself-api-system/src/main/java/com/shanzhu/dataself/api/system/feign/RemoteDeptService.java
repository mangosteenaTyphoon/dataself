package com.shanzhu.dataself.api.system.feign;

import com.shanzhu.dataself.api.system.feign.factory.RemoteDeptFallbackFactory;
import com.shanzhu.dataself.framework.core.constants.ServiceNameConstants;
import com.shanzhu.dataself.framework.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 部门服务
 */
@FeignClient(contextId = "RemoteDeptService", value = ServiceNameConstants.SYSTEM_SERVICE,
		fallbackFactory = RemoteDeptFallbackFactory.class)
public interface RemoteDeptService {

	/**
	 * 获取当前用户持有的权限列表
	 * @return R<Set<Long>>
	 */
	@GetMapping(value = "/api/dept/current/user/ids")
	R<Set<Long>> selectDeptIdListByUser();

}
