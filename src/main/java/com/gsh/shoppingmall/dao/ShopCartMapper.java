package com.gsh.shoppingmall.dao;

import com.gsh.shoppingmall.entity.ShopCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsh.shoppingmall.vo.ShopCartResultVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 购物车表 Mapper 接口
 * </p>
 *
 * @author gsh
 * @since 2019-01-02
 */
public interface ShopCartMapper extends BaseMapper<ShopCart> {
    /**
     * 购物车信息详情列表
     * @param userId
     * @return
     */
    List<ShopCartResultVo> listInfo(@Param("userId") Integer userId);

}
