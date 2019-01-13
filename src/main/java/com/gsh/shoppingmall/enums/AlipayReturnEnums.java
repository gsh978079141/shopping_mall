package com.gsh.shoppingmall.enums;
/**
    * @Title: AlipayEnums
    * @Package com.gsh.shoppingmall.enums
    * @Description:   支付宝返回码枚举
    * @author gsh
    * @date 2019/1/10 14:27
    */
public enum AlipayReturnEnums {
    AlipayReturn_1000("1000", "接口调用成功"),
    AlipayReturn_20000("20000", "服务不可用"),
    AlipayReturn_20001("20001", "授权权限不足"),
    AlipayReturn_40001("40001", "缺少必选参数"),
    AlipayReturn_40002("40002", "非法的参数"),
    AlipayReturn_40004("40004", "业务处理失败"),
    AlipayReturn_40006("40006", "权限不足"),
    ;

    private String code;
    private String message;
    
    private AlipayReturnEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
