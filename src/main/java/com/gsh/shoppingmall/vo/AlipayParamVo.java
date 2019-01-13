package com.gsh.shoppingmall.vo;

import lombok.Data;

/**
    * @Title: AlipayPostVo
    * @Package com.gsh.shoppingmall.vo
    * @Description: 支付宝支付入参Vo
    * @author gsh
    * @date 2019/1/10 13:03
    */
@Data
public class AlipayParamVo {

    /**
     *参数解释:类型|是否必填|最大长度|描述|示例值
     */
    /**
     * String|是|64|商户网站唯一订单号|70501111111S001111119
     */
    private String out_trade_no;
    /**
     * String|是	|9|订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]|9.00
     */
    private String total_amount;
    /**
     * String|是	|256|商品的标题/交易标题/订单标题/订单关键字等。|大乐透
     */
    private String subject;
    /**
     * String|否|128|对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。|	Iphone6 16G
     */
    private String body;
    /**
     * String|是|64|销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY|QUICK_MSECURITY_PAY
     */
    private String product_code;
    /**
     * String|否|6|该笔订单允许的最晚付款时间，逾期将关闭交易。
     * 取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。
     * 该参数数值不接受小数点， 如 1.5h，可转换为 90m。注：若为空，则默认为15d。|90m
     */
    private String timeout_express;
    /**
     * String|否|2|商品主类型：0—虚拟类商品，1—实物类商品注：虚拟类商品不支持使用花呗渠道|0
     */
    private String goods_type;
    /**
     * String|否|32|商户门店编号。该参数用于请求参数中以区分各门店，非必传项。|NJ_001
     */
    private String store_id;

}
