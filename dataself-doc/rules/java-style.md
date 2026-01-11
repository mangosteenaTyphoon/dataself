---
alwaysApply: true
---
# 角色
你是资深的 Java 工程师，精通阿里巴巴 Java 开发规范和 Spring Cloud 微服务架构。

# 实体类 (Domain) 代码示例
```java
package com.shanzhu.dataself.api.system.domain;

import java.io.Serial;
import com.shanzhu.dataself.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 用户对象 sys_user
 * 
 * @author shanzhu
 */
@Schema(description = "用户对象")
public class SysUser extends BaseEntity {
    
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private Long deptId;

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    private String username;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickName;

    /**
     * 用户邮箱
     */
    @Schema(description = "用户邮箱")
    private String email;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    private String phonenumber;

    /**
     * 用户性别
     */
    @Schema(description = "用户性别")
    private String sex;

    /**
     * 用户头像
     */
    @Schema(description = "用户头像")
    private String avatar;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    @Schema(description = "帐号状态")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @Schema(description = "删除标志")
    private String delFlag;

    /**
     * 最后登录IP
     */
    @Schema(description = "最后登录IP")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @Schema(description = "最后登录时间")
    private Date loginDate;

    // getter 和 setter 方法
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // ... 其他 getter/setter 方法

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", getUserId())
                .append("deptId", getDeptId())
                .append("username", getUsername())
                .append("nickName", getNickName())
                .append("email", getEmail())
                .append("phonenumber", getPhonenumber())
                .append("sex", getSex())
                .append("avatar", getAvatar())
                .append("password", getPassword())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("loginIp", getLoginIp())
                .append("loginDate", getLoginDate())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
```

# Controller 层代码示例
```java
package com.shanzhu.dataself.server.system.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.shanzhu.dataself.common.core.controller.TWTController;
import com.shanzhu.dataself.common.core.domain.JsonResult;
import com.shanzhu.dataself.common.core.page.TableDataInfo;
import com.shanzhu.dataself.common.log.annotation.Log;
import com.shanzhu.dataself.common.log.enums.BusinessType;
import com.shanzhu.dataself.common.security.annotation.PreAuthorize;
import com.shanzhu.dataself.common.core.utils.poi.ExcelUtil;
import com.shanzhu.dataself.api.system.domain.SysUser;
import com.shanzhu.dataself.server.system.service.ISysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 用户信息
 * 
 * @author shanzhu
 */
@Tag(description = "SysUserController", name = "用户信息")
@RestController
@RequestMapping("/user")
public class SysUserController extends TWTController {
    
    @Autowired
    private ISysUserService iSysUserService;

    /**
     * 获取用户列表
     */
    @Operation(summary = "获取用户列表")
    @PreAuthorize(hasPermi = "system:user:list")
    @GetMapping("/pageQuery")
    public JsonResult<TableDataInfo<SysUser>> pageQuery(SysUser user) {
        startPage();
        List<SysUser> list = iSysUserService.selectUserList(user);
        return success(getDataTable(list));
    }

    /**
     * 根据用户编号获取详细信息
     */
    @Operation(summary = "根据用户编号获取详细信息")
    @PreAuthorize(hasPermi = "system:user:query")
    @GetMapping(value = { "/", "/{userId}" })
    public JsonResult<SysUser> getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        JsonResult<SysUser> ajax = JsonResult.success();
        if (Objects.nonNull(userId)) {
            ajax.put("user", iSysUserService.selectUserById(userId));
        }
        return ajax;
    }

    /**
     * 新增用户
     */
    @Operation(summary = "新增用户")
    @PreAuthorize(hasPermi = "system:user:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public JsonResult<Void> add(@Validated @RequestBody SysUser user) {
        if (!iSysUserService.checkUserNameUnique(user.getUsername())) {
            return JsonResult.error("新增用户'" + user.getUsername() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && !iSysUserService.checkPhoneUnique(user)) {
            return JsonResult.error("新增用户'" + user.getUsername() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail())
                && !iSysUserService.checkEmailUnique(user)) {
            return JsonResult.error("新增用户'" + user.getUsername() + "'失败，邮箱账号已存在");
        }
        user.setCreateBy(SecurityUtils.getUsername());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return toAjax(iSysUserService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @Operation(summary = "修改用户")
    @PreAuthorize(hasPermi = "system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public JsonResult<Void> edit(@Validated @RequestBody SysUser user) {
        iSysUserService.checkUserAllowed(user);
        if (!iSysUserService.checkUserNameUnique(user.getUsername())) {
            return JsonResult.error("修改用户'" + user.getUsername() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && !iSysUserService.checkPhoneUnique(user)) {
            return JsonResult.error("修改用户'" + user.getUsername() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail())
                && !iSysUserService.checkEmailUnique(user)) {
            return JsonResult.error("修改用户'" + user.getUsername() + "'失败，邮箱账号已存在");
        }
        user.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(iSysUserService.updateUser(user));
    }

    /**
     * 删除用户
     */
    @Operation(summary = "删除用户")
    @PreAuthorize(hasPermi = "system:user:remove")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public JsonResult<Void> remove(@PathVariable Long[] userIds) {
        return toAjax(iSysUserService.deleteUserByIds(userIds));
    }
}
```

# Service 接口层代码示例
```java
package com.shanzhu.dataself.server.system.service;

import java.util.List;
import com.shanzhu.dataself.api.system.domain.SysUser;

/**
 * 用户 业务层
 * 
 * @author shanzhu
 */
public interface ISysUserService {
    
    /**
     * 根据条件分页查询用户列表
     * 
     * @param user 用户信息
     * @return 用户信息集合
     */
    List<SysUser> selectUserList(SysUser user);

    /**
     * 通过用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    SysUser selectUserById(Long userId);

    /**
     * 新增用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    int insertUser(SysUser user);

    /**
     * 修改用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    int updateUser(SysUser user);

    /**
     * 批量删除用户信息
     * 
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    int deleteUserByIds(Long[] userIds);

    /**
     * 校验用户名称是否唯一
     * 
     * @param username 用户名称
     * @return 结果
     */
    boolean checkUserNameUnique(String username);

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    boolean checkPhoneUnique(SysUser user);

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    boolean checkEmailUnique(SysUser user);

    /**
     * 校验用户是否允许操作
     * 
     * @param user 用户信息
     */
    void checkUserAllowed(SysUser user);
}
```

# Service 实现层代码示例
```java
package com.shanzhu.dataself.server.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.shanzhu.dataself.common.core.exception.TWTException;
import com.shanzhu.dataself.common.datascope.annotation.MicroDataScope;
import com.shanzhu.dataself.common.security.utils.SecurityUtils;
import com.shanzhu.dataself.api.system.domain.SysUser;
import com.shanzhu.dataself.server.system.mapper.SysUserMapper;
import com.shanzhu.dataself.server.system.service.ISysUserService;

/**
 * 用户 业务层处理
 * 
 * @author shanzhu
 */
@Service
public class SysUserServiceImpl implements ISysUserService {
    
    @Autowired
    private SysUserMapper userMapper;

    /**
     * 根据条件分页查询用户列表
     * 
     * @param user 用户信息
     * @return 用户信息集合
     */
    @Override
    @MicroDataScope(deptIdField = "d.dept_id", userIdField = "u.user_id")
    public List<SysUser> selectUserList(SysUser user) {
        return userMapper.selectUserList(user);
    }

    /**
     * 通过用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     * 新增用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = TWTException.class)
    public int insertUser(SysUser user) {
        // 新增用户信息
        int rows = userMapper.insertUser(user);
        return rows;
    }

    /**
     * 修改用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = TWTException.class)
    public int updateUser(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 批量删除用户信息
     * 
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = TWTException.class)
    public int deleteUserByIds(Long[] userIds) {
        for (Long userId : userIds) {
            checkUserAllowed(new SysUser(userId));
        }
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 校验用户名称是否唯一
     * 
     * @param username 用户名称
     * @return 结果
     */
    @Override
    public boolean checkUserNameUnique(String username) {
        int count = userMapper.checkUserNameUnique(username);
        return count == 0;
    }

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean checkPhoneUnique(SysUser user) {
        Long userId = Objects.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (Objects.nonNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return false;
        }
        return true;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean checkEmailUnique(SysUser user) {
        Long userId = Objects.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (Objects.nonNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return false;
        }
        return true;
    }

    /**
     * 校验用户是否允许操作
     * 
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user) {
        if (Objects.nonNull(user.getUserId()) && user.isAdmin()) {
            throw new TWTException("不允许操作超级管理员用户");
        }
    }
}
# Mapper 接口层代码示例
```java
package com.shanzhu.dataself.server.system.mapper;

import java.util.List;
import com.shanzhu.dataself.api.system.domain.SysUser;

/**
 * 用户表 数据层
 * 
 * @author shanzhu
 */
public interface SysUserMapper {
    
    /**
     * 根据条件分页查询用户列表
     * 
     * @param sysUser 用户信息
     * @return 用户信息集合
     */
    List<SysUser> selectUserList(SysUser sysUser);

    /**
     * 通过用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    SysUser selectUserById(Long userId);

    /**
     * 新增用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    int insertUser(SysUser user);

    /**
     * 修改用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    int updateUser(SysUser user);

    /**
     * 批量删除用户信息
     * 
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    int deleteUserByIds(Long[] userIds);

    /**
     * 校验用户名称是否唯一
     * 
     * @param username 用户名称
     * @return 结果
     */
    int checkUserNameUnique(String username);

    /**
     * 校验手机号码是否唯一
     *
     * @param phonenumber 手机号码
     * @return 结果
     */
    SysUser checkPhoneUnique(String phonenumber);

    /**
     * 校验email是否唯一
     *
     * @param email 用户邮箱
     * @return 结果
     */
    SysUser checkEmailUnique(String email);
}
```

# Mapper XML 层代码示例
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.shanzhu.dataself.server.system.mapper.SysUserMapper">

    <resultMap type="SysUser" id="SysUserResult">
        <id     property="userId"       column="user_id"      />
        <result property="deptId"       column="dept_id"      />
        <result property="username"     column="username"     />
        <result property="nickName"     column="nick_name"    />
        <result property="email"        column="email"        />
        <result property="phonenumber"  column="phonenumber"  />
        <result property="sex"          column="sex"          />
        <result property="avatar"       column="avatar"       />
        <result property="password"     column="password"     />
        <result property="status"       column="status"       />
        <result property="delFlag"      column="del_flag"     />
        <result property="loginIp"      column="login_ip"     />
        <result property="loginDate"    column="login_date"   />
        <result property="createBy"     column="create_by"    />
        <result property="createTime"   column="create_time"  />
        <result property="updateBy"     column="update_by"    />
        <result property="updateTime"   column="update_time"  />
        <result property="remark"       column="remark"       />
    </resultMap>

    <sql id="selectUserVo">
        select u.user_id, u.dept_id, u.username, u.nick_name, u.email, u.avatar, u.phonenumber, u.password, u.sex, u.status, u.del_flag, u.login_ip, u.login_date, u.create_by, u.create_time, u.remark,
        d.dept_name, d.leader
        from sys_user u
        left join sys_dept d on u.dept_id = d.dept_id
    </sql>

    <select id="selectUserList" parameterType="SysUser" resultMap="SysUserResult">
        select u.user_id, u.dept_id, u.username, u.nick_name, u.email, u.avatar, u.phonenumber, u.sex, u.status, u.del_flag, u.login_ip, u.login_date, u.create_by, u.create_time, u.remark, d.dept_name, d.leader from sys_user u
        left join sys_dept d on u.dept_id = d.dept_id
        where u.del_flag = '0'
        <if test="userId != null and userId != 0">
            AND u.user_id = #{userId}
        </if>
        <if test="username != null and username != ''">
            AND u.username like concat('%', #{username}, '%')
        </if>
        <if test="status != null and status != ''">
            AND u.status = #{status}
        </if>
        <if test="phonenumber != null and phonenumber != ''">
            AND u.phonenumber like concat('%', #{phonenumber}, '%')
        </if>
        <if test="params.beginTime != null and params.beginTime != ''"><!-- 开始时间检索 -->
            AND date_format(u.create_time,'%y%m%d') &gt;= date_format(#{params.beginTime},'%y%m%d')
        </if>
        <if test="params.endTime != null and params.endTime != ''"><!-- 结束时间检索 -->
            AND date_format(u.create_time,'%y%m%d') &lt;= date_format(#{params.endTime},'%y%m%d')
        </if>
        <if test="deptId != null and deptId != 0">
            AND (u.dept_id = #{deptId} OR u.dept_id IN ( SELECT t.dept_id FROM sys_dept t WHERE find_in_set(#{deptId}, ancestors) ))
        </if>
        <!-- 数据范围过滤 -->
        ${params.dataScope}
    </select>

    <select id="selectUserById" parameterType="Long" resultMap="SysUserResult">
        <include refid="selectUserVo"/>
        where u.user_id = #{userId}
    </select>

    <insert id="insertUser" parameterType="SysUser" useGeneratedKeys="true" keyProperty="userId">
        insert into sys_user(
        <if test="userId != null and userId != 0">user_id,</if>
        <if test="deptId != null and deptId != 0">dept_id,</if>
        <if test="username != null and username != ''">username,</if>
        <if test="nickName != null and nickName != ''">nick_name,</if>
        <if test="email != null and email != ''">email,</if>
        <if test="avatar != null and avatar != ''">avatar,</if>
        <if test="phonenumber != null and phonenumber != ''">phonenumber,</if>
        <if test="sex != null and sex != ''">sex,</if>
        <if test="password != null and password != ''">password,</if>
        <if test="status != null and status != ''">status,</if>
        <if test="createBy != null and createBy != ''">create_by,</if>
        <if test="remark != null and remark != ''">remark,</if>
        create_time
        )values(
        <if test="userId != null and userId != ''">#{userId},</if>
        <if test="deptId != null and deptId != ''">#{deptId},</if>
        <if test="username != null and username != ''">#{username},</if>
        <if test="nickName != null and nickName != ''">#{nickName},</if>
        <if test="email != null and email != ''">#{email},</if>
        <if test="avatar != null and avatar != ''">#{avatar},</if>
        <if test="phonenumber != null and phonenumber != ''">#{phonenumber},</if>
        <if test="sex != null and sex != ''">#{sex},</if>
        <if test="password != null and password != ''">#{password},</if>
        <if test="status != null and status != ''">#{status},</if>
        <if test="createBy != null and createBy != ''">#{createBy},</if>
        <if test="remark != null and remark != ''">#{remark},</if>
        sysdate()
        )
    </insert>

    <update id="updateUser" parameterType="SysUser">
        update sys_user
        <set>
            <if test="deptId != null and deptId != 0">dept_id = #{deptId},</if>
            <if test="username != null and username != ''">username = #{username},</if>
            <if test="nickName != null and nickName != ''">nick_name = #{nickName},</if>
            <if test="email != null ">email = #{email},</if>
            <if test="phonenumber != null ">phonenumber = #{phonenumber},</if>
            <if test="sex != null and sex != ''">sex = #{sex},</if>
            <if test="avatar != null and avatar != ''">avatar = #{avatar},</if>
            <if test="password != null and password != ''">password = #{password},</if>
            <if test="status != null and status != ''">status = #{status},</if>
            <if test="loginIp != null and loginIp != ''">login_ip = #{loginIp},</if>
            <if test="loginDate != null">login_date = #{loginDate},</if>
            <if test="updateBy != null and updateBy != ''">update_by = #{updateBy},</if>
            <if test="remark != null">remark = #{remark},</if>
            update_time = sysdate()
        </set>
        where user_id = #{userId}
    </update>

    <update id="deleteUserByIds" parameterType="Long">
        update sys_user set del_flag = '2' where user_id in
        <foreach collection="array" item="userId" open="(" separator="," close=")">
            #{userId}
        </foreach>
    </update>

    <select id="checkUserNameUnique" parameterType="String" resultType="int">
        select count(1) from sys_user where username = #{username} and del_flag = '0' limit 1
    </select>

    <select id="checkPhoneUnique" parameterType="String" resultMap="SysUserResult">
        select user_id, phonenumber from sys_user where phonenumber = #{phonenumber} and del_flag = '0' limit 1
    </select>

    <select id="checkEmailUnique" parameterType="String" resultMap="SysUserResult">
        select user_id, email from sys_user where email = #{email} and del_flag = '0' limit 1
    </select>

</mapper>
```

# 序号生成器 代码示例
```java
    // id序号生成器
    @Bean(name = "demoSequence")
    public Sequence demoSequence(@Autowired SequenceDao sequenceDao) {
       DefaultSequence sequence = new DefaultSequence();
       sequence.setSequenceDao(sequenceDao);
       sequence.setName("demo_id");
       return sequence;
    }
```