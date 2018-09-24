//
//  main.cpp
//  SetMIsmatch_645
//
//  Created by Wenzhe Lu on 2018/3/1.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//The set S originally contains numbers from 1 to n. But unfortunately,
//due to the data error, one of the numbers in the set got duplicated to another number in the set,
//which results in repetition of one number and loss of another number.
//
//Given an array nums representing the data status of this set after the error.
//Your task is to firstly find the number occurs twice and then find the number that is missing.
//Return them in the form of an array.
//
//Example 1:
//
//Input: nums = [1,2,2,4]
//Output: [2,3]
//
//Note:
//
//The given array size will in the range [2, 10000].
//The given array's numbers won't have any order.


#include <iostream>

#include <vector>

using namespace std;

class Solution {
public:
    // sorting is trivial and slow.
    // using an array as a set is good.
    // can we do O(1) space?
    
    // Note: a lot of traps in this problem....
    vector<int> findErrorNums(vector<int>& nums) {
        vector<bool> set(nums.size(), false);
        vector<int> rs(2);
        for (int& num : nums) {
            if (set[num - 1]) rs[0] = num;
            set[num - 1] = true;
        }
        int i = 0;
        while (set[i++]);
        rs[1] = i;
        return rs;
    }
};

int main(int argc, const char * argv[]) {
    // insert code here...
    std::cout << "Hello, World!\n";
    return 0;
}
