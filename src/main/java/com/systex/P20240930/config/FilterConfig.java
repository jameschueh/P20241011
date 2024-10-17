package com.systex.P20240930.config;

import com.systex.P20240930.filter.AuthFilter;
import com.systex.P20240930.filter.CSPFilter;
import com.systex.P20240930.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Autowired
    private MemberService memberService;

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilter() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthFilter(memberService));
        registrationBean.addUrlPatterns("/*"); // 過濾所有路徑
        return registrationBean;
    }

    // 註冊 CSP 過濾器
    @Bean
    public FilterRegistrationBean<CSPFilter> cspFilter() {
        FilterRegistrationBean<CSPFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CSPFilter());
        registrationBean.addUrlPatterns("/*"); // 套用於所有頁面
        return registrationBean;
    }
}
