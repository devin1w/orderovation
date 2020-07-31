package com.devin.seckill.ui.dto;

/**
 * @author 韦盛友
 */
public enum RspEnum {
    // 执行成功
    SUCCESS("0000", "执行成功"),
    // 系统异常
    SYS_ERROR("9999", "系统异常"),
    // 业务异常
    BUSI_ERROR("8888", "业务异常"),
    // 服务层异常
    SERVICE_ERROR("9998", "服务层异常"),
    // 数据层异常
    DAO_ERROR("9997", "数据层异常"),
    // 登录失败
    LOGIN_ERROR("7777", "登录失败"),
    // 非法调用
    ILLEGAL_REQUEST("6666", "非法调用"),
    // 外围上下文异常
    CONTEXT_ERROR("5555", "外围上下文异常"),
    // 权限不足
    PERMISSION_ERROR("1111", "权限不足");

    private String code;
    private String msg;

    RspEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
