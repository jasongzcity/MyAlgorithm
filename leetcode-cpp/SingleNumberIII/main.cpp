//
//  main.cpp
//  SingleNumberIII
//
//  Created by Wenzhe Lu on 2018/2/26.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//


//Given an array of numbers nums, in which exactly two elements appear only once and
//all the other elements appear exactly twice.
//Find the two elements that appear only once.
//
//For example:
//
//Given nums = [1, 2, 1, 3, 2, 5], return [3, 5].
//
//Note:
//
//The order of the result is not important. So in the above example, [5, 3] is also correct.
//Your algorithm should run in linear runtime complexity. Could you implement it using only constant space complexity?

#include <vector>

using std::vector;


class Solution {
public:
    
    // again, using a map can do it in linear time and O(n) space, trivial
    // further thought?
    vector<int> singleNumber(vector<int>& nums) {
        vector<int> rs(2, 0);
        int diff = 0;
        for (int& num : nums) diff ^= num;
        // get the last set bit
        diff &= -diff;
        // now we know a and b is difference in this bit
        for (int& num : nums) {
            if ((num & diff) == 0) {
                rs[0] ^= num;
            } else {
                rs[1] ^= num;
            }
        }
        return rs;
    }
};

int main(int argc, const char * argv[]) {

    return 0;
}
