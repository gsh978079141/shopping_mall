package com.gsh.shoppingmall.vo;
/**
    * @Title: OrderLocalVo
    * @Package com.gsh.shoppingmall.vo
    * @Description:
    * @author gsh
    * @date 2019/1/10 11:09
    */
public class OrderLocalVo {
    /**
     * 支付来源1立即购买2购物车结算
     */
    private Integer payType;

    private Integer userId;

    private Integer goodId;

    private Integer adressId;

    private Integer num;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public Integer getAdressId() {
        return adressId;
    }

    public void setAdressId(Integer adressId) {
        this.adressId = adressId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }
}
