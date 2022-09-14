package com.Github.IkhideIfidon;

public class Main {
    public static void main(String[] args) {
        one();
        two();
    }

    private static void one() {
        char[][] grid1 =
                {
                        {'1', '1', '1', '1', '0'},
                        {'1', '1', '0', '1', '0'},
                        {'1', '1', '0', '0', '0'},
                        {'0', '0', '0', '0', '0'}
                };

        char[][] grid2 =
                {
                        {'1', '1', '0', '0', '0'},
                        {'1', '1', '0', '0', '0'},
                        {'0', '0', '1', '0', '0'},
                        {'0', '0', '0', '1', '1'}
                };

        System.out.println(Exercises.numIslands(grid2));
        System.out.println(Exercises.numIslands(grid1));
    }

    private static void two() {
        int[][] isConnected1 =
                {
                        {1, 1, 0},
                        {1, 1, 0},
                        {0, 0, 1}
                };

        int[][] isConnected2 =
                {
                        {1, 0, 0, 1},
                        {0, 1, 1, 0},
                        {0, 1, 1, 1},
                        {1, 0, 1, 1}
                };

//        System.out.println(Exercises.findCircleNum(isConnected1));
        System.out.println(Exercises.findCircleNum(isConnected2));
    }
}