//
//  main.cpp
//  SearchInRotatedArray_33
//
//  Created by Wenzhe Lu on 2018/2/11.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
//
//(i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
//
//You are given a target value to search. If found in the array return its index, otherwise return -1.
//
//You may assume no duplicate exists in the array.

#include <iostream>
#include <vector>

using std::vector;

class Solution {
public:
    // simple idea: only check if the target is in the sorted half
    // accepted.
    int search(vector<int>& nums, int target) {
        int lo = 0, hi = nums.size() - 1;
        while(lo < hi) {
            int mid = (lo + hi) / 2;
            if (nums[mid] < nums[hi]) {
                if (target > nums[mid] && target <= nums[hi]) lo = mid + 1;
                else hi = mid;
            } else {
                if (target <= nums[mid] && target >= nums[lo]) hi = mid;
                else lo = mid + 1;
            }
        }
        return nums[lo] == target ? lo : -1;
    }
};

int main(int argc, const char * argv[]) {
    std::cout << "Hello, World!\n";
    return 0;
}
