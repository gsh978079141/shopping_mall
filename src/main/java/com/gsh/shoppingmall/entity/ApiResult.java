package com.gsh.shoppingmall.entity;
/**
    * @Title: ApiResult
    * @Package com.gsh.shoppingmall.entity
    * @Description: 统一返回信息
    * @author gsh
    * @date 2019/1/2 17:13
    */
public class ApiResult<T> {

    /*错误码*/
    private Integer code;

    /*提示信息 */
    private String msg;

    /*具体内容*/
    private  T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
