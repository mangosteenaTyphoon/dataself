package com.shanzhu.dataself.framework.security.support.grant.oauth2.github;

import com.shanzhu.dataself.framework.security.support.base.OAuth2ResourceOwnerBaseAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Map;
import java.util.Set;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 密码授权token信息
 */
public class OAuth2ResourceOwnerGitHubAuthenticationToken extends OAuth2ResourceOwnerBaseAuthenticationToken {

	/**
	 * AuthenticationToken
	 */
	private final Object principal;

	private Object credentials;

	public OAuth2ResourceOwnerGitHubAuthenticationToken(AuthorizationGrantType authorizationGrantType,
			Authentication clientPrincipal, Set<String> scopes, Map<String, Object> additionalParameters) {
		super(authorizationGrantType, clientPrincipal, scopes, additionalParameters);
		this.principal = clientPrincipal;
	}

	public OAuth2ResourceOwnerGitHubAuthenticationToken(UserDetails sysUser) {
		super(sysUser.getAuthorities());
		this.principal = sysUser;
		super.setAuthenticated(true); // 设置认证成功 必须
	}

	/**
	 * AuthenticationToken
	 * @return Object
	 */
	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	/**
	 * credentials
	 * @return credentials
	 */
	@Override
	public Object getCredentials() {
		return this.credentials;
	}

}
