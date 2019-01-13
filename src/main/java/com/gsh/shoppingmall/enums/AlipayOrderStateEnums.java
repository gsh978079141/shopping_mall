package com.gsh.shoppingmall.enums;
/**
    * @Title: AlipayOrderStateEnums
    * @Package com.gsh.shoppingmall.enums
    * @Description:   支付宝订单状态枚举
    * @author gsh
    * @date 2019/1/10 14:27
    */
public enum AlipayOrderStateEnums {
    WAIT_BUYER_PAY("WAIT_BUYER_PAY","等待支付"),
    TRADE_CLOSED("TRADE_CLOSED","交易关闭"),
    TRADE_FINISHED("TRADE_FINISHED","交易结束"),
    TRADE_SUCCESS("TRADE_SUCCESS","交易成功"),
    ;

    private String code;
    private String message;

    private AlipayOrderStateEnums(String code, String message) {
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
