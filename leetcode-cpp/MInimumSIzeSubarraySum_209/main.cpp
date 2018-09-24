//
//  main.cpp
//  MInimumSIzeSubarraySum_209
//
//  Created by Wenzhe Lu on 2018/3/2.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//Given an array of n positive integers and a positive integer s,
//find the minimal length of a contiguous subarray of which the sum ≥ s. If there isn't one, return 0 instead.
//
//For example, given the array [2,3,1,2,4,3] and s = 7,
//the subarray [4,3] has the minimal length under the problem constraint.
//
//More practice:
//
//If you have figured out the O(n) solution, try coding another solution of which the time complexity is O(n log n).

// similar problem: Maximum length of repeated subarray_718


#include <iostream>
#include <vector>
#include <limits>

using namespace std;

class Solution {
public:
    // sliding window problem
    int minSubArrayLen(int s, vector<int>& nums) {
        if (nums.size() == 0) return 0;
        int rs = numeric_limits<int>::max();
        int fast = 1, slow = 0, sum = nums[0];
        while (fast < nums.size() || sum >= s) {
            if (sum >= s) {
                // shrink the window
                while (sum >= s) {
                    sum -= nums[slow++];
                }
                rs = std::min(rs, fast - slow + 1);
            } else {
                sum += nums[fast++];
            }
        }
        return rs == numeric_limits<int>::max() ? 0 : rs;
    }
    
    // better code style:
    int minSubArrayLen2(int s, vector<int>& nums) {
        if (nums.size() == 0) return 0;
        int rs = numeric_limits<int>::max();
        int fast = 0, slow = 0, sum = 0;
        while (fast < nums.size()) {
            sum += nums[fast++];
            while (sum >= s) {
                sum -= nums[slow++];
                rs = min(rs, fast - slow + 1);
            }
        }
        return rs == numeric_limits<int>::max() ? 0 : rs;
    }
    
    // O(nlogn) solution
    int minSubArrayLen3(int s, vector<int>& nums) {
        vector<int> sums(nums.size() + 1, 0);   // sums[0] = 0 for corner case work around
        for (int i = 1; i < sums.size(); i++) {
            sums[i] = sums[i - 1] + nums[i - 1];
        }
        
        int rs = nums.size() + 1;
        for (int i = 0; i < sums.size(); i++) {
            int end = binSearch(sums, s + sums[i], i);
            if (end == sums.size()) break;  // no more possible subarray
            if (end - i < rs) rs = end - i;
        }
        return rs == nums.size() + 1 ? 0 : rs;
    }
    
    int binSearch(vector<int>& sums, int target, int begin) {
        int lo = begin, hi = sums.size();
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (sums[mid] >= target) hi = mid;
            else lo = mid + 1;
        }
        
        return lo;
    }
};

int main(int argc, const char * argv[]) {
    Solution s;
    vector<int> v{2, 3, 1, 2, 4, 3};
    cout << s.minSubArrayLen(7, v) << endl;
    return 0;
}
