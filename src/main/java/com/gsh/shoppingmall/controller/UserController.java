package com.gsh.shoppingmall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gsh.shoppingmall.entity.ApiResult;
import com.gsh.shoppingmall.entity.User;
import com.gsh.shoppingmall.service.UserService;
import com.gsh.shoppingmall.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author gsh
 * @since 2019-01-02
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    /**
     * 用户登录
     * @param user
     * @return
     */
    @RequestMapping("/login")
    public ApiResult login(User user,HttpServletRequest request){
        User userInfo = userService.getOne(new QueryWrapper<>(user));
        if (userInfo != null){
            request.getSession().setAttribute(userInfo.getUserName(),userInfo);
            return ResultUtil.success(userInfo);
        }else{
            return ResultUtil.error(2001,"用户名或者密码错误");
        }
    }

    /**
     * 用户退出
     * @return
     */
    @RequestMapping("/logout")
    public ApiResult logout(HttpServletRequest request){
        request.getSession().invalidate();
        return ResultUtil.success("");
    }
    /**
     * 用户注册
     * @return
     */
    @RequestMapping("/register")
    public ApiResult register(User user){
        if (userService.getOne(new QueryWrapper<>(user)) != null){
            return ResultUtil.error(2002,"用户名已存在");
        }else {
            saveOrUpdate(user);
            return ResultUtil.success("");
        }
    }
    /**
     * 获得用户列表
     * @param user
     * @return
     */
    @RequestMapping("/list")
    public ApiResult list(User user){
        return ResultUtil.success(userService.list(new QueryWrapper(user)));
    }


    /**
     * 修改或者保存用户信息
     * @param user
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    public ApiResult saveOrUpdate(User user){
        return ResultUtil.success(userService.saveOrUpdate(user));
    }

    /**
     * 批量删除用户
     * @param userIds
     * @return
     */
    @RequestMapping("/delete")
    public ApiResult delete(List userIds){
        return ResultUtil.success(userService.removeByIds(userIds));
    }
}

