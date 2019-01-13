package com.gsh.shoppingmall.service;

import com.gsh.shoppingmall.util.Result;
import com.gsh.shoppingmall.vo.AlipayParamVo;
import com.gsh.shoppingmall.vo.OrderLocalVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author gsh
 * @Title: PayService
 * @Package com.gsh.shoppingmall.service
 * @Description: 支付服务接口
 * @date 2019/1/10 12:59
 */
public interface AlipayService {
    void startPay(HttpServletResponse response, AlipayParamVo alipayParamVo);

    void payment(HttpServletResponse response, OrderLocalVo orderLocalVo);

    Result closeOrder(String orderId, String alipayNo);

    Map<String, String> getPayParams(HttpServletRequest request);

    String alipayReturn(HttpServletRequest request, HttpServletResponse response);

    void alipayNotify(HttpServletRequest request, HttpServletResponse response);

    Result queryOrder(String orderId, String alipayNo);

    Result refund(String orderId, String alipayNo, float money, String reason);

    Result refundQuery(String orderId, String alipayNo, String refundId);

    void cartPayment(HttpServletResponse response, String suserId ,String sadressId) throws InvocationTargetException, IllegalAccessException;
}
