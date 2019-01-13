package com.gsh.shoppingmall.serviceImpl;

import com.gsh.shoppingmall.entity.Good;
import com.gsh.shoppingmall.dao.GoodMapper;
import com.gsh.shoppingmall.service.GoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author gsh
 * @since 2019-01-02
 */
@Service
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements GoodService {

}
