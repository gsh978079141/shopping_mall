package com.gsh.shoppingmall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gsh.shoppingmall.entity.Adress;
import com.gsh.shoppingmall.entity.ApiResult;
import com.gsh.shoppingmall.service.AdressService;
import com.gsh.shoppingmall.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 收货地址表 前端控制器
 * </p>
 *
 * @author gsh
 * @since 2019-01-02
 */
@RestController
@RequestMapping("/adress")
public class AdressController {
    @Autowired
    AdressService adressService;

    /**
     * 获得收货地址列表
     * @param adress
     * @return
     */
    @RequestMapping("/list")
    public ApiResult list(Adress adress){
        return ResultUtil.success(adressService.list(new QueryWrapper(adress)));
    }

    /**
     * 修改或者保存收货地址信息
     * @param adressInfo
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    public ApiResult saveOrUpdate(Adress adressInfo){
        return ResultUtil.success(adressService.saveOrUpdate(adressInfo));
    }

    /**
     * 批量删除收货地址
     * @param adressIds
     * @return
     */
    @RequestMapping("/delete")
    public ApiResult delete(@RequestParam(required = true, value = "adressIds[]") List<Integer> adressIds){
        return ResultUtil.success(adressService.removeByIds(adressIds));
    }
    
    
}

