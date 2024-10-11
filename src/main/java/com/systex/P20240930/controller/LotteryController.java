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

        int groups = 0;
        LinkedList<Integer> excludes = new LinkedList<>();

        try {
            groups = Integer.parseInt(group);
            if (groups <= 0) {
                errorMsgs.add("組數必須大於0。");
            }
        } catch (NumberFormatException e) {
            errorMsgs.add("組數必須是一個有效的數字。");
        }

        if (exclude != null && !exclude.isEmpty()) {
            String[] excludeArray = exclude.split(" ");
            for (String num : excludeArray) {
                try {
                    int excludeNum = Integer.parseInt(num);
                    if (excludeNum < 1 || excludeNum > 50) {
                        errorMsgs.add("排除數字必須在1到50之間：" + num);
                    } else {
                        excludes.add(excludeNum);
                    }
                } catch (NumberFormatException e) {
                    errorMsgs.add("排除數字格式錯誤：" + num);
                }
            }
        }

        if (!errorMsgs.isEmpty()) {
            return "lottery/main";
        }

        ArrayList<int[]> results = lotteryService.gotNumbers(groups, excludes);
        model.addAttribute("results", results);
        return "lottery/result";
    }
}
