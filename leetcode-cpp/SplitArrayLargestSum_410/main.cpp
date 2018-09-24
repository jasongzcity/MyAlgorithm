//
//  main.cpp
//  SplitArrayLargestSum_410
//
//  Created by Wenzhe Lu on 2018/1/31.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//


//Given an array which consists of non-negative integers and an integer m,
//you can split the array into m non-empty continuous subarrays.
//Write an algorithm to minimize the largest sum among these m subarrays.
//
//Note:
//If n is the length of array, assume the following constraints are satisfied:
//
//1 ≤ n ≤ 1000
//1 ≤ m ≤ min(50, n)
//Examples:
//
//Input:
//nums = [7,2,5,10,8]
//m = 2
//
//Output:
//18
//
//Explanation:
//There are four ways to split nums into two subarrays.
//The best way is to split it into [7,2,5] and [10,8],
//where the largest sum among the two subarrays is only 18.

#include <vector>

using namespace std;

// hint: tag: binary search and dynamic programming
class Solution {
public:
    // binary search solution
    int splitArray(vector<int>& nums, int m) {
        // the result is between the largest number in the array and the sum of the whole array.
        int hi = 0, lo = -1;
        for (auto& n : nums) {
            hi += n;
            if (n > lo) lo = n;
        }
        if (m == 1) return hi; // no division.
        // now do binary search
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (valid(nums, mid, m)) {
                // if we can make each subarray sum smaller than or equal to mid.
                // we should try to make mid smaller. notice mid is valid.
                hi = mid - 1;
            } else {
                // if there is always subarray larger than mid, try to make mid larger.
                // mid is invalid.
                lo = mid + 1;
            }
        }
        return lo;
    }
    
    // can we divide nums into m subarrays, with no sum larger than target?
    bool valid(vector<int>& nums, int target, int m) {
        int counter = 1;
        long sum = 0;
        for (auto& num : nums) {
            sum += num;
            if (sum > target) {
                sum = num; // reset
                if (++counter > m) return false;
            }
        }
        return true;
    }
    
    // giving up on dp solution....
};

int main(int argc, const char * argv[]) {
    return 0;
}
