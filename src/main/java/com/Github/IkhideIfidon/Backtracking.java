package com.Github.IkhideIfidon;

import java.util.*;

public class Backtracking {

    // Time complexity = O(n!)
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        permuteBacktrack(nums, new ArrayList<>(), result);
        return result;
    }

    private static void permuteBacktrack(int[] nums, List<Integer> temp, List<List<Integer>> result) {
        if (temp.size() == nums.length)
            result.add(new ArrayList<>(temp));

        for (int num : nums) {
            if (temp.contains(num))
                continue;

            // Decision to take
            temp.add(num);
            permuteBacktrack(nums, temp, result);
            // Decision NOT to take
            temp.remove(temp.size() - 1);
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

    // Medium: 47. Permutations II
    private static void backtrackPermuteUnique(int[] nums, List<Integer> tempList, List<List<Integer>> result, Map<Integer, Integer> frequency) {
        // [1, 1, 2] -----> [[1, 1, 2], [1, 2, 1], [2, 1, 1]]
        if (tempList.size() == nums.length) {
            result.add(new ArrayList<>(tempList));
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

    // Medium: 39. Combination Sum
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        /*
            candidates = [2,3,5], target = 8
            [[2,2,2,2], [2,3,3], [3,5]]
         */
        List<List<Integer>> result = new ArrayList<>();
        backtrack(candidates, 0, 0, target, new ArrayList<>(), result);
        return result;
    }
    private static void backtrack(int[] candidates, int start, int total, int target, List<Integer> temp, List<List<Integer>> result ) {
        if (total == target) {
            result.add(new ArrayList<>(temp));
            return;
        }

        if (start >= candidates.length || total > target)
            return;

        if (total + candidates[start] > target)
            return;

        // Decision to add candidates[start]
        temp.add(candidates[start]);
        backtrack(candidates, start, total + candidates[start], target, temp, result);

        // Decision NOT to add candidates[start]
        temp.remove(temp.size() - 1);
        backtrack(candidates, start + 1, total, target, temp, result);
    }

    public static String longestPalindrome(String s) {
        if (s == null || s.length() == 0)
            return "";
        int n = s.length();
        if (n <= 2) {
            return n == 1 ? s : (s.charAt(0) == s.charAt(1) ? s : s.substring(0, 1));
        }

        String w = new StringBuilder(s).reverse().toString();
        int[][] dp = new int[n][n];

        // Populate the first row of dp
        for (int j = 0; j < n; j++) {
            if (w.charAt(0) == s.charAt(j))
                dp[0][j] = 1;
        }

        // Populate the first column of dp
        for (int i = 0; i < n; i++) {
            if (w.charAt(i) == s.charAt(0))
                dp[i][0] = 1;
        }

        int maxLength = 0;
        int m = 0;
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                if (w.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    if (dp[i][j] > maxLength) {
                        maxLength = dp[i][j];
                        m = j;
                    }
                } else
                        dp[i][j] = 0;
            }
        }
        System.out.println(Arrays.deepToString(dp));

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maxLength; i++)
            sb.append(s.charAt(m--));

        return sb.toString();
    }

    public static List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        combinationBacktrack(k, n, 1, 0, new ArrayList<>(), result);
        return result;
    }

    private static void combinationBacktrack(int k, int n, int start, int total, List<Integer> temp, List<List<Integer>> result) {
        if (temp.size() == k && total == n) {
            result.add(new ArrayList<>(temp));
            return;
        }

        if (temp.size() == k || total > n)
            return;

        if (total  + start > n)
            return;

        for (int num = start; num <= n; num++) {
            // Decision to add
            temp.add(num);
            combinationBacktrack(k, n, num + 1, total + num, temp, result);

            // Decision to remove
            temp.remove(temp.size() - 1);
        }
    }

    // Combination Sum II
    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        backtrackCombinationSum2(candidates, target, 0, 0, new ArrayList<>(), result);
        return result;
    }

    private static void backtrackCombinationSum2(int[] candidates, int target, int total, int start, List<Integer> temp, List<List<Integer>> result) {
        if (total == target) {
            result.add(new ArrayList<>(temp));
            return;
        }

        if (total > target)
            return;

        if (total + candidates[start] > target)
            return;

        for (int i = start; i < candidates.length; i++) {
            if(i > start && candidates[i] == candidates[i-1])               // skip duplicates
                continue;
            temp.add(candidates[i]);
            backtrackCombinationSum2(candidates, target, total + candidates[i], i + 1, temp, result);
            temp.remove(temp.size() - 1);
        }
    }


}
