//
//  main.cpp
//  SingleNumber
//
//  Created by Wenzhe Lu on 2018/2/26.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//
//
//Given an array of integers, every element appears twice except for one. Find that single one.
//
//Note:
//Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?

// similar problems: single numberII/ single numberIII/ single number IV/ Missing number/ find duplicate number
// find the difference/find the celebrity/ find missing two number/first missing positive/ set mismatch

#include <vector>

using std::vector;

class Solution {
public:
    // its easy to think of using something like map or set
    // another way: use xor
    int singleNumber(vector<int>& nums) {
        int rs = 0;
        for (auto& n : nums)
            rs ^= n;
        return rs;
    }
};

int main(int argc, const char * argv[]) {

    return 0;
}
