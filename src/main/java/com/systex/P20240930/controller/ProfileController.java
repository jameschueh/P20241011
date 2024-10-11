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

@Controller
public class ProfileController {

    private final MemberService memberService;

    @Autowired
    public ProfileController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        Member member = (Member) session.getAttribute("user");
        model.addAttribute("member", member);
        return "auth/profile"; // 返回個人資料頁面
    }

    @PostMapping("/updateProfile")
    public String updateProfile(HttpSession session, 
                                @RequestParam("account") String account,
                                @RequestParam("password") String password,
                                RedirectAttributes redirectAttributes) {
        Member member = (Member) session.getAttribute("user");
        member.setAccount(account);
        member.setPassword(password); // 這裡可以增加密碼強度檢查

        memberService.update(member); // 更新會員資料
        session.setAttribute("user", member); // 更新session中的使用者資訊

        redirectAttributes.addFlashAttribute("successMessage", "個人資料更新成功！");
        return "redirect:/profile"; // 更新成功後重定向到個人資料頁面
    }
}
