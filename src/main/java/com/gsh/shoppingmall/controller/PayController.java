package com.gsh.shoppingmall.controller;

import com.gsh.shoppingmall.service.*;
import com.gsh.shoppingmall.util.Result;
import com.gsh.shoppingmall.vo.OrderLocalVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

/**
 * 支付Controller
 * Wiki：
 * https://docs.open.alipay.com/270/105900/
 * https://docs.open.alipay.com/270/105902/
 * @author gsh
 * @since 2018/6/3 13:59
 */
@Controller
@Slf4j
public class PayController {

    @Autowired
    private AlipayService alipayService;

    @RequestMapping("/")
    public String showIndex() {
        return "index.html";
    }

    @PostMapping("/alipay/payment")
    public void payment(HttpServletResponse response , OrderLocalVo orderLocalVo) {
        alipayService.payment(response,orderLocalVo);
    }

    /**
     * 购物车支付宝结算
     * 该方法无返回值，执行成功后response回写结果即可
     * @param userId 用户id(String)
     * @author gsh
     */
    @PostMapping("/alipay/cartPayment")
    public void cartPayment( HttpServletResponse response , String userId, String adressId) throws InvocationTargetException, IllegalAccessException {
        alipayService.cartPayment(response,userId,adressId);
    }

    /**
     * 该方式仅仅在买家付款完成以后进行自动跳转，因此只会进行一次
     * 支付宝服务器同步通知页面，获取支付宝GET过来反馈信息
     * 该方法执行完毕后跳转到成功页即可
     * （1）该方式不是支付宝主动去调用商户页面，而是支付宝的程序利用页面自动跳转的函数，使用户的当前页面自动跳转；
     * （2）返回URL只有一分钟的有效期，超过一分钟该链接地址会失效，验证则会失败
     * （3）可在本机而不是只能在服务器上进行调试
     * @author gsh
     * @since 2018/6/4 15:06
     */
    @GetMapping("/alipay/return")
    public String alipayReturn(HttpServletRequest request,  HttpServletResponse response ) {
       return alipayService.alipayReturn(request,response);
    }
    
    /**
     * 服务器异步通知，获取支付宝POST过来反馈信息
     * 该方法无返回值，静默处理
     * 订单的状态已该方法为主，其他的状态修改方法为辅 *
     * （1）程序执行完后必须打印输出“success”（不包含引号）。
     * 如果商户反馈给支付宝的字符不是success这7个字符，支付宝服务器会不断重发通知，直到超过24小时22分钟。
     * （2）程序执行完成后，该页面不能执行页面跳转。
     * 如果执行页面跳转，支付宝会收不到success字符，会被支付宝服务器判定为该页面程序运行出现异常，而重发处理结果通知
     * （3）cookies、session等在此页面会失效，即无法获取这些数据
     * （4）该方式的调试与运行必须在服务器上，即互联网上能访问 *
     * @author gsh
     * @since 2018/6/4 14:45
     */
    @PostMapping("/alipay/notify")
    public void alipayNotify(HttpServletRequest request,  HttpServletResponse response){
        alipayService.alipayNotify(request,response);
    }

    /**
     * 交易查询
     * 以下两个参数任一即可
     * @param orderId 订单ID
     * @param alipayNo 支付宝交易号
     * @author gsh
     * @since 2018/6/4 19:15
     */
    @PostMapping("/alipay/query")
    @ResponseBody
    public Result queryOrder(String orderId, String alipayNo) {
        return alipayService.queryOrder(orderId,alipayNo);
    }

    /**
     * 退款
     * 订单ID、支付宝交易号任一即可
     * https://docs.open.alipay.com/api_1/alipay.trade.refund
     * @param orderId 订单ID
     * @param alipayNo 支付宝交易号
     * @param money 退款金额，注意不要大于支付金额
     * @param reason 退款原因
     * @author gsh
     * @since 2018/6/4 20:08
     */
    @PostMapping("/alipay/refund")
    @ResponseBody
    public Result refund(String orderId, String alipayNo, float money, String reason) {
        return alipayService.refund(orderId,alipayNo,money,reason);
    }

    /**
     * 退款记录查询
     * orderId和alipayNo二选一即可
     * refundId可选，如果不填，则查询该订单的所有退款疾苦
     * @param orderId 订单ID
     * @param alipayNo 支付宝交易号
     * @param refundId 退款请求号
     * @author gsh
     * @since 2018/6/4 22:01
     */
    @PostMapping("/alipay/refund/query")
    @ResponseBody
    public Result refundQuery(String orderId, String alipayNo, String refundId) {
        return  alipayService.refundQuery(orderId,alipayNo,refundId);
    }

    /**
     * 交易关闭
     * orderId和alipayNo二选一即可
     * @author gsh
     * @since 2018/6/4 22:27
     */
    @PostMapping("/alipay/close")
    @ResponseBody
    private Result closeOrder(String orderId, String alipayNo) {
        return  alipayService.closeOrder(orderId,alipayNo);
    }
}