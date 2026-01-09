package com.shanzhu.dataself.framework.security.support.service.manager;

import com.shanzhu.dataself.api.system.domain.SysUser;
import com.shanzhu.dataself.api.system.feign.RemoteUserService;
import com.shanzhu.dataself.api.system.model.UserInfo;
import com.shanzhu.dataself.framework.core.domain.R;
import com.shanzhu.dataself.framework.core.domain.utils.ResUtils;
import com.shanzhu.dataself.framework.redis.service.constants.CacheConstants;
import com.shanzhu.dataself.framework.security.constants.Oauth2ClientEnums;
import com.shanzhu.dataself.framework.security.constants.Oauth2GrantEnums;
import com.shanzhu.dataself.framework.security.domain.LoginUser;
import com.shanzhu.dataself.framework.security.exception.UserFrozenException;
import com.shanzhu.dataself.framework.security.support.service.TwUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 自定义用户信息处理
 */
@Primary
public class TwTUserDetailsServiceImpl implements TwUserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(TwTUserDetailsServiceImpl.class);

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private CacheManager cacheManager;

	/**
	 * 识别是否使用此登录器
	 * @param clientId 目标客户端
	 * @param grantType 登录类型
	 * @return boolean
	 */
	@Override
	public boolean support(String clientId, String grantType) {
        return Oauth2ClientEnums.dataself.getClientId().equals(clientId)
                && Oauth2GrantEnums.PASSWORD.getGrant().equals(grantType);
	}

	/**
	 * 用户名称登录
	 * @param username String
	 * @return UserDetails
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
		if (cache != null && cache.get(username) != null) {
			return (LoginUser) cache.get(username).get();
		}
		R<UserInfo> userResult = remoteUserService.getUserInfo(username);
		auth(userResult, username);
		UserDetails userDetails = getUserDetails(userResult);

		if (cache != null) {
			cache.put(username, userDetails);
		}
		return userDetails;
	}

	/**
	 * 根据第三方唯一ID进行获取登录
	 * @param oauth2GrantEnums 枚举第三方平台
	 * @param oAuth2UserId 第三方唯一ID
	 * @return UserDetails
	 * @throws UsernameNotFoundException UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByOAuth2UserId(Oauth2GrantEnums oauth2GrantEnums, String oAuth2UserId)
			throws UsernameNotFoundException {
		if (Oauth2GrantEnums.GITHUB.equals(oauth2GrantEnums)) { // GitHub
			// TODO 实现通过GitHub uuid进行绑定用户信息
			return loadUserByUsername(oAuth2UserId);
		}
		log.info("Oauth2GrantEnums：{} 不存在.", oauth2GrantEnums);
		throw new UsernameNotFoundException("错误的登录类型");
	}

	/**
	 * 自定义账号状态检测
	 * @param userInfo userResult
	 * @param username username
	 */
	private void auth(R<UserInfo> userInfo, String username) {
		SysUser sysUser = ResUtils.of(userInfo).getData().orElseThrow(() -> {
			log.info("登录用户：{} 不存在.", username);
			return new UsernameNotFoundException("登录用户：" + username + " 不存在");
		}).getSysUser();

		// 获取用户状态信息
		if (sysUser.getStatus().equals("1")) {
			log.info("{}： 用户已被冻结.", username);
			throw new UserFrozenException("账号已被冻结");
		}
	}

	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}

}
