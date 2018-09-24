//
//  main.cpp
//  FirstMissingPositive_41
//
//  Created by Wenzhe Lu on 2018/3/1.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//Given an unsorted integer array, find the first missing positive integer.
//
//For example,
//Given [1,2,0] return 3,
//and [3,4,-1,1] return 2.
//
//Your algorithm should run in O(n) time and uses constant space.

#include <iostream>
#include <vector>

using namespace std;

class Solution {
public:
    // use constant space so no set & map
    // we can easily prove that the first missing positive must be
    // in the range of [1, nums.size() + 1]
    // the extreme case would be number from 1 to nums.size()
    // so that first missing number would be nums.size() + 1
    int firstMissingPositive(vector<int>& nums) {
        for (int i = 0; i < nums.size(); i++) {
            dfs(nums, nums[i]);
        }
        
        int i = 1;
        for (; i <= nums.size() && nums[i - 1] == i; i++) ;
        return i;
    }
    
    // try dfs
    void dfs(vector<int>& nums, int cur) {
        if (cur < 1 || cur > nums.size() || nums[cur - 1] == cur) return;
        int index = cur - 1, next = nums[index];
        nums[index] = cur;
        dfs(nums, next);
    }
    
    // could use swapping instead of dfs
};

int main(int argc, const char * argv[]) {
    
    return 0;
}
