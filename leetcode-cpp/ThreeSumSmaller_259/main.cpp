//
//  main.cpp
//  ThreeSumSmaller_259
//
//  Created by Wenzhe Lu on 2018/3/6.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//


//Given an array of n integers nums and a target, find the number of index triplets i, j, k
//with 0 <= i < j < k < n that satisfy the condition nums[i] + nums[j] + nums[k] < target.
//
//For example, given nums = [-2, 0, 1, 3], and target = 2.
//
//Return 2. Because there are two triplets which sums are less than 2:
//
//[-2, 0, 1]
//[-2, 0, 3]
//Follow up:
//Could you solve it in O(n^2) runtime?

#include <iostream>
#include <vector>

using namespace std;

class Solution {
public:
    // O(n^2logn) using binary search is trivial
    // try use the same technique like 3Sums
    // it's like 2sum smaller with O(n)
    int threeSumSmaller(vector<int>& nums, int target) {
        sort(nums.begin(), nums.end());
        
        int rs = 0;
        
        for (int i = 0; i < nums.size(); i++) {
            int newTar = target - nums[i];
            for (int left = i + 1, right = nums.size() - 1; left < right; ) {
                if (nums[right] + nums[left] >= newTar) {
                    right--;
                } else {
                    rs += right - left++;
                }
            }
        }
        
        return rs;
    }
};

int main(int argc, const char * argv[]) {
    // insert code here...
    std::cout << "Hello, World!\n";
    return 0;
}
