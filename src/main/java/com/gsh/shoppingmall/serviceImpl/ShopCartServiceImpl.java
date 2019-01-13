package com.gsh.shoppingmall.serviceImpl;

import com.gsh.shoppingmall.entity.ShopCart;
import com.gsh.shoppingmall.dao.ShopCartMapper;
import com.gsh.shoppingmall.service.ShopCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsh.shoppingmall.vo.ShopCartResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 * @author gsh
 * @since 2019-01-02
 */
@Service
public class ShopCartServiceImpl extends ServiceImpl<ShopCartMapper, ShopCart> implements ShopCartService {

    @Autowired
    ShopCartMapper shopCartMapper;

    @Override
    public List<ShopCartResultVo> listInfo(Integer userId) {
        return shopCartMapper.listInfo(userId);
    }

}
