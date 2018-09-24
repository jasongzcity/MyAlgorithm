//
//  main.cpp
//  TopKFrequentElement_347
//
//  Created by Wenzhe Lu on 2018/3/14.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//Given a non-empty array of integers, return the k most frequent elements.
//
//For example,
//Given [1,1,1,2,2,3] and k = 2, return [1,2].
//
//Note:
//You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
//Your algorithm's time complexity must be better than O(n log n), where n is the array's size.

#include <iostream>
#include <vector>
#include <unordered_map>

using namespace std;

class Solution {
public:
    // good example for CPP map operations
    vector<int> topKFrequent(vector<int>& nums, int k) {
        vector<int> rs;
        rs.reserve(k);
        int pt, maxcount = 1;
        unordered_map<int, int> map(nums.size());
        for (int& i : nums) {
            unordered_map<int, int>::const_iterator iter = map.find(i);
            if (iter == map.end()) map[i] = 1;
            else {
                map[i]++;
                maxcount = max(maxcount, map[i]);
            }
        }
        
        vector<vector<int>> list(maxcount, vector<int>());
        for (auto& pr : map) {
            list[pr.second - 1].push_back(pr.first);
        }
        
        pt = maxcount - 1;
        while (rs.size() < k) {
            rs.insert(rs.end(), list[pt].begin(), list[pt].end());
            pt--;
        }
        
        return rs;
    }
    
    // also can use a priority queue with pairs as its elements
};

int main(int argc, const char * argv[]) {

    return 0;
}
