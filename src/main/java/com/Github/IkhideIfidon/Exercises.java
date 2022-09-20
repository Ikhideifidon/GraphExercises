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
        if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length)
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
    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        final boolean[] visited = new boolean[numCourses];
        final boolean[] onStack = new boolean[numCourses];
        @SuppressWarnings("unchecked")
        final List<Integer>[] adjacent = (List<Integer>[]) new LinkedList[numCourses];

        // Initialize the adjacency list
        for (int i = 0; i < numCourses; i++)
            adjacent[i] = new LinkedList<>();

        // Populate the adjacency list
        for (int[] prerequisite : prerequisites)
            adjacent[prerequisite[0]].add(prerequisite[1]);

        for (int v = 0; v < numCourses; v++) {
            if (!visited[v]) {
                boolean result = dfsFour(v, visited, onStack, adjacent);
                if (result)
                    return false;
            }
        }
        return true;
    }

    private static boolean dfsFour(int v, boolean[] visited, boolean[] onStack, List<Integer>[] adjacent) {
        visited[v] = true;
        onStack[v] = true;

        for (int w : adjacent[v]) {
            if (!visited[w])
                dfsFour(w, visited, onStack, adjacent);

            // If the vertex w is still on stack, then there must be a cycle. Therefore, course scheduling will not finish
            if (onStack[w])
                return true;
        }
        onStack[v] = false;
        return false;
    }

    // Medium 210. Course Schedule II
    public static int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] preorder = new int[numCourses];
        final boolean[] visited = new boolean[numCourses];
        final boolean[] onStack = new boolean[numCourses];
        final Deque<Integer> order = new LinkedList<>();
        @SuppressWarnings("unchecked")
        final List<Integer>[] adjacent = (List<Integer>[]) new LinkedList[numCourses];

        // Initialize the adjacency list
        for (int i = 0; i < numCourses; i++)
            adjacent[i] = new LinkedList<>();

        // Populate the adjacency list
        for (int[] prerequisite : prerequisites)
            adjacent[prerequisite[0]].add(prerequisite[1]);

        for (int v = 0; v < numCourses; v++) {
            if (!visited[v]) {
                boolean result = dfs(v, visited, onStack, adjacent, order);
                if (result)
                    return new int[0];
            }
        }
        for (int i = 0; i < numCourses; i++)
            //noinspection ConstantConditions
            preorder[i] = order.poll();
        return preorder;
    }

    private static boolean dfs(int v, boolean[] visited, boolean[] onStack, List<Integer>[] adjacent, Deque<Integer> order) {
        visited[v] = true;
        onStack[v] = true;
        order.offer(v);

        for (int w : adjacent[v]) {
            if (!visited[w])
                dfs(w, visited, onStack, adjacent, order);

            // If the vertex w is still on stack, then there must be a cycle. Therefore, course scheduling will not finish
            if (onStack[w])
                return true;
        }
        onStack[v] = false;
        return false;
    }

    // Medium 994. Rotting Oranges
    public static int orangesRotting(int[][] grid) {

        int row = grid.length;
        int col = grid[0].length;
        Deque<int[]> queue = new LinkedList<>();
        int countFresh = 0;

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (grid[r][c] == 2)
                    queue.offer(new int[]{r, c});

                else if (grid[r][c] == 1)
                    countFresh++;
            }
        }

        if (countFresh == 0)
            return 0;

        // Permissible directions for both r and c.
        int[] dr = {1, -1, 0, 0};
        int[] dc = {0, 0, 1, -1};
        int minute = 0;

        while (countFresh > 0 && !queue.isEmpty()) {
            minute++;

            int size = queue.size();
            for (int x = 0; x < size; x++) {
                int[] pairs = queue.poll();
                for (int i = 0; i < dr.length; i++) {
                    assert pairs != null;
                    int rr = pairs[0] + dr[i];
                    int cc = pairs[1] + dc[i];

                    // Check Out of Bounds
                    if (rr < 0 || cc < 0 || rr >= grid.length || cc >= grid[0].length)
                        continue;

                    // Skip non-permissible value
                    if (grid[rr][cc] == 0)
                        continue;

                    // Skip already rotten orange
                    if (grid[rr][cc] == 2)
                        continue;

                    // Mark the visited cells
                    grid[rr][cc] = 2;

                    // Enqueue the new rr and cc as a list
                    queue.offer(new int[]{rr, cc});
                    countFresh--;
                }
            }
        }
        return countFresh == 0 ? minute : -1;
    }

    // Medium: 417. Pacific Atlantic Water Flow

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        int row = heights.length;
        int col = heights[0].length;
        int[][] pacific = new int[row][col];
        int[][] atlantic = new int[row][col];
        int previousHeight = Integer.MIN_VALUE;
        List<List<Integer>> result = new LinkedList<>();

        // Top row of the Pacific ocean to the bottom row of the Atlantic ocean.
        for (int column = 0; column < heights[0].length; column++) {
            dfs(heights, 0, column, previousHeight, pacific);
            dfs(heights, heights.length - 1, column, previousHeight, atlantic);
        }

        // left column of the Pacific ocean to the right  column of the Atlantic ocean.
        for (int rows = 0; rows < heights.length; rows++) {
            dfs(heights, rows, 0, previousHeight, pacific);
            dfs(heights, rows, heights[0].length - 1, previousHeight, atlantic);
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (pacific[i][j] == 1 && atlantic[i][j] == 1) {
                    LinkedList<Integer> temp = new LinkedList<>();
                    temp.add(i, j);
                    result.add(temp);
                }
            }
        }
        return  result;
    }

    private void dfs(int[][] heights, int row, int col, int previousHeight, int[][] visited) {
        // Permissible Directions.
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, 1, -1};

        for (int i = 0; i < dr.length; i++) {
            int rr = row + dr[i];
            int cc = col + dc[i];

            // Check boundary conditions
            if (rr < 0 || cc < 0 || rr >= heights.length || cc >= heights[0].length)
                continue;

            // Skip already visited
            if (visited[rr][cc] == 1)
                continue;

            // Check if this cell can be traversed
            if (heights[rr][cc] < previousHeight)
                continue;

            // Mark visited cell
            visited[rr][cc] = 1;

            dfs(heights, rr, cc, previousHeight, visited);
        }
    }

}
