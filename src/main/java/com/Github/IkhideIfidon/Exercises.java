package com.Github.IkhideIfidon;

public class Exercises {

    // Medium: 1. 200. Number of Islands
    public static int numIslands(char[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int countIslands = 0;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == '1') {
                    dfsOne(grid, i, j);
                    countIslands++;
                }
            }
        }
        return countIslands;
    }

    private static void dfsOne(char[][] grid, int r, int c) {
        // Skip out of bounds
        if (r < 0 | c < 0 | r >= grid.length | c >= grid[0].length)
            return;

        // Skip already visited cells
        if (grid[r][c] == '2')
            return;

        // Skip non-permissible area
        if (grid[r][c] == '0')
            return;

        // Mark the visited
        grid[r][c] = '2';

        int[] dr = {-1, 1, 0, 0};           // Permissible row directions
        int[] dc = {0, 0, 1, -1};           // Permissible column directions
        for (int i = 0; i < 4; i++) {
            int rr = r + dr[i];
            int cc = c + dc[i];
            dfsOne(grid, rr, cc);
        }
    }

    // 547. Number of Provinces
    public static int findCircleNum(int[][] isConnected) {
        int row = isConnected.length;
        boolean[] visited = new boolean[row];
        int numberOfProvinces = 0;
        for (int v = 0; v < row; v++) {
            if (!visited[v]) {
                dfsTwo(isConnected, v, visited);
                numberOfProvinces++;
            }
        }
        return numberOfProvinces;
    }

    private static void dfsTwo(int[][] isConnected, int i, boolean[] visited) {
        visited[i] = true;
        for (int j = 0; j < isConnected[0].length; j++) {
            if (isConnected[i][j] == 1 && !visited[j]) {
                visited[j] = true;
                dfsTwo(isConnected, j, visited);
            }
        }
    }
}
