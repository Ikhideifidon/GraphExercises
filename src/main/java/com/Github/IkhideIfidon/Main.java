package com.Github.IkhideIfidon;

import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        one();
        two();
        three();
        four();

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

        System.out.println(Exercises.findCircleNum(isConnected1));
        System.out.println(Exercises.findCircleNum(isConnected2));
    }

    public static void three() {
        int[][] edges = {{0, 5}, {4, 3}, {0, 1}, {9, 12}, {6, 4}, {5, 4},
                {0, 2}, {11, 12}, {9, 10}, {0, 6}, {7, 8}, {9, 11}, {5, 3}, {6, 7}};

        int n = 13;
        System.out.println(Exercises.connectedComponents(edges, n));
    }

    public static void four() {
        int numCourses = 13;

        // No cycle
        int[][] prerequisites = {{0, 1}, {0, 6}, {0, 5}, {2, 0}, {2, 3}, {3, 5}, {5, 4}, {6, 4},
                {6, 9}, {7, 6}, {8, 7}, {9, 10}, {9, 11}, {11, 12}, {9, 12}};

        System.out.println(Exercises.canFinish(numCourses, prerequisites));
    }

}