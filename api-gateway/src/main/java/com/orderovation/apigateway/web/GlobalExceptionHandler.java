package com.orderovation.apigateway.web;

import com.orderovation.apigateway.common.LoginException;
import com.orderovation.apigateway.common.Rsp;
import com.orderovation.apigateway.common.RspEnum;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author 韦盛友
 */
@CrossOrigin
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({LoginException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Rsp loginError(LoginException e) {
        return Rsp.error(RspEnum.LOGIN_ERROR);
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public Rsp authorizationError(AuthorizationException e) {
        return Rsp.error(RspEnum.PERMISSION_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Rsp defaultError(AuthorizationException e) {
        return Rsp.error(RspEnum.BUSI_ERROR);
    }
}
