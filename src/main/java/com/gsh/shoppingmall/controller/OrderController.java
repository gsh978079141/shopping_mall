package com.gsh.shoppingmall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gsh.shoppingmall.entity.ApiResult;
import com.gsh.shoppingmall.entity.OrderLocal;
import com.gsh.shoppingmall.service.OrderLocalService;
import com.gsh.shoppingmall.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * <p>
 * 用户订单表 前端控制器
 * </p>
 *
 * @author gsh
 * @since 2019-01-02
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderLocalService orderLocalService;
    /**
     * 获得订单列表
     * @param order
     * @return
     */
    @RequestMapping("/list")
    public ApiResult list(OrderLocal order){
        return ResultUtil.success(orderLocalService.list(new QueryWrapper(order)));
    }

    /**
     * 修改或者保存订单信息
     * @param orderLocal
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    public ApiResult saveOrUpdate(OrderLocal orderLocal){
        return ResultUtil.success(orderLocalService.saveOrUpdate(orderLocal));
    }

    /**
     * 批量删除订单
     * @param orderIds
     * @return
     */
    @RequestMapping("/delete")
    public ApiResult delete(List orderIds){
        return ResultUtil.success(orderLocalService.removeByIds(orderIds));
    }
}

