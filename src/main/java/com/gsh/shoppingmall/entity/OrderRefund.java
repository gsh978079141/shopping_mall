package com.gsh.shoppingmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;

/**
 * <p>
 * 订单退款
 * </p>
 *
 * @author gsh
 * @since 2018-06-04
 */
@Data
@AllArgsConstructor
public class OrderRefund implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 退款号
     */
    @TableId(type = IdType.INPUT)
    private String refundId;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 退款金额
     */
    private float money;
    /**
     * 退款账户
     */
    private String account;
    /**
     * 退款原因
     */
    private String reason;
    /**
     * 退款时间
     */
    private String refundDate;
}
