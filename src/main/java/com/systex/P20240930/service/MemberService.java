package com.systex.P20240930.service;

import com.systex.P20240930.model.Member;
import com.systex.P20240930.model.MemberRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public boolean register(Member member) {
    	
        if (member.getAccount() == null || member.getAccount().isEmpty()) {
            throw new IllegalArgumentException("帳號不能為空");
        }
        // 確認帳號是否已經存在
        if (memberRepository.findByAccount(member.getAccount()) != null) {
            return false; // 帳號已存在，註冊失敗
        }

        // 保存新帳號
        memberRepository.save(member);
        return true; // 註冊成功
    }

    public Member login(String account, String password) {
        // 查詢帳號，並比較密碼是否正確
        Member member = memberRepository.findByAccount(account);
        if (member != null && member.getPassword().equals(password)) {
            return member; // 登入成功
        }
        return null; // 登入失敗
    }
    
    public boolean validateUser(String account, String password) {
        return "admin".equals(account) && "password".equals(password);
    }
    
    // 更新會員資料
    public boolean update(Member member) {
        Optional<Member> optionalMember = memberRepository.findById(member.getId());
        
        if (optionalMember.isPresent()) {
            memberRepository.save(member); // 更新資料
            return true; // 更新成功
        }
        return false; // 更新失敗，找不到使用者
    }
}
