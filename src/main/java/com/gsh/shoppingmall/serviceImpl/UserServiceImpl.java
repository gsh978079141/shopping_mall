package com.gsh.shoppingmall.serviceImpl;

import com.gsh.shoppingmall.entity.User;
import com.gsh.shoppingmall.dao.UserMapper;
import com.gsh.shoppingmall.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author gsh
 * @since 2019-01-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
