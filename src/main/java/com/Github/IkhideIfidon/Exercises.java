package com.Github.IkhideIfidon;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

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
    // This is a Connected Components Problem
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

    // Number of Connected Components in an Undirected Graph

    public static int connectedComponents(int[][] edges, int n) {
        boolean[] visited = new boolean[n];
        int count = 0;
        for (int v = 0; v < n; v++) {
            if(!visited[v]) {
                dfsThree(edges, v, visited);
                count++;
            }
        }
        return count;
    }

    private static void dfsThree(int[][] edges, int v, boolean[] visited) {
        visited[v] = true;
        for (int w : neighbors(edges, v)) {
            if (!visited[w])
                dfsThree(edges, w, visited);
        }
    }

    // Another option will be to convert the edgeList into Adjacency List.

    /*
     * List<Integer>[] adjacent = (List<Integer>[]) new LinkedList[n];
        for (int i = 0; i < n; i++)
            adjacent[i] = new LinkedList<>();

        for (int[] edge : edges) {
            adjacent[edge[0]].add(edge[1]);
            adjacent[edge[1]].add(edge[0]);
        }
     */
    private static Iterable<Integer> neighbors(int[][] edges, int v) {
        List<Integer> temp = new LinkedList<>();
        for (int[] array : edges) {
            if (array[0] == v)
                temp.add(array[1]);
            else if (array[1] == v)
                temp.add(array[0]);
        }
        return temp;
    }

    // Medium 207. Course Schedule
    static Deque<Integer> cycle;
    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        final boolean[] visited = new boolean[numCourses];
        final boolean[] onStack = new boolean[numCourses];
        final int[] edgeTo = new int[numCourses];
        @SuppressWarnings("unchecked")
        final List<Integer>[] adjacent = (List<Integer>[]) new LinkedList[numCourses];

        // Initialize the adjacency list
        for (int i = 0; i < numCourses; i++)
            adjacent[i] = new LinkedList<>();

        // Populate the adjacency list
        for (int[] prerequisite : prerequisites)
            adjacent[prerequisite[0]].add(prerequisite[1]);

        for (int v = 0; v < numCourses; v++) {
            if (!visited[v])
                dfsFour(v, visited, onStack, edgeTo, adjacent);
        }
        return !possibleToFinish();
    }

    private static void dfsFour(int v, boolean[] visited, boolean[] onStack, int[] edgeTo, List<Integer>[] adjacent) {
        visited[v] = true;
        onStack[v] = true;

        for (int w : adjacent[v]) {
            if (possibleToFinish())     // If possible to finish is false, that means there is a cycle.
                return;

            else if (!visited[w]) {
                edgeTo[w] = v;
                dfsFour(w, visited, onStack, edgeTo, adjacent);
            }

            else if (onStack[w]) {
                cycle = new LinkedList<>();
                for (int value = v; value != w; value = edgeTo[value])
                    cycle.push(value);
                cycle.push(w);
                cycle.push(v);
            }
        }
        onStack[v] = false;
    }

    private static boolean possibleToFinish() { return cycle != null; }

}
