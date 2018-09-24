//
//  main.cpp
//  MajorityElement_169
//
//  Created by Wenzhe Lu on 2018/3/1.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//
//
//Given an array of size n, find the majority element.
//The majority element is the element that appears more than ⌊ n/2 ⌋ times.
//
//You may assume that the array is non-empty and the majority element always exist in the array.

#include <iostream>
#include <vector>

using namespace std;

class Solution {
public:
    // solving it by map is trivial
    int majorityElement(vector<int>& nums) {
        // the array not empty
        int count = 0, candidate = nums[0];
        
        for (int& num : nums) {
            if (count == 0) {
                candidate = num;
                count++;
            } else if (num == candidate) {
                count++;
            } else {
                count--;
            }
        }
        
        // if count > 0 then majority exists
        return candidate;
    }
};

int main(int argc, const char * argv[]) {
    // insert code here...
    std::cout << "Hello, World!\n";
    return 0;
}
