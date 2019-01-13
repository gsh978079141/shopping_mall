package com.gsh.shoppingmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author gsh
 * @Title: ShoppingMallApplication
 * @Package com.gsh.shoppingmall
 * @Description: 启动类
 * @date 2018/7/14 21:04
 */
@SpringBootApplication
@MapperScan("com.gsh.shoppingmall.dao")
public class ShoppingMallApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShoppingMallApplication.class, args
        );
    }
}
