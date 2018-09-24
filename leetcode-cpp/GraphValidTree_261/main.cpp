//
//  main.cpp
//  GraphValidTree_261
//
//  Created by Wenzhe Lu on 2018/1/30.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//


//Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes),
//write a function to check whether these edges make up a valid tree.
//
//For example:
//
//Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], return true.
//
//Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], return false.
//
//Note: you can assume that no duplicate edges will appear in edges.
//Since all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges.

#include <iostream>
#include <vector>
#include <unordered_map>
#include <queue>
#include <set>

using namespace std;

class Solution {
public:
    // Note that "valid" stands for acyclic and all connected
    // intuitive thought: DFS
    // great material to learn map in C++..
    // how find fail? how will [] act? how will [] fail?
    // vector already there? or you should initialize it?
    
    // Ref: find fail return map.end();
    // [] return the reference, fail will automatically insert element and return a reference.
    // count return the number associated with specific key(1 or 0).
    // "at" function return reference, throw "out of range" if fail.
    
    // dfs solution is kind of slow, try out bfs
    // accepted.
    bool validTree(int n, vector<pair<int, int>>& edges) {
        unordered_map<int, vector<int>> graph(n << 1);
        for (auto& p : edges) {
            graph[p.first].push_back(p.second);
            graph[p.second].push_back(p.first);
        }
        vector<bool> flags(n, false);
        int counter = 0;
        if (!dfs(graph, 0, n, flags, counter)) return false;
        return counter == n;
    }
    
    // how to manage vector<pair<>> into graph?
    // try use hashmap
    // flags in the flags vector:
    // false not visited, true visited.
    bool dfs(unordered_map<int, vector<int>>& graph, int cur, int parent, vector<bool>& flags, int& counter) {
        if (flags[cur] == true) return false;
        flags[cur] = true;
        ++counter;
        for (int& neighbor : graph[cur]) {
            if (neighbor != parent && !dfs(graph, neighbor, cur, flags, counter)) {
                return false;
            }
        }
        return true;
    }
    
    // accepted.
    bool validTreeII(int n, vector<pair<int, int>>& edges) {
        if (edges.size() != n) return false; // less, nodes unconnected. more, cyclic
        
        // below code check if there are nodes unconnected.
        
        unordered_map<int, vector<int>> graph(n << 1);
        for (auto& p : edges) {
            graph[p.first].push_back(p.second);
            graph[p.second].push_back(p.first);
        }
        queue<int> q;
        set<int> visited;
        
        q.push(0);
        while(!q.empty()) {
            int cur = q.front();
            q.pop();
            visited.insert(cur);
            for (auto& nei : graph[cur]) {
                if (visited.find(nei) != visited.end())
                    q.push(nei);
            }
        }
        return visited.size() == n;
    }
    
    // still, it's not the fastest solution.
    // thought: using edges.size() != n we can eliminate unconnected situation.
    // we just need to check if acyclic using union-find alike method.
    // also notice that in this situation if there is no cycle, there is no unconnected node.
    // Accepted.
    bool validTreeIII(int n, vector<pair<int, int>>& edges) {
        if (edges.size() != n - 1) return false;
        
        vector<int> root(n);
        for (int i = 0; i < n; i++) root[i] = i;
//        root[edges[0].first] = edges[0].first;
        for (auto& p : edges) {
            int r1 = root[p.first], r2 = root[p.second];
            // notice that we must use while loop, because there may be a long distance between
            // the leaf and the root.
            while (root[r1] != r1) r1 = root[r1];
            while (root[r2] != r2) r2 = root[r2];
            // now r1 and r2 are two roots
            if (r1 == r2) return false;
            root[r2] = r1;
        }
        return true;
    }
};

int main(int argc, const char * argv[]) {
    string s("ssss");
//    for (char& c : s) {
//        c = 'k';
//    }
//    std::cout << s << endl;
    
    vector<pair<int, int>> edges({{0, 1}, {0, 2}, {2, 3}, {2, 4}}); // using initialization lists.
    Solution so;
    so.validTreeII(5, edges);
    return 0;
}
