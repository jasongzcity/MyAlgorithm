//
//  main.cpp
//  BurstBalloons_312
//
//  Created by Wenzhe Lu on 2018/3/20.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//Given n balloons, indexed from 0 to n-1.
//Each balloon is painted with a number on it represented by array nums.
//You are asked to burst all the balloons.
//If the you burst balloon i you will get nums[left] * nums[i] * nums[right] coins.
//Here left and right are adjacent indices of i. After the burst, the left and right then becomes adjacent.
//
//Find the maximum coins you can collect by bursting the balloons wisely.
//
//Note:
//(1) You may imagine nums[-1] = nums[n] = 1. They are not real therefore you can not burst them.
//(2) 0 ≤ n ≤ 500, 0 ≤ nums[i] ≤ 100
//
//Example:
//
//Given [3, 1, 5, 8]
//
//Return 167
//
//nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
//coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167

#include <iostream>
#include <vector>

using namespace std;

// brute-force intuitive solution would be O(n^3)
class Solution {
public:
    // try intuitive divide and conquer
    // also DP with memoization
    
    // a very smart trick about this solution:
    // divide the array into two parts is actually quite intuitive
    // if we just picking the next bursted balloon we will get stuck:
    // the neighbor can become hard to decide
    // so, we are picking the last deleted element in the range, so
    // we don't need to worry about the neighbor problem: they are fixed.
    int maxCoins(vector<int>& nums) {
        int n = nums.size();
        if (n == 0) return 0;
        int nnums[n + 2];
        for (int i = 0; i < n; i++) nnums[i + 1] = nums[i];
        nnums[0] = nnums[n + 1] = 1;
        vector<vector<int>> memo(n, vector<int>(n + 2, 0));
        
        return divideAndMerge(nnums, 0, n + 1, memo);
    }
    
    int divideAndMerge(int nums[], int left, int right, vector<vector<int>>& memo) {
        if (left + 1 == right) return 0;
        if (memo[left][right] > 0) return memo[left][right];
        
        int rs = 0;
        // i is the last deleted balloon in the range
        for (int i = left + 1; i < right; i++) {
            rs = std::max(rs, nums[i] * nums[left] * nums[right] + divideAndMerge(nums, left, i, memo) + divideAndMerge(nums, i, right, memo));
        }
        
        return memo[left][right] = rs;
    }
    
    // dp solution
    int maxCoins2(vector<int>& nums) {
        int n = nums.size();
        int nn[n + 2];
        int dp[n + 1][n + 2];
        memset(dp, 0, sizeof(int) * (n + 1) * (n + 2));
        nn[0] = nn[n + 1] = 1;
        for (int i = 0; i < n; i++) nn[i + 1] = nums[i];
        
        for (int dis = 2; dis <= n + 1; dis++) {
            for (int left = 0; left + dis <= n + 1; left++) {
                // range [left, left + dis]
                // now search in range
                int rs = 0;
                for (int i = left + 1; i < left + dis; i++) {
                    rs = std::max(rs, nn[i] * nn[left] * nn[left + dis] + dp[left][i] + dp[i][left + dis]);
                }
                dp[left][left + dis] = rs;
            }
        }
        return dp[0][n + 1];
    }
};

int main(int argc, const char * argv[]) {
    Solution s;
    vector<int> v{3, 1, 5, 8};
    s.maxCoins2(v);
    return 0;
}
