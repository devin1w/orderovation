package com.orderovation.apigateway.service;

import com.orderovation.apigateway.dto.UserDTO;
import com.orderovation.apigateway.po.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author devin
 */
@FeignClient("IDENTITY-SERVICE")
public interface IdentityService {

    /**
     * 根据用户名查询用户认证信息
     * @param userName userName
     * @return User
     */
    @RequestMapping(value = "/user/principal/name/{userName}", method = RequestMethod.GET)
    User findPrincipalByUsername(@PathVariable String userName);

    /**
     * 根据用户id查询用户基础信息
     * @param userId userId
     * @return UserDTO
     */
    @RequestMapping(value = "/user/id/{userId}", method = RequestMethod.GET)
    UserDTO getUserDetail(@PathVariable String userId);
}
