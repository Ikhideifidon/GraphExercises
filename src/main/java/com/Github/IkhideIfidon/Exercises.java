package com.Github.IkhideIfidon;

import java.util.*;

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

    public static List<List<Integer>> pacificAtlantic(int[][] heights) {
        int row = heights.length;
        int col = heights[0].length;
        int[][] pacific = new int[row][col];        // Visited Pacific Ocean
        int[][] atlantic = new int[row][col];       // Visited Atlantic Ocean
        int previousHeight = Integer.MIN_VALUE;
        List<List<Integer>> result = new LinkedList<>();

        // The top of the island borders the Pacific ocean and the bottom of the island borders the Atlantic ocean
        // Check the cells that are able to reach the pacific ocean from the atlantic ocean and vice versa.
        for (int column = 0; column < heights[0].length; column++) {
            dfs(heights, 0, column, previousHeight, pacific);
            dfs(heights, heights.length - 1, column, previousHeight, atlantic);
        }

        // The left of the island borders the Pacific Ocean and the right of the island borders the Atlantic ocean.
        // Check the cells that are able to reach the atlantic ocean from the pacific ocean and vice versa.
        for (int rows = 0; rows < heights.length; rows++) {
            dfs(heights, rows,0, previousHeight, pacific);
            dfs(heights, rows, heights[0].length - 1, previousHeight, atlantic);
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (pacific[i][j] == 1 && atlantic[i][j] == 1)
                    result.add(List.of(i, j));
            }
        }
        return  result;
    }

    private static void dfs(int[][] heights, int r, int c, int previousHeight, int[][] visited) {
        // Skip Boundary conditions
        if (r < 0 || c < 0 || r >= heights.length || c >= heights[0].length)
            return;

        // Skip already visited
        if (visited[r][c] == 1)
            return;

        // Skip cells less than the previous height.
        if (heights[r][c] < previousHeight)
            return;

        // Mark visited cell
        visited[r][c] = 1;

        // Permissible Directions.
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, 1, -1};

        for (int i = 0; i < dr.length; i++) {
            int rr = r + dr[i];
            int cc = c + dc[i];
            dfs(heights, rr, cc, heights[r][c], visited);
        }
    }

    // Medium.2316. Count Unreachable Pairs of Nodes in an Undirected Graph

    public static long countPairs(int n, int[][] edges) {
        // This is a connected components related question.

        boolean[] marked = new boolean[n];
        @SuppressWarnings("unchecked")
        List<Integer>[] adjacent = (List<Integer>[]) new LinkedList[n];

        // Initialize the adjacency list
        for (int i = 0; i < n; i++)
            adjacent[i] = new LinkedList<>();

        // Populate the adjacent list
        for (int[] edge : edges) {
            adjacent[edge[0]].add(edge[1]);
            adjacent[edge[1]].add(edge[0]);
        }

        int sum = n;
        int sizeOfConnectedComponents;
        long result = 0;
        for (int v = 0; v < n; v++) {
            if (!marked[v]) {
                sizeOfConnectedComponents = dfs(adjacent, v, marked, new int[1]);
                sum = sum - sizeOfConnectedComponents;
                result += (long) sum * sizeOfConnectedComponents;
            }
        }
        return result;

    }

    private static int  dfs(List<Integer>[] adjacent, int v, boolean[] marked, int[] count) {
        marked[v] = true;
        count[0]++;
        for (int w : adjacent[v]) {
            if (!marked[w])
                dfs(adjacent, w, marked, count);
        }
        return count[0];
    }

    // Medium 130. Surrounded Regions

    public static void solve(char[][] board) {
        // After careful analysis, we can conclude that if there is any 'O' at the border, it
        // is un-flippable and if that 'O' is connected to another 'O' such 'O' is also
        // un-flippable.

        // The basic idea is to find all 'O' connected to the 'O' at the border
        // (if any).

        int m = board.length;
        int n = board[0].length;
        /*

        [X  O  X  X  X]                   [X  X  X  X  X]
        [X  O  O  O  X]                   [X  O  O  O  X]
        [X  O  X  X  X]                   [X  O  X  X  X]
        [X  X  X  X  X]                   [X  X  X  X  X]

         */

        // 1. Move over the first and the last column.
        for (int row = 0; row < m; row++) {
            if (board[row][0] == 'O')
                dfs(board, row, 0);
            if (board[row][n - 1] == 'O')
                dfs(board, row, n - 1);
        }

        // 2. Move over the first and last row
        for (int column = 0; column < n; column++) {
            if (board[0][column] == 'O')
                dfs(board, 0, column);
            if (board[m - 1][column] == 'O')
                dfs(board, m - 1, column);

        }

        // 3. For every position in the board, check if 'O' is marked from the operations of 2 and 3 above.
        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                if (board[row][col] == 'O')
                    board[row][col] = 'X';

                if (board[row][col] == '#')
                    board[row][col] = 'O';
            }
        }
     }

    private static void dfs(char[][] board, int i, int j) {
        // Out of bounds
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length)
            return;

        // Visited or Forbidden position
        if (board[i][j] != 'O')
            return;

        // Visited board position
        board[i][j] = '#';

        // Permissible Directions
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, 1, -1};

        for (int x = 0; x < dr.length; x++) {
            int rr = i + dr[x];
            int cc = j + dc[x];

            dfs(board, rr, cc);
        }
    }

    // Walls and Gates
    public static void wallsAndGates(int[][] rooms) {

        /*
            [INF -1   0  INF]
            [INF INF INF  -1]
            [INF -1  INF  -1]
            [0   -1  INF INF]
         */
        // -1 --> A Wall or an Obstacle
        //  0 -----> A gate
        // INF ----. Integer.MAX_VALUE ---> Empty room;

        int row = rooms.length;
        int col = rooms[0].length;
        boolean[][] visited = new boolean[row][col];

        Deque<int[]> queue = new LinkedList<>();

        // Iterate through the rooms and enqueue any gate if present
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (rooms[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                    visited[i][j] = true;
                }
            }
        }

        int distance = 0;

        // Permissible Directions
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, 1, -1};

        while (!queue.isEmpty()) {
            int size = queue.size();

            // Iterate through each element of the queue.
            for (int i = 0; i < size; i++) {
                int[] pairs = queue.poll();
                assert pairs != null;
                rooms[pairs[0]][pairs[1]] = distance;
                for (int p = 0; p < dr.length; p++) {
                    int rr = pairs[0] + dr[p];
                    int cc = pairs[1] + dc[p];

                    // Check Out of Bounds
                    if (rr < 0 || cc < 0 || rr >= row || cc >= col)
                        continue;

                    // Skip already visited
                    if (visited[rr][cc])
                        continue;

                    // Skip a wall or obstacle
                    if (rooms[rr][cc] == -1)
                        continue;

                    // Mark visited
                    visited[rr][cc] = true;

                    // Enqueue the new position.
                    queue.offer(new int[] {rr, cc});
                }
            }
            distance++;
        }
        System.out.println(Arrays.deepToString(rooms));
    }

    static LinkedList<String> route = new LinkedList<>();
    static Map<String, PriorityQueue<String>> adjacent = new HashMap<>();
    public static List<String> findItinerary(String[][] tickets) {
        
        if (tickets == null || tickets.length == 0) {
                return route;
        }

        for (String[] ticket : tickets) {
            // Populate the adjacent map
            adjacent.computeIfAbsent(ticket[0], k -> new PriorityQueue<>()).add(ticket[1]);
        }

        dfs("JFK");
        return route;
   }

   private static void dfs(String airport) {
        while (adjacent.containsKey(airport) && !adjacent.get(airport).isEmpty())
            dfs(adjacent.get(airport).poll());
        route.offerFirst(airport);
   }
}
