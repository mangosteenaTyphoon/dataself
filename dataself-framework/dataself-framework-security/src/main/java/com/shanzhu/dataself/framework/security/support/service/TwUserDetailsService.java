package com.shanzhu.dataself.framework.security.support.service;

import cn.hutool.core.collection.CollUtil;
import com.shanzhu.dataself.api.system.domain.SysUser;
import com.shanzhu.dataself.api.system.model.UserInfo;
import com.shanzhu.dataself.framework.core.constants.SecurityConstants;
import com.shanzhu.dataself.framework.core.domain.R;
import com.shanzhu.dataself.framework.security.constants.Oauth2GrantEnums;
import com.shanzhu.dataself.framework.security.domain.LoginUser;
import org.springframework.core.Ordered;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 自定义实现spring security用户体系
 */
public interface TwUserDetailsService extends UserDetailsService, Ordered {

	/**
	 * 重写此方法，以此支持登录器是否支持此客户端校验
	 * @param clientId 目标客户端
	 * @return true/false
	 */
	default boolean support(String clientId, String grantType) {
		return true;
	}

	/**
	 * 排序值 默认取最大的
	 * @return 排序值
	 */
	default int getOrder() {
		return 0;
	}

	/**
	 * 根据手机号获取登录
	 * @param phone 手机号
	 * @return UserDetails
	 * @throws UsernameNotFoundException UsernameNotFoundException
	 */
	default UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException {
		return null;
	};

	/**
	 * 根据第三方唯一ID进行获取登录
	 * @param oauth2GrantEnums 枚举第三方平台
	 * @param oAuth2UserId 第三方唯一ID
	 * @return UserDetails
	 * @throws UsernameNotFoundException UsernameNotFoundException
	 */
	default UserDetails loadUserByOAuth2UserId(Oauth2GrantEnums oauth2GrantEnums, String oAuth2UserId)
			throws UsernameNotFoundException {
		return null;
	};

	/**
	 * 构建userdetails
	 * @param result 用户信息
	 * @return UserDetails
	 */
	default UserDetails getUserDetails(R<UserInfo> result) {
		UserInfo info = result.getData();

		Set<String> dbAuthsSet = new HashSet<>();
		if (CollUtil.isNotEmpty(info.getRoles())) {
			// 获取角色
			dbAuthsSet.addAll(info.getRoles());
			// 获取权限
			dbAuthsSet.addAll(info.getPermissions());
		}

		Collection<? extends GrantedAuthority> authorities = AuthorityUtils
			.createAuthorityList(dbAuthsSet.toArray(new String[0]));

		SysUser user = info.getSysUser();

		return new LoginUser(user.getUserId(), user.getDeptId(), user.getRoles(), user.getUsername(),
				SecurityConstants.BCRYPT + user.getPassword(), true, true, true, true, authorities);
	}

	/**
	 * 通过用户实体查询
	 * @param loginUser user
	 * @return UserDetails
	 */
	default UserDetails loadUserByUser(LoginUser loginUser) {
		return this.loadUserByUsername(loginUser.getUsername());
	}

}
