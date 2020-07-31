package com.orderovation.apigateway.auth;

import com.orderovation.apigateway.po.User;
import com.orderovation.apigateway.service.IdentityService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private IdentityService identityService;

    /**
     * 授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return new SimpleAuthorizationInfo();
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1. 获取用户标识
        String username = (String) authenticationToken.getPrincipal();
        // 2. 查询用户信息
        User user = identityService.findPrincipalByUsername(username);
        // 3. 认证
        if (user == null) {
            throw new UnknownAccountException("用户不存在!");
        } else {
            // 4. 用户真实认证信息
            return new SimpleAuthenticationInfo(
                    user.getUsername(), user.getPassword(), this.getName()
            );
        }
    }
}
