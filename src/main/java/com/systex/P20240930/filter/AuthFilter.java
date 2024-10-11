package com.systex.P20240930.filter;

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

    @Autowired
    private MemberService memberService;

    @Autowired
    public AuthFilter(MemberService memberService) {
        this.memberService = memberService; // 確保 memberService 被正確注入
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        String requestURI = httpRequest.getRequestURI();

        boolean loggedIn = (session != null && session.getAttribute("user") != null);

        // 定義不需要認證的 URI
        String loginURI = httpRequest.getContextPath() + "/login";
        String ajaxLoginURI = httpRequest.getContextPath() + "/ajaxLogin";
        String registerURI = httpRequest.getContextPath() + "/register";
        String checkRegisterURI = httpRequest.getContextPath() + "/checkRegister";
        boolean isPublicResource = requestURI.equals(loginURI) || requestURI.equals(ajaxLoginURI) 
                || requestURI.equals(registerURI) || requestURI.equals(checkRegisterURI);

        if (!loggedIn && !isPublicResource) {
            // 如果未登入且不是公共資源，則重定向到登入頁面
            httpResponse.sendRedirect(loginURI);
            return;
        }

        // 處理傳統登入的 POST 請求
        if (requestURI.equals(loginURI) && "POST".equalsIgnoreCase(httpRequest.getMethod())) {
            String account = httpRequest.getParameter("account");
            String password = httpRequest.getParameter("password");

            Member member = memberService.login(account, password);
            if (member != null) {
                session.setAttribute("user", member);
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/index"); // 登入成功，重定向到主頁面
                return;
            } else {
                // 登入失敗，將錯誤訊息存入 session
                session.setAttribute("errorMessage", "帳號或密碼錯誤！");
                httpResponse.sendRedirect(loginURI); // 重新導向回登入頁面
                return;
            }
        }

        // AJAX 登入處理
        if (requestURI.equals(ajaxLoginURI) && "POST".equalsIgnoreCase(httpRequest.getMethod())) {
            String account = httpRequest.getParameter("account");
            String password = httpRequest.getParameter("password");

            Member member = memberService.login(account, password);
            if (member != null) {
                session.setAttribute("user", member);
                httpResponse.setStatus(HttpServletResponse.SC_OK); // 設置狀態為 200 OK
                return; // 返回成功
            } else {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 設置狀態為 401 Unauthorized
                httpResponse.setContentType("application/json"); // 設置內容類型為 JSON
                // 返回錯誤訊息的 JSON 格式
                httpResponse.getWriter().write("{\"error\": \"帳號或密碼錯誤！\"}");
                return;
            }
        }

        // 繼續處理請求
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 可以在此進行清理操作
    }
}
