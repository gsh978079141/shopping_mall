package com.gsh.shoppingmall.serviceImpl;

import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.gsh.shoppingmall.config.AliPayConfig;
import com.gsh.shoppingmall.entity.*;
import com.gsh.shoppingmall.enums.AlipayReturnEnums;
import com.gsh.shoppingmall.exception.CustomException;
import com.gsh.shoppingmall.service.*;
import com.gsh.shoppingmall.util.JsonUtils;
import com.gsh.shoppingmall.util.RandomUtils;
import com.gsh.shoppingmall.util.Result;
import com.gsh.shoppingmall.util.ResultEnum;
import com.gsh.shoppingmall.vo.AlipayParamVo;
import com.gsh.shoppingmall.vo.OrderLocalVo;
import com.gsh.shoppingmall.vo.ShopCartResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author gsh
 * @Title: PayServiceImpl
 * @Package com.gsh.shoppingmall.serviceImpl
 * @Description: 支付宝支付服务实现
 * @date 2019/1/10 13:00
 */
@Service
@Slf4j
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private AliPayConfig aliPayConfig;
    @Autowired
    private AlipayClient alipayClient;
    @Autowired
    private OrderLocalService orderLocalService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private AdressService adressService;
    @Autowired
    private OrderRefundService orderRefundService;
    @Autowired
    private ShopCartService shopCartService;

    /**
     * 发起支付宝支付请求
     *
     * @param response
     * @param alipayParamVo
     * @return
     */
    @Override
    public void startPay(HttpServletResponse response, AlipayParamVo alipayParamVo) {
        // 1、设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        // 页面跳转同步通知页面路径
        alipayRequest.setReturnUrl(aliPayConfig.getReturnUrl());
        // 服务器异步通知页面路径
        alipayRequest.setNotifyUrl(aliPayConfig.getNotifyUrl());

        // 2、SDK已经封装掉了公共参数，这里只需要传入业务参数，请求参数查阅开头Wiki
        Map<String, String> map = new HashMap<>(16);
        // 2.1、通过反射拼接参数map
        Field fields[] = alipayParamVo.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.get(alipayParamVo) != null) {
                    map.put(field.getName(), field.get(alipayParamVo).toString());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        alipayRequest.setBizContent(JsonUtils.objectToJson(map));
        response.setContentType("text/html;charset=utf-8");
        try {
            // 3、生成支付表单
            AlipayTradePagePayResponse alipayResponse = alipayClient.pageExecute(alipayRequest);
            if (alipayResponse.isSuccess()) {
                String result = alipayResponse.getBody();
                response.getWriter().write(result);
            } else {
                log.error("【支付表单生成】失败，错误信息：{}", alipayResponse.getSubMsg());
                response.getWriter().write("error");
            }
        } catch (Exception e) {
            log.error("【支付表单生成】异常，异常信息：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 支付宝支付
     * 该方法无返回值，执行成功后response回写结果即可
     *
     * @param orderLocalVo 订单信息 subject 订单名称 body 订单描述
     * @author gsh
     */
    @Override
    public void payment(HttpServletResponse response, OrderLocalVo orderLocalVo) {

        // 支付宝订单-->订单名称(必传)
        List<String> subject = new ArrayList<>();
        // 支付宝订单-->订单详情(必传)
        List<String> body = new ArrayList<>();
        // 本地订单-->地址信息
        Adress adress = adressService.getById(orderLocalVo.getAdressId());
        // 本地订单-->开始
        List<OrderLocal> orderLocalList = new ArrayList<>();
        // 本地订单-->总价格
        float totalMoney = 0;
        //支付宝订单-->初始化
        OrderInfo orderInfo = new OrderInfo();
        if (orderLocalVo.getPayType() == 1) {
            Good good = goodService.getById(orderLocalVo.getGoodId());
            subject.add(good.getName());
            body.add(good.getTitle());
            totalMoney = orderLocalVo.getNum() * (good.getPrice().floatValue());
            // 支付宝订单-->金额(保留两位)
            float money = Math.round(totalMoney * 100) / 100;
            // 支付宝订单-->生成
            orderInfo = orderInfoService.createOrder(subject.toString(), body.toString(), money, aliPayConfig.getSellerId());
            OrderLocal orderLocal = new OrderLocal();
            // 关联支付宝订单号
            orderLocal.setAlipayOrdNum(orderInfo.getOrderId());
            // 本地订单-->商品信息
            orderLocal.setGoodName(good.getName());
            orderLocal.setGoodPrice(good.getPrice());
            orderLocal.setAdressInfo(adress.getAdress());
            orderLocal.setGoodDetail(good.getDetail());
            orderLocal.setUserId(orderLocalVo.getUserId());
            orderLocalList.add(orderLocal);
        } else if (orderLocalVo.getPayType() == 2) {
            //购物车需要删除的id
            List<Integer> shopCartIds = new ArrayList<>();
            //S  购物车付款
            //获得购物车商品详情信息列表
            List<ShopCartResultVo> listShopCart = shopCartService.listInfo(orderLocalVo.getUserId());
            //支付宝订单-->参数
            for (ShopCartResultVo shopCartResultVo : listShopCart) {
                float price = (shopCartResultVo.getPrice()).floatValue();
                int num = shopCartResultVo.getNum();
                subject.add(shopCartResultVo.getName());
                body.add(shopCartResultVo.getTitle());
                totalMoney += price * num;
            }
            // 支付宝订单-->金额(保留两位)
            float money = Math.round(totalMoney * 100) / 100;
            // 支付宝订单-->生成
            orderInfo = orderInfoService.createOrder(subject.toString(), body.toString(), money, aliPayConfig.getSellerId());
            // 本地订单-->生成

            for (ShopCartResultVo shopCartResultVo : listShopCart) {
                shopCartIds.add(shopCartResultVo.getId());
                OrderLocal orderLocal = new OrderLocal();
//            BeanUtils.copyProperties(shopCartResultVo,orderLocal);
                orderLocal.setAdressInfo(adress.getAdress());
                orderLocal.setGoodDetail(shopCartResultVo.getDetail());
                orderLocal.setGoodName(shopCartResultVo.getName());
                orderLocal.setGoodNum(shopCartResultVo.getNum());
                orderLocal.setGoodPrice(shopCartResultVo.getPrice());
                orderLocal.setUserId(orderLocalVo.getUserId());
                //关联支付宝订单号
                orderLocal.setAlipayOrdNum(orderInfo.getOrderId());
                orderLocalList.add(orderLocal);
            }
            //删除购物车付款的商品
            shopCartService.removeByIds(shopCartIds);
        }

        // 支付宝订单-->金额(保留两位)
        float money = Math.round(totalMoney * 100) / 100;
        // 本地订单-->入库
        orderLocalService.saveBatch(orderLocalList);
        // 本地订单-->结束
        // 发起支付-->开始
        // 发起支付-->入参封装
        AlipayParamVo alipayParamVo = new AlipayParamVo();
        alipayParamVo.setSubject(subject.toString());
        alipayParamVo.setBody(body.toString());
        alipayParamVo.setOut_trade_no(orderInfo.getOrderId());
        alipayParamVo.setTotal_amount(money + "");
        alipayParamVo.setProduct_code("FAST_INSTANT_TRADE_PAY");
        startPay(response, alipayParamVo);
        // 发起支付-->结束
    }

    /**
     * 购物车支付宝结算
     * 该方法无返回值，执行成功后response回写结果即可
     *
     * @param suserId   用户id(String)
     * @param sadressId 用户收货地址id(String)
     * @author gsh
     */

    @Override
    public void cartPayment(HttpServletResponse response, String suserId, String sadressId) {
        //用户id
        Integer userId = Integer.parseInt(suserId);
        //用户收货地址
        Integer adressId = Integer.parseInt(sadressId);
        Adress adress = adressService.getById(adressId);
        // 支付宝订单-->订单名称(必传)
        List<String> subject = new ArrayList<>();
        // 支付宝订单-->订单详情(必传)
        List<String> body = new ArrayList<>();
        //获得购物车商品详情信息列表
        List<ShopCartResultVo> listShopCart = shopCartService.listInfo(userId);
        // 支付宝订单-->总金额(必传)
        float totalMoney = 0;
        //支付宝订单-->参数

        // 支付宝订单-->总金额(保留两位)
        float money = Math.round(totalMoney * 100) / 100;
        // 生成支付宝订单
        OrderInfo orderInfo = orderInfoService.createOrder(subject.toString(), body.toString(), money, aliPayConfig.getSellerId());
        //本地订单-->开始
        List<OrderLocal> orderLocalList = new ArrayList<>();

        //本地订单-->批量保存
        orderLocalService.saveBatch(orderLocalList);
        // 发起支付-->入参封装
        AlipayParamVo alipayParamVo = new AlipayParamVo();
        alipayParamVo.setSubject(subject.toString());
        alipayParamVo.setBody(body.toString());
        alipayParamVo.setOut_trade_no(orderInfo.getOrderId());
        alipayParamVo.setTotal_amount(money + "");
        alipayParamVo.setProduct_code("FAST_INSTANT_TRADE_PAY");
        startPay(response, alipayParamVo);
        // 发起支付-->结束
    }

    /**
     * 该方式仅仅在买家付款完成以后进行自动跳转，因此只会进行一次
     * 支付宝服务器同步通知页面，获取支付宝GET过来反馈信息
     * 该方法执行完毕后跳转到成功页即可
     * （1）该方式不是支付宝主动去调用商户页面，而是支付宝的程序利用页面自动跳转的函数，使用户的当前页面自动跳转；
     * （2）返回URL只有一分钟的有效期，超过一分钟该链接地址会失效，验证则会失败
     * （3）可在本机而不是只能在服务器上进行调试
     *
     * @author gsh
     * @since 2018/6/4 15:06
     */
    @Override
    public String alipayReturn(HttpServletRequest request, HttpServletResponse response) {
        // 获取参数
        Map<String, String> params = getPayParams(request);
        try {
            // 验证订单
            boolean flag = orderInfoService.validOrder(params);
            if (flag) {
                // 验证成功后，修改订单状态为已支付
                String orderId = params.get("out_trade_no");
                /*
                 * 订单状态（与官方统一）
                 * WAIT_BUYER_PAY：交易创建，等待买家付款；
                 * TRADE_CLOSED：未付款交易超时关闭，或支付完成后全额退款；
                 * TRADE_SUCCESS：交易支付成功；
                 * TRADE_FINISHED：交易结束，不可退款
                 */
                // 获取支付宝订单号
                String tradeNo = params.get("trade_no");
                // 更新状态
                orderInfoService.changeStatus(orderId, "TRADE_SUCCESS", tradeNo);

//                response.setContentType("text/html;charset=utf-8");
//                response.getWriter().write("<!DOCTYPE html>\n" +
//                        "<html lang=\"en\">\n" +
//                        "<head>\n" +
//                        "    <meta charset=\"UTF-8\">\n" +
//                        "    <title>pay success</title>\n" +
//                        "</head>\n" +
//                        "<body>\n" +
//                        "\n" +
//                        "<div class=\"container\">\n" +
//                        "    <div class=\"row\">\n" +
//                        "        <p>orderId："+orderId+"</p>\n" +
//                        "        <p>tradeNo："+tradeNo+"</p>\n" +
//                        "        <a href=\"/\">home</a>\n" +
//                        "    </div>\n" +
//                        "</div>\n" +
//                        "\n" +
//                        "</body>\n" +
//                        "</html>");
                return "redirect:http://www.njhxsc.com/a/shopping_mall/";
            } else {
                log.error("【支付宝同步方法】验证失败");
                return "redirect:http://www.njhxsc.com/a/shopping_mall/";
//                response.getWriter().write("支付验证失败");
            }
        } catch (Exception e) {
            log.error("【支付宝同步方法】异常，异常信息：{}", e.getMessage());
            e.printStackTrace();
        }
        return "redirect:http://www.njhxsc.com/a/shopping_mall/";
    }

    /**
     * 获取支付参数
     *
     * @author gsh
     * @since 2018/6/4 16:39
     */
    @Override
    public Map<String, String> getPayParams(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>(16);
        Map<String, String[]> requestParams = request.getParameterMap();

        Iterator<String> iter = requestParams.keySet().iterator();
        while (iter.hasNext()) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        return params;
    }


    /**
     * 交易关闭
     * orderId和alipayNo二选一即可
     *
     * @author gsh
     * @since 2018/6/4 22:27
     */
    @Override
    public Result closeOrder(String orderId, String alipayNo) {
        //设置请求参数
        AlipayTradeCloseRequest alipayRequest = new AlipayTradeCloseRequest();
        Map<String, String> map = new HashMap<>(16);
        map.put("out_trade_no", orderId);
        map.put("trade_no", alipayNo);
        alipayRequest.setBizContent(JsonUtils.objectToJson(map));

        try {
            //请求
            String json = alipayClient.execute(alipayRequest).getBody();

            Map<String, Object> resMap = JsonUtils.jsonToPojo(json, Map.class);
            Map<String, String> responseMap = (Map) resMap.get("alipay_trade_close_response");

            String code = responseMap.get("code");
            if ("10000".equals(code)) {
                // 将状态更改为关闭
                String outTradeNo = responseMap.get("out_trade_no");
                orderInfoService.changeStatus(outTradeNo, "TRADE_CLOSED");
                return Result.ok();
            } else {
                log.error("【支付宝交易关闭】失败，错误原因：{}", responseMap.get("sub_msg"));
                return Result.error(ResultEnum.ALIPAY_CLOSE_ERROR);
            }
        } catch (Exception e) {
            log.error("【支付宝交易关闭】异常，异常原因：{}", e.getMessage());
            e.printStackTrace();
        }
        return Result.error(ResultEnum.ALIPAY_CLOSE_ERROR);
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
     *
     * @author gsh
     * @since 2018/6/4 14:45
     */
    @Override
    public void alipayNotify(HttpServletRequest request, HttpServletResponse response) {
        /*
         默认只有TRADE_SUCCESS会触发通知，如果需要开通其他通知，请联系客服申请
         触发条件名 	    触发条件描述 	触发条件默认值
        TRADE_FINISHED 	交易完成 	false（不触发通知）
        TRADE_SUCCESS 	支付成功 	true（触发通知）
        WAIT_BUYER_PAY 	交易创建 	false（不触发通知）
        TRADE_CLOSED 	交易关闭 	false（不触发通知）
        来源：https://docs.open.alipay.com/270/105902/#s2
         */
        // 获取参数
        Map<String, String> params = getPayParams(request);
        try {
            // 验证订单
            boolean flag = orderInfoService.validOrder(params);
            if (flag) {
                //商户订单号
                String orderId = params.get("out_trade_no");
                //支付宝交易号
                String tradeNo = params.get("trade_no");
                //交易状态
                String tradeStatus = params.get("trade_status");

                switch (tradeStatus) {
                    case "WAIT_BUYER_PAY":
                        orderInfoService.changeStatus(orderId, tradeStatus);
                        break;
                    /*
                     * 关闭订单
                     * （1)订单已创建，但用户未付款，调用关闭交易接口
                     * （2）付款成功后，订单金额已全部退款【如果没有全部退完，仍是TRADE_SUCCESS状态】
                     */
                    case "TRADE_CLOSED":
                        orderInfoService.changeStatus(orderId, tradeStatus);
                        break;
                    /*
                     * 订单完成
                     * （1）退款日期超过可退款期限后
                     */
                    case "TRADE_FINISHED":
                        orderInfoService.changeStatus(orderId, tradeStatus);
                        break;
                    /*
                     * 订单Success
                     * （1）用户付款成功
                     */
                    case "TRADE_SUCCESS":
                        orderInfoService.changeStatus(orderId, tradeStatus, tradeNo);
                        break;
                    default:
                        break;
                }
                response.getWriter().write("success");
            } else {
                log.error("【支付异步方法】验证失败，错误信息：{}", AlipaySignature.getSignCheckContentV1(params));
                response.getWriter().write("fail");
            }
        } catch (Exception e) {
            log.error("【支付异步方法】异常，异常信息：{}", e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * 交易查询
     * 以下两个参数任一即可
     *
     * @param orderId  订单ID
     * @param alipayNo 支付宝交易号
     * @author gsh
     * @since 2018/6/4 19:15
     */
    @Override
    public Result queryOrder(String orderId, String alipayNo) {
        // 1、设置请求参数
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
        Map<String, String> map = new HashMap<>(16);
        map.put("out_trade_no", orderId);
        map.put("trade_no", alipayNo);
        alipayRequest.setBizContent(JsonUtils.objectToJson(map));

        try {
            // 2、请求
            String json = alipayClient.execute(alipayRequest).getBody();
            Map<String, Object> resMap = JsonUtils.jsonToPojo(json, Map.class);
            Map<String, String> responseMap = (Map) resMap.get("alipay_trade_query_response");

            // 获得返回状态码，具体参考：https://docs.open.alipay.com/common/105806
            String code = responseMap.get("code");
            if (AlipayReturnEnums.AlipayReturn_1000.getCode().equals(code)) {
                // 获取查询结果
                String outTradeNo = responseMap.get("out_trade_no");
                OrderInfo orderInfo = orderInfoService.getById(outTradeNo);
                Map<String, Object> result = new HashMap<>(16);
                result.put("orderInfo", orderInfo);
                result.put("buyer_logon_id", responseMap.get("buyer_logon_id"));
                result.put("trade_status", responseMap.get("trade_status"));
                return Result.ok(null, result);
            } else {
                log.error("【支付宝查询】错误，错误码：{}，错误信息：{}", code, responseMap.get("sub_msg"));
                return Result.error(ResultEnum.ALIPAY_QUERY_ERROR);
            }
        } catch (Exception e) {
            log.error("【支付宝查询】异常，异常信息：{}", e.getMessage());
            e.printStackTrace();
        }

        return Result.error(ResultEnum.ALIPAY_QUERY_ERROR);
        /*
         {
            "alipay_trade_query_response":{
                "code":"10000",
                "msg":"Success",
                "buyer_logon_id":"uce***@sandbox.com",
                "buyer_pay_amount":"0.00",
                "buyer_user_id":"2088102176077881",
                "buyer_user_type":"PRIVATE",
                "invoice_amount":"0.00",
                "out_trade_no":"152810603232866",
                "point_amount":"0.00",
                "receipt_amount":"0.00",
                "send_pay_date":"2018-06-04 17:54:04",
                "total_amount":"2.00",
                "trade_no":"2018060421001004880200500828",
                "trade_status":"TRADE_SUCCESS"
            },
            "sign":"HqdTcGWWhW4ivZxPNpdZfUkwHsVKg9eQZ2/Z17XA4wngMk3bOFmYYgYX5DwGPxccywyvxa+L7sUDZXQoxMYg2zcbPCLkn2poLCC51IAqCibo8R9F98cLFsjeKIFQ6Mw4a30lcFjr+esRTa8T7bJsoqRl4HX7B1qvMcarWJdBGN8AX3MIRmAWrqs2N4AULUghPucJKsApTi/CVebGYlf2e3cakxUhTos/Rw0Y3kvjwFaDBm18QZAt8xQ5dkYfFEEQuxDNkPYrxZTuAlp5M6BbEzbIf3z1iRBSkLuA7VfpZiZUNDw6dXLmpIaZZJK+3/Ltu3aOUJLlRR7EQ9PX7rDJ6g=="
        }

        {
            "alipay_trade_query_response":{
                "code":"40004",
                "msg":"Business Failed",
                "sub_code":"ACQ.TRADE_NOT_EXIST",
                "sub_msg":"交易不存在",
                "buyer_pay_amount":"0.00",
                "invoice_amount":"0.00",
                "out_trade_no":"1528106032328",
                "point_amount":"0.00",
                "receipt_amount":"0.00"
            },
            "sign":"BcGLHOlzPryBbF8FvuvvtA/8vItcZRawWJ7kX4SRKRxomB+h6kq+SzJG9xMs8N24CPA144D9EXBBCqAGPoj149pBBFHhmFwnBEFDpNrBrfB4MAfsJndK6xvYeaCoOXqgqs3f7tfOiDbUVOMuLKZYZTSm0N/UA2OKXUXT1aPVeLLMVuKPwBXZY7MvpbWxNVLqRz2Qmf6n1i4o4hl9p5ywiNIR9FgvAvN2Dwyd32QED1cYcfPXWJWGjWKucrsCcZIfetput+ZyYWxxG4l3GBQ32fXrAx1vbkfDtAdRmeStAfvUL3adiZpplEXil3AGgIckSlUfDF7hHG92Bnd1U9LYNg=="
        }
         */
    }


    /**
     * 退款
     * 订单ID、支付宝交易号任一即可
     * https://docs.open.alipay.com/api_1/alipay.trade.refund
     *
     * @param orderId  订单ID
     * @param alipayNo 支付宝交易号
     * @param money    退款金额，注意不要大于支付金额
     * @param reason   退款原因
     * @author gsh
     * @since 2018/6/4 20:08
     */
    @Override
    public Result refund(String orderId, String alipayNo, float money, String reason) {
        if (money == 0) {
            throw new CustomException(ResultEnum.MONEY_ERROR);
        }
        // 金额保留两位
        money = (float) (Math.round(money * 100)) / 100;

        OrderInfo orderInfo = orderInfoService.getByIdOrAlipayNo(orderId, alipayNo);
        if (orderId == null) {
            throw new CustomException(ResultEnum.ORDER_NOT_EXIST);
        }

        // 只有订单状态为TRADE_SUCCESS，才能退款
        if (!"TRADE_SUCCESS".equals(orderInfo.getStatus())) {
            throw new CustomException(ResultEnum.ORDER_STATUS_NOT_SUPPORT);
        }

        // 判断金额是否足够退款（注：即使不判断，金额不够也无法退款成功）
        float totalMoney = orderInfo.getMoney();
        float refundMoney = orderInfo.getRefundMoney();
        float canRefundMoney = (float) (Math.round((totalMoney - refundMoney) * 100)) / 100;
        if (canRefundMoney < money) {
            throw new CustomException(ResultEnum.PARAMS_ERROR.getCode(),
                    "退款金额超过可退款金额，最大退款金额：" + canRefundMoney);
        }

        // 生成退款请求号，标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
        String refundId = RandomUtils.time();

        // 1、设置请求参数
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
        Map<String, String> map = new HashMap<>(16);
        map.put("out_trade_no", orderId);
        map.put("trade_no", alipayNo);
        map.put("refund_amount", String.valueOf(money));
        map.put("refund_reason", reason);
        map.put("out_request_no", refundId);
        alipayRequest.setBizContent(JsonUtils.objectToJson(map));

        try {
            // 2、请求
            String json = alipayClient.execute(alipayRequest).getBody();

            Map<String, Object> resMap = JsonUtils.jsonToPojo(json, Map.class);
            Map<String, String> responseMap = (Map) resMap.get("alipay_trade_refund_response");

            // 获得返回状态码，具体参考：https://docs.open.alipay.com/common/105806
            String code = responseMap.get("code");
            if (AlipayReturnEnums.AlipayReturn_1000.getCode().equals(code)) {
                // 插入数据库
                orderRefundService.createRefund(refundId, reason, money, responseMap);
                return Result.ok();
            } else {
                String subMsg = responseMap.get("sub_msg");
                log.error("【支付宝退款】错误，错误码：{}，错误信息：{}", code, subMsg);

                // 如果错误信息为 交易状态不合法 ，说明本地状态与服务器的不一致，需要手动同步
                if ("交易状态不合法".equals(subMsg)) {
                    orderInfoService.syncStatus(orderId, alipayNo);
                }

                return Result.error(ResultEnum.ALIPAY_REFUND_ERROR);
            }
        } catch (Exception e) {
            log.error("【支付宝退款】异常，异常原因：{}", e.getMessage());
            e.printStackTrace();
        }
        return Result.error(ResultEnum.ALIPAY_REFUND_ERROR);

        /*
         {
            "alipay_trade_refund_response":{
                "code":"10000",
                "msg":"Success",
                "buyer_logon_id":"uce***@sandbox.com",
                "buyer_user_id":"2088102176077881",
                "fund_change":"Y",
                "gmt_refund_pay":"2018-06-04 20:23:07",
                "out_trade_no":"152810620510594",
                "refund_fee":"2.00",
                "send_back_fee":"0.00",
                "trade_no":"2018060421001004880200500500"
            },
            "sign":"Udr4p57b8oD7Iuov0Gp5j6+CpKX6voO8e0X5myr31KHU45/p3uyRH7UtQx8EV9GV9QDkwI1UePGzQT6xD8kYyFySBkUvAOz2oneXG4VKjY9LDgKn2Wm5WpWQqNfEHj1TkkTwFe5XgysIl9QsCw2tB1WRgm18tZiQjY5vGoATRxF6t96xSDh4dlw5Ylo1bJljNwQgJ5X2fXeVFul9yCCIubwjROIKxMKVMuoUJaM9qxiVyXmJvYykUX4l7ngV72XPO+pSBbj4I8hWHH2GH/ozLAtrYGrbEqoaTrWWqM24Tf2U3DgrApl+jpFrZkiZoSYXQCgROi9ZFkuoUaBRNULUfA=="
        }

        {
            "alipay_trade_refund_response":{
                "code":"40004",
                "msg":"Business Failed",
                "sub_code":"ACQ.REASON_TRADE_REFUND_FEE_ERR",
                "sub_msg":"REASON_TRADE_REFUND_FEE_ERR",
                "out_trade_no":"152810620510594",
                "refund_fee":"0.00",
                "send_back_fee":"0.00"
            },
            "sign":"D6D+dctnKnh5Q4VNNgFgW2wiadjWccIROWYw0y54tvrDrPRCHOpxc2e7xS/+URqstqq+YK7NLl1Tl3Eqne1wmggj0eCw2sT1wygC6nI7lafEtjAuNyp36uLEs+WRH1fi7QZmyPaigrjk3yPYXslDgEYlFpFPqj/f6F5wMZ46tKlGVygfXzjHeHAsDUGwl3TOnyYTDdde98IqU1bcW9U3+3dBp/kvZh4pFzrlR8j6sShhW9C7E52ZY0Z4ZLDvF6BVIoII+owfvKtFZNacxMAKvcnDsz0d2FG2k/fVxAr2yea+5hZWq7u0W6We5mM3k/Q43D4RUAN4RxgvHXQol8rjXw=="
        }
         */
    }

    /**
     * 退款记录查询
     * orderId和alipayNo二选一即可
     * refundId可选，如果不填，则查询该订单的所有退款疾苦
     *
     * @param orderId  订单ID
     * @param alipayNo 支付宝交易号
     * @param refundId 退款请求号
     * @author gsh
     * @since 2018/6/4 22:01
     */
    @Override
    public Result refundQuery(String orderId, String alipayNo, String refundId) {
        OrderInfo orderInfo = orderInfoService.getByIdOrAlipayNo(orderId, alipayNo);
        if (orderInfo == null) {
            throw new CustomException(ResultEnum.ORDER_NOT_EXIST);
        }

        // 这里我直接查询数据库了，也可以请求支付宝，参考文档：https://docs.open.alipay.com/api_1/alipay.trade.fastpay.refund.query/
        List<OrderRefund> refunds = orderRefundService.listRefund(orderInfo.getOrderId(), refundId);

        return Result.ok(null, refunds);
    }


}

