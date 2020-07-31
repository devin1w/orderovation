package com.orderovation.identityaccess.ui;

import com.orderovation.identityaccess.ui.dto.Rsp;
import com.orderovation.identityaccess.ui.dto.RspEnum;
import org.springframework.web.bind.annotation.*;

/**
 * @author 韦盛友
 */
@CrossOrigin
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Rsp defaultError(Exception e) {
        e.printStackTrace();
        return Rsp.error(RspEnum.BUSI_ERROR);
    }
}
