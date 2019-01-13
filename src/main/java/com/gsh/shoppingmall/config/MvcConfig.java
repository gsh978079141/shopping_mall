package com.gsh.shoppingmall.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.MultipartConfigElement;
/**
    * @Title: MvcConfig
    * @Package com.gsh.shoppingmall.config
    * @Description: MVC配置类
    * @author gsh
    * @date 2019/1/13 08:32
    */
@Configuration
public class MvcConfig {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单个文件最大
        // KB, MB
        factory.setMaxFileSize("201400KB");
        // 设置上传数据总大小
        factory.setMaxRequestSize("2014000KB");
        return factory.createMultipartConfig();
    }
}
