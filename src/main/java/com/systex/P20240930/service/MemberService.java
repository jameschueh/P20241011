package com.systex.P20240930.service;

import com.systex.P20240930.model.Member;
import com.systex.P20240930.model.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // 用於加密的工具方法
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("加密演算法出錯", e);
        }
    }

    public boolean register(Member member) {
        if (member.getAccount() == null || member.getAccount().isEmpty()) {
            throw new IllegalArgumentException("帳號不能為空");
        }

        // 確認帳號是否已經存在
        if (memberRepository.findByAccount(member.getAccount()) != null) {
            return false; // 帳號已存在，註冊失敗
        }

        // 加密密碼
        member.setPassword(hashPassword(member.getPassword()));

        // 保存新帳號
        memberRepository.save(member);
        return true; // 註冊成功
    }

    public Member login(String account, String password) {
        Member member = memberRepository.findByAccount(account);
        if (member != null && member.getPassword().equals(hashPassword(password))) {
            return member; // 登入成功
        }	
        return null; // 登入失敗
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
