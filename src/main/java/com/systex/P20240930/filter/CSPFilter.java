package com.systex.P20240930.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CSPFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化過濾器（如有需要）
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // 設置 CSP 標頭，允許 Bootstrap CDN 和 jsDelivr 資源
        httpResponse.setHeader("Content-Security-Policy", 
                "default-src 'self'; " +   
                "script-src 'self' 'unsafe-inline' https://code.jquery.com https://cdn.jsdelivr.net https://stackpath.bootstrapcdn.com; " + // 允許本站腳本、內聯腳本、jQuery、jsDelivr 和 Bootstrap CDN 的腳本
                "style-src 'self' 'unsafe-inline' https://stackpath.bootstrapcdn.com; " +  // 允許內聯樣式和來自 Bootstrap CDN 的樣式
                "img-src 'self' data:; " +  
                "font-src 'self'; " +  
                "frame-ancestors 'none';"); 

        // 繼續處理請求
        chain.doFilter(request, response);
    }


    @Override
    public void destroy() {
        // 清理過濾器（如有需要）
    }
}
