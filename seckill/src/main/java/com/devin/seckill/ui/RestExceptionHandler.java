package com.devin.seckill.ui;

import com.devin.seckill.application.BusiException;
import com.devin.seckill.ui.dto.Rsp;
import com.devin.seckill.ui.dto.RspEnum;
import com.mysql.cj.jdbc.exceptions.MySQLTransactionRollbackException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author devin
 */
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BusiException.class)
    @ResponseBody
    public Rsp handleBusiException(BusiException e) {
        return Rsp.error(RspEnum.BUSI_ERROR, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Rsp handleException(Exception e) {
        return Rsp.error(RspEnum.SYS_ERROR, e.getMessage());
    }
}
