package com.systex.P20240930.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class LotteryService {

    public ArrayList<int[]> gotNumbers(int groups, LinkedList<Integer> excludes) {
        ArrayList<int[]> results = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < groups; i++) {
            int[] result = generateSingleGroup(random, excludes);
            results.add(result);
        }
        return results;
    }

    private int[] generateSingleGroup(Random random, LinkedList<Integer> excludes) {
        List<Integer> availableNumbers = getAvailableNumbers(excludes);

        if (availableNumbers.size() < 6) {
            throw new IllegalArgumentException("可用號碼不足，無法生成組別！");
        }

        return drawNumbers(random, availableNumbers);
    }

    private List<Integer> getAvailableNumbers(LinkedList<Integer> excludes) {
        List<Integer> numbers = new ArrayList<>();
        for (int j = 1; j <= 50; j++) {
            if (!excludes.contains(j)) {
                numbers.add(j);
            }
        }
        return numbers;
    }

    private int[] drawNumbers(Random random, List<Integer> availableNumbers) {
        int[] result = new int[6];

        for (int j = 0; j < 6; j++) {
            int index = random.nextInt(availableNumbers.size());
            result[j] = availableNumbers.remove(index);
        }
        return result;
    }
}
