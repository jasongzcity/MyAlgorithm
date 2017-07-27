package CourseScheduleII_210;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * There are a total of n courses you have to take, labeled from 0 to n - 1.
 *
 * Some courses may have prerequisites, for example to take course 0
 * you have to first take course 1, which is expressed as a pair: [0,1]
 *
 * Given the total number of courses and a list of prerequisite pairs,
 * return the ordering of courses you should take to finish all courses.
 *
 * There may be multiple correct orders, you just need to return one of them.
 * If it is impossible to finish all courses, return an empty array.
 *
 * For example:
 * 2, [[1,0]]
 * There are a total of 2 courses to take.
 * To take course 1 you should have finished course 0.
 * So the correct course order is [0,1]
 *
 * 4, [[1,0],[2,0],[3,1],[3,2]]
 * There are a total of 4 courses to take.
 * To take course 3 you should have finished both courses 1 and 2.
 * Both courses 1 and 2 should be taken after you finished course 0.
 * So one correct course order is [0,1,2,3]. Another correct ordering is[0,2,1,3].
 *
 * Note:
 * The input prerequisites is a graph represented by a list of edges,
 * not adjacency matrices. Read more about how a graph is represented.
 * You may assume that there are no duplicate edges in the input prerequisites.
 */
public class Solution {
    // do it by DFS and BFS
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        if(numCourses<=1) return new int[numCourses];
        List<List<Integer>> graph = new ArrayList<>(numCourses);
        int[] indegrees = new int[numCourses],rs = new int[numCourses];
        boolean[] visited = new boolean[numCourses];
        for(int i=0;i<numCourses;i++) graph.add(new ArrayList<>());
        for(int[] a:prerequisites){
            graph.get(a[1]).add(a[0]);
            indegrees[a[0]]++;
        }
        int begin = 0;
        for(int i=0;i<numCourses;i++)
            if(indegrees[i]==0&&!visited[i])
                begin = dfs(i,begin,rs,indegrees,visited,graph);
        if(begin!=numCourses) return new int[0];
        else return rs;
    }

    private int dfs(int course,int begin,int[] rs,int[] indegrees,boolean[] visited,
                    List<List<Integer>> graph){
        rs[begin++] = course;
        visited[course] = true;
        for(int i:graph.get(course))
            if(--indegrees[i]==0)
                begin = dfs(i,begin,rs,indegrees,visited,graph);
        return begin;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[][] a = new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}};
        int[][] a2 = new int[][]{{0, 1}};
        int[][] g = new int[][]{{1, 0}, {2, 6}, {1, 7}, {5, 1}, {6, 4}, {7, 0}, {0, 5}};

        System.out.println(Arrays.toString(s.findOrder(4, a)));
        System.out.println(Arrays.toString(s.findOrder(2, a2)));
        System.out.println(Arrays.toString(s.findOrder(8, g)));
    }

    // better dfs solution
    int index;

    public int[] findOrder2(int numCourses, int[][] prerequisites) {
        index = numCourses - 1;
        List[] neighbors = new List[numCourses];
        for (int[] edge : prerequisites) {
            if (neighbors[edge[1]] == null) {
                neighbors[edge[1]] = new ArrayList<>();
            }
            neighbors[edge[1]].add(edge[0]);
        }
        int[] vis = new int[numCourses];
        int[] path = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            if (!dfs(i, neighbors, vis, path)) return new int[0];
        }
        return path;
    }

    public boolean dfs(int cur, List[] nbs, int[] vis, int[] path) {
        if (vis[cur] == 0) {
            vis[cur] = 1;
            List<Integer> nb = nbs[cur];
            if (nb != null) {
                for (int i : nb) {
                    if (!dfs(i, nbs, vis, path))    return false;
                }
            }
            path[index--] = cur;
            vis[cur] = 2;
        }
        return vis[cur] != 1;
    }

    // BFS is the same as the (#207) "Course Schedule" BFS solution
}
