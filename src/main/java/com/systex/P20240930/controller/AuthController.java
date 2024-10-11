package com.systex.P20240930.controller;

import com.systex.P20240930.model.Member;
import com.systex.P20240930.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
public class AuthController {

    private MemberService memberService;

    @Autowired
    public AuthController(MemberService memberService) {
        this.memberService = memberService; // 確保這裡不會是 null
    }
    
//    @GetMapping("/")
//    public String redirectToLogin() {
//        return "redirect:/login"; // 訪問 / 時自動重定向到 /login
//    }

    @GetMapping("/index")
    public String showIndex() {
        return "index"; // Spring MVC 會將其映射到 /WEB-INF/pages/index.jsp
    }
    
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        return "auth/register";
    }

    @PostMapping("/checkRegister")
    public String processRegister(
            @RequestParam("account") String account,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes) {

        LinkedList<String> errors = new LinkedList<>();

        // Validate account and password
        if (account == null || account.isEmpty()) {
            errors.add("請輸入帳號。");
        }
        if (password == null || password.isEmpty()) {
            errors.add("請輸入密碼。");
        }

        // Check for errors
        if (!errors.isEmpty()) {
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/register"; // Redirect back to the registration page with errors
        }

        // Attempt to register a new account
        Member member = new Member();
        member.setAccount(account);
        member.setPassword(password);

        if (memberService.register(member)) {
            redirectAttributes.addFlashAttribute("successMessage", "註冊成功，請登入！");
            return "redirect:/login"; // Successful registration, redirect to login page
        } else {
            errors.add("該帳號已經存在！");
            redirectAttributes.addFlashAttribute("errors", errors); // Add error messages to flash attributes
            return "redirect:/register"; // Redirect back to registration page with errors
        }
    }
    
    @GetMapping("/login")
    public String showLoginForm(Model model, HttpSession session) {
        // 取得登入頁面的錯誤訊息
        String errorMessage = (String) session.getAttribute("errorMessage");
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            session.removeAttribute("errorMessage"); // 使用後刪除
        }

        // 其他邏輯保持不變
        return "auth/login"; // 返回登入視圖
    }
//    @GetMapping("/login")
//    public String showLoginForm(Model model, HttpSession session) {
//        // Check for success message from registration
//        String successMessage = (String) session.getAttribute("successMessage");
//        if (successMessage != null) {
//            model.addAttribute("successMessage", successMessage);
//            session.removeAttribute("successMessage"); // Remove it after using it
//        }
//        
//        // Check for error messages if any
//        String errorMessage = (String) session.getAttribute("errorMessage");
//        if (errorMessage != null) {
//            model.addAttribute("errorMessage", errorMessage);
//            session.removeAttribute("errorMessage"); // Remove it after using it
//        }
//
//        return "auth/login"; // Return the login view
//    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 清除 session
        return "redirect:/login"; // 登出後重定向到登入頁面
    }
    
//    @PostMapping("/ajaxLogin")
//    @ResponseBody // 返回 JSON
//    public ResponseEntity<?> ajaxLogin(@RequestParam("account") String account,
//                                        @RequestParam("password") String password,
//                                        HttpSession session) {
//        Member member = memberService.login(account, password);
//        if (member != null) {
//            session.setAttribute("user", member); // 設定登入用戶到 session
//            return ResponseEntity.ok().build(); // 返回 200 OK
//        } else {
//            List<String> errors = new ArrayList<>();
//            errors.add("帳號或密碼錯誤！"); // 可自定義錯誤訊息
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors); // 返回 401 Unauthorized 和錯誤訊息
//        }
//    }
//    
//    @PostMapping("/login")
//    public String processLogin(HttpServletRequest request,
//                               HttpServletResponse response,
//                               Model model) {
//        String errorMessage = (String) request.getAttribute("errorMessage");
//        if (errorMessage != null) {
//            model.addAttribute("errorMessage", errorMessage);
//            return "auth/login";  // 返回登入頁面並顯示錯誤訊息
//        }
//        return "redirect:/index"; // 登入成功，重定向到首頁
//    }
}
