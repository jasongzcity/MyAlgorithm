package MinimumHeightTrees_310;

import java.util.*;

/**
 * For a undirected graph with tree characteristics,
 * we can choose any node as the root.
 * The result graph is then a rooted tree.
 * Among all possible rooted trees, those with minimum height are
 * called minimum height trees (MHTs).
 * Given such a graph, write a function to find all the
 * MHTs and return a list of their root labels.
 *
 * Format
 * The graph contains n nodes which are labeled from 0 to n - 1.
 * You will be given the number n and a list of undirected edges
 * (each edge is a pair of labels).
 *
 * You can assume that no duplicate edges will appear in edges.
 * Since all edges are undirected, [0, 1] is the same as [1, 0]
 * and thus will not appear together in edges.
 *
 * Example 1:
 * Given n = 4, edges = [[1, 0], [1, 2], [1, 3]]
 *
 *     0
 *     |
 *     1
 *    / \
 *   2   3
 * return [1]
 *
 * Example 2:
 * Given n = 6, edges = [[0, 3], [1, 3], [2, 3], [4, 3], [5, 4]]
 *       0  1  2
 *        \ | /
 *          3
 *          |
 *          4
 *          |
 *          5
 * return [3, 4]
 *
 * Note:
 *
 * (1) According to the definition of tree on Wikipedia:
 * “a tree is an undirected graph in which any two vertices are
 * connected by exactly one path. In other words,
 * any connected graph without simple cycles is a tree.”
 *
 * (2) The height of a rooted tree is the number of edges on the
 * longest downward path between the root and a leaf.
 */
public class Solution {
    // great explanations on leetcode:
    // https://discuss.leetcode.com/topic/30572/share-some-thoughts
    // faster solution:
    // https://discuss.leetcode.com/topic/31478/
    // share-my-bfs-java-code-using-degree-with-explanation-which-beats-more-than-95
    // n vertices, n-1 edges must be a tree(no cycle).
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if(n==1) return Collections.singletonList(0);
        // construct graph and count degree of each vertex
        int[] degrees = new int[n];
        List<List<Integer>> graph = new ArrayList<>(n);
        for(int i=0;i<n;i++) graph.add(new ArrayList<>());
        for(int i=0;i<edges.length;i++){
            graph.get(edges[i][0]).add(edges[i][1]);
            graph.get(edges[i][1]).add(edges[i][0]);
            degrees[edges[i][0]]++;
            degrees[edges[i][1]]++;
        }

        // start BFS with leaves
        LinkedList<Integer> q = new LinkedList<>();
        for(int i=0;i<n;i++)
            if(degrees[i]==1)
                q.add(i);

        // The result must less than or equal to 2
        while(n>2){
            int size = q.size();
            n-=size;
            for(int i=0;i<size;i++){
                int cur = q.poll();
                for(int neighbor:graph.get(cur)){
                    // check all neighbor
                    if(--degrees[neighbor]==1) q.add(neighbor); // next leaf
                }
            }
        }
        return q;
    }
}
