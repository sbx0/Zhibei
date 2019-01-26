package cn.sbx0.zhibei.config;

import cn.sbx0.zhibei.entity.Role;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * Shiro 身份校验
 */
public class ShiroRealmConfig extends AuthorizingRealm {
    @Resource
    UserService userService;

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 从凭证中获得用户名
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        // 根据用户名查询用户对象
        User user = userService.findByUsername(username);
        // 查询用户拥有的角色
        Role role = user.getRole();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addStringPermission(role.getName());
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取传过来的用户名
        String username = (String) authenticationToken.getPrincipal();
        // 根据用户名查找数据库中的用户
        User user = userService.findByUsername(username);
        // 用户为空直接拜拜
        if (user == null) {
            return null;
        }
        return new SimpleAuthenticationInfo(user.getName(), user.getPassword(), getName());
    }

}