package com.systex.P20240930.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systex.P20240930.filter.AuthFilter;
import com.systex.P20240930.service.MemberService;

@Configuration
public class FilterConfig {
    
    @Autowired
    private MemberService memberService; // 注入 MemberService

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilter() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthFilter(memberService)); // 將 service 傳入過濾器
        registrationBean.addUrlPatterns("/*"); // 指定過濾器的 URL 模式
        return registrationBean;
    }
}

