package com.systex.P20240930.controller;

import com.systex.P20240930.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.LinkedList;

@Controller
public class LotteryController {

    @Autowired
    private LotteryService lotteryService;

    @GetMapping("/lottery")
    public String showLotteryForm(Model model) {
        return "lottery/main";
    }

    @PostMapping("/doLottery")
    public String processLottery(
            @RequestParam("groups") String group, 
            @RequestParam("exclude") String exclude, 
            Model model) {

        LinkedList<String> errorMsgs = new LinkedList<>();
        model.addAttribute("errors", errorMsgs);

        int groups = validateGroups(group, errorMsgs);
        LinkedList<Integer> excludes = validateExcludes(exclude, errorMsgs);

        if (!errorMsgs.isEmpty()) {
            return "lottery/main"; // 返回主頁面，顯示錯誤訊息
        }

        ArrayList<int[]> results = lotteryService.gotNumbers(groups, excludes);
        model.addAttribute("results", results);
        return "lottery/result"; // 返回結果頁面
    }

    private int validateGroups(String group, LinkedList<String> errorMsgs) {
        int groups = 0;
        try {
            groups = Integer.parseInt(group);
            if (groups <= 0) {
                errorMsgs.add("組數必須大於0。");
            }
        } catch (NumberFormatException e) {
            errorMsgs.add("組數必須是一個有效的數字。");
        }
        return groups;
    }

    private LinkedList<Integer> validateExcludes(String exclude, LinkedList<String> errorMsgs) {
        LinkedList<Integer> excludes = new LinkedList<>();
        if (exclude != null && !exclude.isEmpty()) {
            String[] excludeArray = exclude.split(" ");
            for (String num : excludeArray) {
                processExcludeNumber(num, excludes, errorMsgs);
            }
        }
        return excludes;
    }

    private void processExcludeNumber(String num, LinkedList<Integer> excludes, LinkedList<String> errorMsgs) {
        try {
            int excludeNum = Integer.parseInt(num);
            if (isExcludeNumValid(excludeNum)) {
                excludes.add(excludeNum);
            } else {
                errorMsgs.add("排除數字必須在1到50之間：" + num);
            }
        } catch (NumberFormatException e) {
            errorMsgs.add("排除數字格式錯誤：" + num);
        }
    }

    private boolean isExcludeNumValid(int num) {
        return num >= 1 && num <= 50; // 排除數字必須在1到50之間
    }
}
