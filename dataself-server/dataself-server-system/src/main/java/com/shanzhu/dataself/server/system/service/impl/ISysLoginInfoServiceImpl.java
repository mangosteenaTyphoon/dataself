package com.shanzhu.dataself.server.system.service.impl;

import com.shanzhu.dataself.api.system.domain.SysLoginInfo;
import com.shanzhu.dataself.api.system.domain.SysUser;
import com.shanzhu.dataself.server.system.mapper.SysLoginInfoMapper;
import com.shanzhu.dataself.server.system.mapper.SysUserMapper;
import com.shanzhu.dataself.server.system.service.ISysLoginInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 系统操作/访问日志
 */
@Service
public class ISysLoginInfoServiceImpl implements ISysLoginInfoService {

	@Autowired
	private SysLoginInfoMapper sysLoginInfoMapper;

	@Autowired
	private SysUserMapper sysUserMapper;

	/**
	 * 查询系统登录日志集合
	 * @param loginInfo 访问日志对象
	 * @return List<SysLoginInfo>
	 */
	@Override
	public List<SysLoginInfo> selectLoginInfoList(SysLoginInfo loginInfo) {
		return sysLoginInfoMapper.selectLoginInfoList(loginInfo);
	}

	/**
	 * 批量删除系统登录日志
	 * @param infoIds 需要删除的登录日志ID
	 * @return 操作结果
	 */
	@Override
	public int deleteLoginInfoByIds(Long[] infoIds) {
		return sysLoginInfoMapper.deleteLoginInfoByIds(infoIds);
	}

	/**
	 * 清空系统登录日志
	 */
	@Override
	public void cleanLoginInfo() {
		sysLoginInfoMapper.cleanLoginInfo();
	}

	/**
	 * 新增系统登录日志
	 * @param loginInfo 访问日志对象
	 */
	@Override
	public int insertLoginInfo(SysLoginInfo loginInfo) {
		String userName = loginInfo.getUserName();

		if (StringUtils.isNotEmpty(userName)) {
			SysUser sysUser = sysUserMapper.selectUserByUserName(userName);
			if (Objects.nonNull(sysUser)) {
				Long deptId = sysUser.getDeptId();
				loginInfo.setDeptId(deptId);
				return sysLoginInfoMapper.insertLoginInfo(loginInfo);
			}
		}

		return 0;
	}

}
