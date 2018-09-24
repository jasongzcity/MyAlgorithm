//
//  main.cpp
//  ThreeSumClosest_16
//
//  Created by Wenzhe Lu on 2018/3/6.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//Given an array S of n integers, find three integers in S such that the sum is closest to a given number, target.
//Return the sum of the three integers. You may assume that each input would have exactly one solution.
//
//For example, given array S = {-1 2 1 -4}, and target = 1.
//
//The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).

#include <iostream>
#include <vector>

using namespace std;

class Solution {
public:
    // use the same idea as 3Sum. O(n^2)
    // notice that in this solution you can't skip the duplicate numbers
    // example: [0, 1, 1, 5, 5]. target = 2
    // if you use skipping technique, you may skip those 1s, and eventually
    // miss the 0 + 1 + 1 answer
    int threeSumClosest(vector<int>& nums, int target) {
        // sorting in C++?
        sort(nums.begin(), nums.end());
        long rs = numeric_limits<int>::max();   // avoid overflow
        for (int i = 0; i < nums.size(); i++) {
            int newTar = target - nums[i];
            for (int left = i + 1, right = nums.size() - 1; left < right; ) {
                // you have to skip all the duplicates to avoid
                
                if (std::abs(rs - target) > abs(nums[left] + nums[right] - newTar)) {
//                    while (left < right && nums[left] == nums[left + 1]) left++;
//                    while (left < right && nums[right] == nums[right - 1]) right--;
                    rs = nums[i] + nums[left] + nums[right];
                }
                if (nums[left] + nums[right] > newTar) right--;
                else if (nums[left] + nums[right] < newTar) left++;
                else return target;
            }
        }
        return (int) rs;
    }
};

int main(int argc, const char * argv[]) {
    Solution s;
    vector<int> v{-1, 0, 1, 1, 55};
    cout << s.threeSumClosest(v, 3) << endl;
    return 0;
}
