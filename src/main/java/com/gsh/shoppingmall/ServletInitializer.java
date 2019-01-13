package com.gsh.shoppingmall;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author gsh
 * @Title: ServletInitializer
 * @Package com.gsh.shoppingmall
 * @Description: 序列化
 * @date 2018/7/14 21:04
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ShoppingMallApplication.class);
    }

}
