package CloneGraph_133;

/**
 * Clone an undirected graph. Each node in the graph contains a label \
 * and a list of its neighbors.
 *
 * OJ's undirected graph serialization:
 *
 * Nodes are labeled uniquely.
 * We use # as a separator for each node, and ,
 * as a separator for node label and each neighbor of the node.
 *
 * As an example, consider the serialized graph {0,1,2#1,2#2,2}.
 *
 * The graph has a total of three nodes,
 * and therefore contains three parts as separated by #.
 *
 * First node is labeled as 0. Connect node 0 to both nodes 1 and 2.
 * Second node is labeled as 1. Connect node 1 to node 2.
 * Third node is labeled as 2.
 * Connect node 2 to node 2 (itself), thus forming a self-cycle.
 */

import Graph.UndirectedGraphNode;

import java.util.*;

/**
 * Definition for undirected graph.
 * class UndirectedGraphNode {
 *     int label;
 *     List<UndirectedGraphNode> neighbors;
 *     UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<UndirectedGraphNode>(); }
 * };
 */
public class Solution {

    // Second session
    // thought:
    // using a map to map the nodes with their labels..
    // dfs and bfs..
    // notice: be careful with the self cycle ring!
    // create a new node and put it into map, or
    // it may keep "digging" and making new node(it thought there was a new neighbor,
    // but the neighbor was itself actually)
    // and finally stack overflow.
    public static UndirectedGraphNode cloneGraphII(UndirectedGraphNode node){
        return dfs(node, new HashMap<>());
    }

    private static UndirectedGraphNode dfs(UndirectedGraphNode src, Map<Integer, UndirectedGraphNode> map){
        if(src==null) return null;
        UndirectedGraphNode tar = new UndirectedGraphNode(src.label);
        map.put(src.label, tar);
        for(UndirectedGraphNode nei:src.neighbors){
            UndirectedGraphNode newNei = map.get(nei.label);
            if(newNei==null){
                // we need to create the new node
                newNei = dfs(nei, map);
            }
            tar.neighbors.add(newNei);
        }
        return tar;
    }

    // Can we do BFS?
    public static UndirectedGraphNode cloneGraphII2(UndirectedGraphNode node){
        if(node==null) return null;
        Map<Integer, UndirectedGraphNode> map = new HashMap<>();
        UndirectedGraphNode root = new UndirectedGraphNode(node.label);
        map.put(root.label, root);
        Queue<UndirectedGraphNode> q = new LinkedList<>();
        q.add(node);
        q.add(root);
        while(q.size()!=0){
            UndirectedGraphNode src = q.poll(), cur = q.poll();
            for(UndirectedGraphNode n:src.neighbors){
                UndirectedGraphNode tm = map.get(n.label);
                if(tm==null){
                    tm = new UndirectedGraphNode(n.label);
                    map.put(n.label, tm);
                    q.add(n);
                    q.add(tm);
                }
                cur.neighbors.add(tm);
            }
        }
        return root;
    }

    // DFS with memo
    public static UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if(node==null) return null;
        return cloneNode(node,new HashMap<>());
    }

    private static UndirectedGraphNode cloneNode(UndirectedGraphNode src,
                                                 Map<Integer,UndirectedGraphNode> memo){
        UndirectedGraphNode rs = new UndirectedGraphNode(src.label);
        memo.put(src.label,rs);
        for(UndirectedGraphNode node:src.neighbors){
            if(memo.containsKey(node.label)) rs.neighbors.add(memo.get(node.label));
            else{
                UndirectedGraphNode newNode = cloneNode(node,memo);
                rs.neighbors.add(newNode);
            }
        }
        return rs;
    }

    public static void main(String[] args) {
        Integer[] a1 = new Integer[]{0,1,2};
        Integer[] a2 = new Integer[]{1,2};
        Integer[] a3 = new Integer[]{2,2};
        List<Integer[]> list = new ArrayList<>(3);
        list.add(a1);
        list.add(a2);
        list.add(a3);
        UndirectedGraphNode head = new UndirectedGraphNode(list);
        UndirectedGraphNode head2 = cloneGraph(head);
    }
}