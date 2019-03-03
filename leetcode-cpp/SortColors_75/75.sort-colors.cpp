/*
 * @lc app=leetcode id=75 lang=cpp
 *
 * [75] Sort Colors
 *
 * https://leetcode.com/problems/sort-colors/description/
 *
 * algorithms
 * Medium (41.22%)
 * Total Accepted:    288.9K
 * Total Submissions: 700.9K
 * Testcase Example:  '[2,0,2,1,1,0]'
 *
 * Given an array with n objects colored red, white or blue, sort them in-place
 * so that objects of the same color are adjacent, with the colors in the order
 * red, white and blue.
 * 
 * Here, we will use the integers 0, 1, and 2 to represent the color red,
 * white, and blue respectively.
 * 
 * Note: You are not suppose to use the library's sort function for this
 * problem.
 * 
 * Example:
 * 
 * 
 * Input: [2,0,2,1,1,0]
 * Output: [0,0,1,1,2,2]
 * 
 * Follow up:
 * 
 * 
 * A rather straight forward solution is a two-pass algorithm using counting
 * sort.
 * First, iterate the array counting number of 0's, 1's, and 2's, then
 * overwrite array with total number of 0's, then 1's and followed by 2's.
 * Could you come up with a one-pass algorithm using only constant space?
 * 
 * 
 */

#include <vector>
#include <algorithm>
#include <utility>

using namespace std;

class Solution {
public:
    void sortColorsII(vector<int>& nums) {
        int zero = -1, two = nums.size(), pointer = 0;
        for (; pointer < two;) {
            if (nums[pointer] == 1) {
                pointer++;
            } else if (nums[pointer] == 0) {
                std::swap(nums[++zero], nums[pointer++]);
            } else {
                std::swap(nums[--two], nums[pointer]);
            }
        }
    }

    void sortColors(vector<int>& nums) {
        int zero = -1, two = nums.size(), pointer = 0;
        while(pointer < two) {
            if (nums[pointer] == 1) {
                pointer++;
            } else if (nums[pointer] == 0) {
                swap(nums, ++zero, pointer++);
            } else {
                swap(nums, --two, pointer);
            }
        }
    }

    void swap(vector<int>& nums, int i, int j) {
        int tm = nums[i];
        nums[i] = nums[j];
        nums[j] = tm;
    }
};
