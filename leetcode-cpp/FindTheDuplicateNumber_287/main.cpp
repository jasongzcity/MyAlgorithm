//
//  main.cpp
//  FindTheDuplicateNumber_287
//
//  Created by Wenzhe Lu on 2018/2/28.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//


//Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive),
//prove that at least one duplicate number must exist.
//Assume that there is only one duplicate number, find the duplicate one.
//
//Note:
//
//You must not modify the array (assume the array is read only).
//You must use only constant, O(1) extra space.
//Your runtime complexity should be less than O(n^2).
//There is only one duplicate number in the array, but it could be repeated more than once.

#include <iostream>
#include <vector>

using namespace std;

// similar:
class Solution {
public:
    // the same idea with linked list cycle
    // think of the index-value sequence as a chain
    // there must be a cycle in the list
    // and the start of the cycle must be
    // the duplicate number, because there are
    // two index that points to the same number
    int findDuplicate(vector<int>& nums) {
        int fast = nums[0], slow = nums[0];
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (fast != slow);
        slow = nums[0];
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }
};

int main(int argc, const char * argv[]) {

    return 0;
}
