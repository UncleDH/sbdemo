package com.dh.demo.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc//全面掌管MVC 一些默认设置会消失
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors (InterceptorRegistry registry) {
        //registry.addInterceptor(new LocaleInterceptor()).addPathPatterns("/**").excludePathPatterns("/admin/**");//addPath 指拦截哪些地址 excludePath 指略过哪些地址
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");
        //registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/secure/*");
    }

}
