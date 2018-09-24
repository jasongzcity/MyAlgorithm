//
//  main.cpp
//  FindAllNumberDisappearedInArr_448
//
//  Created by Wenzhe Lu on 2018/3/1.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//Given an array of integers where 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.
//
//Find all the elements of [1, n] inclusive that do not appear in this array.
//
//Could you do it without extra space and in O(n) runtime? You may assume the returned list does not count as extra space.
//
//Example:
//
//Input:
//[4,3,2,7,8,2,3,1]
//
//Output:
//[5,6]

#include <iostream>
#include <vector>

using namespace std;

class Solution {
public:
    // Note that these problems seem easy, just need some simple mapping.
    // but be careful with index mapping, you should use number - 1 to index.
    // Also, don't think you are smarter than compiler and do some small
    // optimization. you may make mistakes. So focus on the code, compiler will
    // do the optimizations.
    // use the swapping technique
    vector<int> findDisappearedNumbers(vector<int>& nums) {
        int i = 0;
        while (i < nums.size()) {
            if (nums[nums[i] - 1] != nums[i]) {
                std::swap(nums[nums[i] - 1], nums[i]);
                // keep checking this position
            } else {
                i++;
            }
        }
        
        vector<int> rs;
        i = 0;
        for (; i < nums.size(); i++) {
            if (nums[i] != i + 1)
                rs.push_back(i + 1);
        }
        return rs;
    }
};

int main(int argc, const char * argv[]) {
    
    return 0;
}
