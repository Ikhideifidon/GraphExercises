package com.Github.IkhideIfidon;

import java.util.*;

public class Backtracking {

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        // Arrays.sort(nums); // not necessary
        backtrack(list, new ArrayList<>(), nums);
        return list;
    }

    private static void backtrack(List<List<Integer>> list, List<Integer> tempList, int [] nums){
        if(tempList.size() == nums.length){
            list.add(new ArrayList<>(tempList));
        } else{
            for (int num : nums) {
                if (tempList.contains(num)) continue; // element already exists, skip
                tempList.add(num);
                backtrack(list, tempList, nums);
                tempList.remove(tempList.size() - 1);
            }
        }
    }

    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        backtrack(list, new ArrayList<>(), nums, 0);
        return list;
    }

    private static void backtrack(List<List<Integer>> list , List<Integer> tempList, int [] nums, int start) {
        if (start == nums.length) {
            list.add(new ArrayList<>(tempList));
            return;
        }

        // Decision to include nums[i]
        tempList.add(nums[start]);
        backtrack(list, tempList, nums, start + 1);
        // Decision to exclude nums[i]
        tempList.remove(tempList.size() - 1);
        backtrack(list, tempList, nums, start + 1);
    }

    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        backtrack(nums, 0, new ArrayList<>(), result);
        return result;
    }

    private static void backtrack1(int[] nums, int start, List<Integer> tempList, List<List<Integer>> result) {
        result.add(new ArrayList<>(tempList));
        for (int i = start; i < nums.length; i++) {
            // Skip duplicates
            if (i > start && nums[i] == nums[i - 1])
                continue;

            // Decision to add nums[i]
            tempList.add(nums[i]);
            backtrack(nums, i + 1, tempList, result);
            // Decision to NOT add nums[i]
            tempList.remove(tempList.size() - 1);
        }
    }

    private static void backtrack(int[] nums, int start, List<Integer> tempList, List<List<Integer>> result) {
        if (start == nums.length) {
            result.add(new ArrayList<>(tempList));
            return;
        }

        // Decision to add nums[i]
        tempList.add(nums[start]);
        backtrack(nums, start + 1, tempList, result);
        // Decision to NOT add nums[i]
        tempList.remove(tempList.size() - 1);

        // Skip duplicates using lookahead.
        while (start + 1 < nums.length && nums[start] == nums[start + 1])
            start++;

        backtrack(nums, start + 1, tempList, result);
    }

    public static List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Map<Integer, Integer> frequency = new HashMap<>();
        for (int num : nums)
            frequency.put(num, frequency.getOrDefault(num, 0) + 1);

        backtrackPermuteUnique(nums, new ArrayList<>(), result, frequency);
        System.out.println(frequency);
        return result;
    }

    private static void backtrackPermuteUnique(int[] nums, List<Integer> tempList, List<List<Integer>> result, Map<Integer, Integer> frequency) {
        // [1, 1, 2] -----> [[1, 1, 2], [1, 2, 1], [2, 1, 1]]
        if (tempList.size() == nums.length) {
            result.add(new ArrayList<>(tempList));
            return;
        }

        for (Integer key : frequency.keySet()) {
            if (frequency.get(key) > 0) {
                // Decision to add nums[i]
                tempList.add(key);
                frequency.put(key, frequency.get(key) - 1);
                backtrackPermuteUnique(nums, tempList, result, frequency);

                // Decision NOT to add nums[i]
                tempList.remove(tempList.size() - 1);
                frequency.put(key, frequency.get(key) + 1);
            }
        }
    }


}
