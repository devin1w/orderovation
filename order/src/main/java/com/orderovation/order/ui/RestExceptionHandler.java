package com.orderovation.order.ui;

import com.orderovation.order.infrastructure.exception.ContextAdapterException;
import com.orderovation.order.infrastructure.service.BusiException;
import com.orderovation.order.ui.dto.Rsp;
import com.orderovation.order.ui.dto.RspEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author devin
 */
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ContextAdapterException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Rsp handleContextAdapterException(ContextAdapterException e) {
        return Rsp.error(RspEnum.CONTEXT_ERROR);
    }

    @ExceptionHandler(BusiException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Rsp handleBusiException(BusiException e) {
        return Rsp.error(RspEnum.CONTEXT_ERROR, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Rsp handleException(Exception e) {
        return Rsp.error(RspEnum.SYS_ERROR);
    }
}
