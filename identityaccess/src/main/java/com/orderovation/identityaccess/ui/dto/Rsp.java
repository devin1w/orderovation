package com.orderovation.identityaccess.ui.dto;

/**
 * @author 韦盛友
 */
public class Rsp {

    private String status;

    private String msg;

    private Object data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    private void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    private void setData(Object data) {
        this.data = data;
    }

    public static Rsp setResult(RspEnum rspEnum, Object object) {
        Rsp rsp = new Rsp();
        rsp.setStatus(rspEnum.getCode());
        rsp.setMsg(rspEnum.getMsg());
        rsp.setData(object);
        return rsp;
    }

    public static Rsp success() {
        Rsp rsp = new Rsp();
        rsp.setStatus(RspEnum.SUCCESS.getCode());
        rsp.setMsg(RspEnum.SUCCESS.getMsg());
        return rsp;
    }

    public static Rsp success(Object object) {
        Rsp rsp = new Rsp();
        rsp.setStatus(RspEnum.SUCCESS.getCode());
        rsp.setMsg(RspEnum.SUCCESS.getMsg());
        rsp.setData(object);
        return rsp;
    }

    public static Rsp error(RspEnum rspEnum) {
        Rsp rsp = new Rsp();
        rsp.setStatus(rspEnum.getCode());
        rsp.setMsg(rspEnum.getMsg());
        return rsp;
    }

    public static Rsp error(RspEnum rspEnum, String message) {
        Rsp rsp = new Rsp();
        rsp.setStatus(rspEnum.getCode());
        rsp.setMsg(message);
        return rsp;
    }
}
