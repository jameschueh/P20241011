package com.systex.P20240930.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systex.P20240930.model.ApiResponse;
import com.systex.P20240930.model.Member;
import com.systex.P20240930.service.MemberService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;

public class AuthFilter implements Filter {

    private final MemberService memberService;
    private final ObjectMapper objectMapper = new ObjectMapper(); // 用於將物件轉換成 JSON

    @Autowired
    public AuthFilter(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        String requestURI = httpRequest.getRequestURI();

        boolean loggedIn = (session != null && session.getAttribute("user") != null);
        System.out.println("請求的 URI: " + requestURI + ", 登入狀態: " + loggedIn);

        String loginURI = httpRequest.getContextPath() + "/login";
        String ajaxLoginURI = httpRequest.getContextPath() + "/ajaxLogin";
        String registerURI = httpRequest.getContextPath() + "/register";
        String checkRegisterURI = httpRequest.getContextPath() + "/checkRegister";
        boolean isPublicResource = requestURI.equals(loginURI) || requestURI.equals(ajaxLoginURI)
                || requestURI.equals(registerURI) || requestURI.equals(checkRegisterURI);

        if (!loggedIn && !isPublicResource) {
            httpResponse.sendRedirect(loginURI);
            return;
        }

        // 傳統登入處理
        if (requestURI.equals(loginURI) && "POST".equalsIgnoreCase(httpRequest.getMethod())) {
            String account = httpRequest.getParameter("account");
            String password = httpRequest.getParameter("password");
            System.out.println("處理傳統登入，帳號: " + account);

            Member member = memberService.login(account, password);
            if (member != null) {
                session.setAttribute("user", member);
                System.out.println("使用者已登入: " + account);
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/index");
                return;
            } else {
            	System.out.println("登入失敗，帳號或密碼錯誤: " + account); 
                session.setAttribute("errorMessage", "帳號或密碼錯誤！");
                httpResponse.sendRedirect(loginURI);
                return;
            }
        }

        // AJAX 登入處理
        if (requestURI.equals(ajaxLoginURI) && "POST".equalsIgnoreCase(httpRequest.getMethod())) {
            String account = httpRequest.getParameter("account");
            String password = httpRequest.getParameter("password");

            Member member = memberService.login(account, password);
            ApiResponse<Member> apiResponse;

            if (member != null) {
                session.setAttribute("user", member);
                apiResponse = new ApiResponse<>("success", "登入成功", member);
                httpResponse.setStatus(HttpServletResponse.SC_OK);
            } else {
                apiResponse = new ApiResponse<>("fail", "帳號或密碼錯誤", null);
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }

            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write(objectMapper.writeValueAsString(apiResponse)); // 將 ApiResponse 轉換為 JSON 格式
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 可以在此進行清理操作
    }
}
