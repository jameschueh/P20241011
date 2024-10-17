package com.systex.P20240930.controller;

import com.systex.P20240930.model.Member;
import com.systex.P20240930.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import java.util.LinkedList;

@Controller
public class AuthController {

    private final MemberService memberService;

    @Autowired
    public AuthController(MemberService memberService) {
        this.memberService = memberService; // 確保這裡不會是 null
    }

    @GetMapping("/index")
    public String showIndex() {
        return "index"; // Spring MVC 會將其映射到 /WEB-INF/pages/index.jsp
    }
    
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        return "auth/register";
    }

//    @PostMapping("/checkRegister")
//    public String processRegister(
//            @RequestParam("account") String account,
//            @RequestParam("password") String password,
//            RedirectAttributes redirectAttributes) {
//
//        LinkedList<String> errors = new LinkedList<>();
//
//        // Validate account and password
//        if (account == null || account.isEmpty()) {
//            errors.add("請輸入帳號。");
//        }
//        if (password == null || password.isEmpty()) {
//            errors.add("請輸入密碼。");
//        }
//
//        // Check for errors
//        if (!errors.isEmpty()) {
//            redirectAttributes.addFlashAttribute("errors", errors);
//            return "redirect:/register"; // Redirect back to the registration page with errors
//        }
//
//        // Attempt to register a new account
//        Member member = new Member();
//        member.setAccount(account);
//        member.setPassword(password); // 密碼會在 service 中加密
//
//        if (memberService.register(member)) {
//            redirectAttributes.addFlashAttribute("successMessage", "註冊成功，請登入！");
//            return "redirect:/login"; // Successful registration, redirect to login page
//        } else {
//            errors.add("該帳號已經存在！");
//            redirectAttributes.addFlashAttribute("errors", errors); // Add error messages to flash attributes
//            return "redirect:/register"; // Redirect back to registration page with errors
//        }
//    }
    
    @PostMapping("/checkRegister")
    public String processRegister(
            @RequestParam("account") String account,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes) {

        LinkedList<String> errors = new LinkedList<>();

        // Validate account and password using regex
        if (account == null || account.isEmpty()) {
            errors.add("請輸入帳號。");
        } else if (!account.matches("^[a-zA-Z0-9]{5,20}$")) { // 例：帳號需為 5-20 位字母或數字
            errors.add("帳號格式錯誤。");
        }

        if (password == null || password.isEmpty()) {
            errors.add("請輸入密碼。");
        } else if (!password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$")) { // 例：密碼需至少8位，包含大寫字母、數字和特殊字符
            errors.add("密碼必須至少8位，包含大寫字母、數字和特殊字符。");
        }

        // Check for errors
        if (!errors.isEmpty()) {
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/register"; // Redirect back to the registration page with errors
        }

        // Attempt to register a new account
        Member member = new Member();
        member.setAccount(account);
        member.setPassword(password); // 密碼會在 service 中加密

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

        // 取得註冊成功的訊息
        String successMessage = (String) session.getAttribute("successMessage");
        if (successMessage != null) {
            model.addAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage"); // 使用後刪除
        }

        return "auth/login"; // 返回登入視圖
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 清除 session
        return "redirect:/login"; // 登出後重定向到登入頁面
    }
}
