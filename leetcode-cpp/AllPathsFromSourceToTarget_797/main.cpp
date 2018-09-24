//
//  main.cpp
//  AllPathsFromSourceToTarget_797
//
//  Created by Wenzhe Lu on 2018/3/11.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//Given a directed, acyclic graph of N nodes.
//Find all possible paths from node 0 to node N-1, and return them in any order.
//
//The graph is given as follows:  the nodes are 0, 1, ..., graph.length - 1.
//graph[i] is a list of all nodes j for which the edge (i, j) exists.
//
//Example:
//Input: [[1,2], [3], [3], []]
//Output: [[0,1,3],[0,2,3]]
//Explanation: The graph looks like this:
//0--->1
//|    |
//v    v
//2--->3

//There are two paths: 0 -> 1 -> 3 and 0 -> 2 -> 3.
//Note:
//
//The number of nodes in the graph will be in the range [2, 15].
//You can print different paths in any order, but you should keep the order of nodes inside one path.

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

// customized follow up: what if the graph may contain cycle????

class Solution {
public:
    // BFS
    // 80+ms
    vector<vector<int>> allPathsSourceTarget2(vector<vector<int>>& graph) {
        vector<vector<int>> rs;
        queue<vector<int>> q;
        q.push(vector<int>{0});
        while (q.size() != 0) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                vector<int> cur = q.front();
                if (cur.back() == graph.size() - 1) {
                    rs.push_back(vector<int>(std::move(cur)));
                } else {
                    for (int& nei : graph[cur.back()]) {
                        vector<int> vv(cur);
                        vv.push_back(nei);
                        q.push(std::move(vv));
                    }
                }
                q.pop();
            }
        }
        return rs;
    }
    
    // now try DFS
    // may save some time for array copying
    // non-memorizing DFS
    vector<vector<int>> allPathsSourceTarget(vector<vector<int>>& graph) {
        vector<vector<int>> rs;
        vector<int> v{0};
        dfs(v, rs, 0, graph);
        return rs;
    }
    
    void dfs(vector<int>& v, vector<vector<int>>& rs, int next, vector<vector<int>>& graph) {
        if (next == graph.size() - 1) {
            rs.push_back(vector<int>(v));
            return;
        }
        
        for (int& nei : graph[next]) {
            v.push_back(nei);
            dfs(v, rs, nei, graph);
            v.pop_back();
        }
    }
    
    // dfs with memo
    // passed
    vector<vector<int>> allPathsSourceTarget3(vector<vector<int>>& graph) {
        vector<vector<vector<int>>> memo(graph.size(), vector<vector<int>>());
        vector<bool> visited(graph.size(), false);
        dfs2(graph, 0, memo, visited);
        return memo[0];
    }
    
    void dfs2(const vector<vector<int>>& graph, int cur, vector<vector<vector<int>>>& memo, vector<bool>& visited) {
        if (cur == graph.size() - 1) {
            memo[cur] = vector<vector<int>>(1, vector<int>{(int) graph.size() - 1});
            visited[cur] = true;
            return;
        }
//        vector<vector<int>>& current = memo[cur];
        for (int nei : graph[cur]) {
            if (!visited[nei]) {
                dfs2(graph, nei, memo, visited);
            }
            for (vector<int> v : memo[nei]) {
                v.insert(v.begin(), cur);
                memo[cur].push_back(std::move(v));
            }
        }
        visited[cur] = true;
    }
};


int main(int argc, const char * argv[]) {

    return 0;
}
