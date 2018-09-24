//
//  main.cpp
//  FindMinInRotatedArray_153
//
//  Created by Wenzhe Lu on 2018/2/9.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
//
//(i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
//
//Find the minimum element.
//
//You may assume no duplicate exists in the array.

#include <iostream>
#include <vector>

using std::vector;

class Solution {
public:
    // wrong case! In this question you can't compare
    // with the lower end! because when you got
    // a sorted array, you may shrink the subproblem in a wrong direction!
    int findMinX(vector<int>& nums) {
        // assume not empty
        int lo = 0, hi = nums.size();
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (nums[mid] <= nums[lo]) hi = mid;
            else lo = mid + 1;
        }
        return nums[lo];
    }
    
    int findMin(vector<int>& nums) {
        size_t lo = 0, hi = nums.size() - 1;
        while (lo < hi) {
            size_t mid = (lo + hi) / 2;
            if (nums[mid] > nums[hi]) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
};

int main(int argc, const char * argv[]) {
    Solution s;
    vector<int> a{2, 3, 1};
    std::cout << s.findMin( a ) << std::endl;
    return 0;
}
