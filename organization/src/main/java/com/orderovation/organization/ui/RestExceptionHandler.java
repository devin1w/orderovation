package com.orderovation.organization.ui;

import com.orderovation.organization.application.BusiException;
import com.orderovation.organization.ui.dto.Rsp;
import com.orderovation.organization.ui.dto.RspEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author devin
 */
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BusiException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Rsp handleContextAdapterException(BusiException e) {
        return Rsp.error(RspEnum.BUSI_ERROR, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Rsp handleException(Exception e) {
        return Rsp.error(RspEnum.SYS_ERROR, e.getMessage());
    }
}
