package com.gsh.shoppingmall.serviceImpl;

import com.gsh.shoppingmall.entity.Adress;
import com.gsh.shoppingmall.dao.AdressMapper;
import com.gsh.shoppingmall.service.AdressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 收货地址表 服务实现类
 * </p>
 *
 * @author gsh
 * @since 2019-01-02
 */
@Service
public class AdressServiceImpl extends ServiceImpl<AdressMapper, Adress> implements AdressService {

}
