package CourseSchedule_207;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * There are a total of n courses you have to take, labeled from 0 to n - 1.
 *
 * Some courses may have prerequisites,
 * for example to take course 0 you have to first take course 1,
 * which is expressed as a pair: [0,1]
 *
 * Given the total number of courses and a list of prerequisite pairs,
 * is it possible for you to finish all courses?
 *
 * For example:
 *
 * 2, [[1,0]]
 * There are a total of 2 courses to take.
 * To take course 1 you should have finished course 0. So it is possible.
 *
 * 2, [[1,0],[0,1]]
 * There are a total of 2 courses to take.
 * To take course 1 you should have finished course 0,
 * and to take course 0 you should also have finished course 1. So it is impossible.
 *
 * Note:
 * The input prerequisites is a graph represented by a list of edges,
 * not adjacency matrices. Read more about how a graph is represented.
 * You may assume that there are no duplicate edges in the input prerequisites.
 */
public class Solution {
    // use DFS to find cycle
    // notice its an directed graph
    // its a topological sort problem
    public boolean canFinish2(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>(numCourses);
        for(int i=0;i<numCourses;i++) graph.add(new ArrayList<>());
        for(int[] arr:prerequisites) graph.get(arr[1]).add(arr[0]);
        // flags
        // 0 for unvisited
        // 1 for visiting
        // 2 for visited
        int[] visited = new int[numCourses];
        for(int i=0;i<visited.length;i++)
            if(visited[i]==0&&!dfs(i,visited,graph))
                return false;
        return true;
    }

    private boolean dfs(int course,int[] visited,List<List<Integer>> graph){
        if(visited[course]!=0) return visited[course]==2;
        visited[course] = 1;
        List<Integer> l = graph.get(course);
        for(int i:l)
            if(!dfs(i,visited,graph)) // find cycle!!!
                return false;
        visited[course] = 2;
        return true;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[][] g = new int[][]{{1,0},{2,6},{1,7},{5,1},{6,4},{7,0},{0,5}};
        System.out.println(s.canFinish(8,g));
    }

    // BFS
    public boolean canFinish(int numCourses, int[][] prerequisites){
        Queue<Integer> q = new LinkedList<>();
        List<List<Integer>> graph = new ArrayList<>(numCourses);
        int[] indegrees = new int[numCourses];
        for(int i=0;i<numCourses;i++) graph.add(new ArrayList<>());
        for(int[] arr:prerequisites){
            graph.get(arr[1]).add(arr[0]);
            indegrees[arr[0]]++;
        }
        boolean[] visited = new boolean[numCourses];
        for(int i=0;i<numCourses;i++)
            if(!visited[i]&&indegrees[i]==0)
                bfs(i,visited,indegrees,graph,q);
        for(boolean b:visited)
            if(!b)
                return false;
        return true;
    }

    private void bfs(int course,boolean[] visited,int[] indegrees,
                        List<List<Integer>> graph,Queue<Integer> q){
        q.add(course);
        while(q.size()!=0){
            int cur = q.poll();
            visited[cur] = true;
            for(int neighbor:graph.get(cur))
                if(--indegrees[neighbor]==0) q.add(neighbor);
        }
    }
}
