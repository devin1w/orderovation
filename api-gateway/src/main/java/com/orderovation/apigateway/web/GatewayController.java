package com.orderovation.apigateway.web;

import com.orderovation.apigateway.common.LoginException;
import com.orderovation.apigateway.common.Rsp;
import com.orderovation.apigateway.dto.LoginDTO;
import com.orderovation.apigateway.dto.UserDTO;
import com.orderovation.apigateway.po.User;
import com.orderovation.apigateway.service.IdentityService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class GatewayController {

    @Autowired
    private IdentityService identityService;

    @RequestMapping(value = "/login/account", method = RequestMethod.POST)
    public Rsp login(@RequestBody LoginDTO loginDTO) throws LoginException {
        // 1. 创建token
        UsernamePasswordToken token = new UsernamePasswordToken(loginDTO.getUserName(), loginDTO.getPassword());
        // 2. 认证主体
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            User user = this.identityService.findPrincipalByUsername(loginDTO.getUserName());
            SecurityUtils.getSubject().getSession().setAttribute("user", user);
            Map<String, Object> resultMap = new HashMap<>(4);
            resultMap.put("user", user);
            return Rsp.success(resultMap);
        } catch (Exception e) {
            throw new LoginException("登录失败");
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Rsp logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return Rsp.success("注销成功");
    }

    @RequestMapping(value = "/currentUser", method = RequestMethod.POST)
    @ResponseBody
    public Rsp currentUser(@RequestBody UserDTO userDTO) {
        return Rsp.success(this.identityService.getUserDetail(userDTO.getUserId()));
    }

    @RequestMapping(value = "/notices", method = RequestMethod.GET)
    @ResponseBody
    public Rsp notices() {
        return Rsp.success("[]");
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public Rsp test() {
        return Rsp.success("Hello!");
    }
}
