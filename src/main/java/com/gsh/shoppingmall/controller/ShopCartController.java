package com.gsh.shoppingmall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gsh.shoppingmall.entity.ApiResult;
import com.gsh.shoppingmall.entity.ShopCart;
import com.gsh.shoppingmall.service.ShopCartService;
import com.gsh.shoppingmall.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 购物车表 前端控制器
 * </p>
 *
 * @author gsh
 * @since 2019-01-02
 */
@RestController
@RequestMapping("/shopCart")
public class ShopCartController {
    @Autowired
    ShopCartService shopCartService;

    /**
     * 获得购物车信息列表
     *
     * @param userId
     * @return
     */
    @RequestMapping("/listInfo")
    public ApiResult listInfo(Integer userId) {
        return ResultUtil.success(shopCartService.listInfo(userId));
    }

    /**
     * 修改或者保存购物车信息
     *
     * @param shopCart
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    public ApiResult saveOrUpdate(ShopCart shopCart) {
        ShopCart shopCart1 = null;
        shopCart1 = shopCartService.getOne(new QueryWrapper<>(shopCart));
        if (shopCart1 == null) {
            return ResultUtil.success(shopCartService.save(shopCart));
        } else {
            shopCart1.setNum(shopCart1.getNum() + 1);
            return ResultUtil.success(shopCartService.updateById(shopCart1));
        }
    }

    /**
     * 修改购物车数量
     *
     * @param id
     * @param num +加-减
     * @return
     */
    @RequestMapping("/updateNum")
    public ApiResult updateNum(int id, int num) {
        ShopCart shopCart = shopCartService.getById(id);
        int nowNum = 0;
        if (num < 0) {
            if (shopCart.getNum() - Math.abs(num) > 0) {
                nowNum =shopCart.getNum() - Math.abs(num);
            } else {
                return ResultUtil.error(20003, "商品数量不足!");
            }
        } else {
            nowNum = shopCart.getNum() + num;
        }
        shopCart.setNum(nowNum);
        shopCartService.saveOrUpdate(shopCart);
        return ResultUtil.success(nowNum);
    }

    /**
     * 批量删除购物车
     * @param shopCartIds
     * @return
     */
    @RequestMapping("/delete")
    public ApiResult delete(@RequestParam(required = true, value = "shopCartIds[]")List<Integer> shopCartIds) {
        return ResultUtil.success(shopCartService.removeByIds(shopCartIds));
    }
}   

