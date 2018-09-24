//
//  main.cpp
//  SingleNumberII
//
//  Created by Wenzhe Lu on 2018/2/26.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//
//
//Given an array of integers, every element appears three times except for one,
//which appears exactly once. Find that single one.
//
//Note:
//Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?

#include <vector>

using std::vector;

class Solution {
public:
    // using set and sum technique is trivial
    // other thought???
    // hard to understand
    
    // one and two represents the bits will become 1
    // if they appear once or twice
    // try to do another follow up: single number for five times
    // hint: your will need three bits to record its status
    /**
     *
       0 -> 1 -> 2 -> 3 -> 4 -> 0
     000   100  010  011  001  000
     gosh this is hard.... give up
     
     *
     */
    int singleNumber(vector<int>& nums) {
        int ones = 0, twos = 0;
        for(int i = 0; i < nums.size(); i++){
            ones = (ones ^ nums[i]) & ~twos;
            twos = (twos ^ nums[i]) & ~ones;
        }
        return ones;
    }
    
    // use a length 32 array to remember the number of times
    // of each bit
    // so in total O(32n)
};

int main(int argc, const char * argv[]) {

    return 0;
}
