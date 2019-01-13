package com.gsh.shoppingmall.service;

import com.gsh.shoppingmall.entity.ShopCart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gsh.shoppingmall.vo.ShopCartResultVo;

import java.util.List;

/**
 * <p>
 * 购物车表 服务类
 * </p>
 *
 * @author gsh
 * @since 2019-01-02
 */
public interface ShopCartService extends IService<ShopCart> {
    List<ShopCartResultVo> listInfo(Integer userId);
}
