/*
 * @lc app=leetcode id=15 lang=cpp
 *
 * [15] 3Sum
 *
 * https://leetcode.com/problems/3sum/description/
 *
 * algorithms
 * Medium (23.28%)
 * Total Accepted:    477.7K
 * Total Submissions: 2.1M
 * Testcase Example:  '[-1,0,1,2,-1,-4]'
 *
 * Given an array nums of n integers, are there elements a, b, c in nums such
 * that a + b + c = 0? Find all unique triplets in the array which gives the
 * sum of zero.
 * 
 * Note:
 * 
 * The solution set must not contain duplicate triplets.
 * 
 * Example:
 * 
 * 
 * Given array nums = [-1, 0, 1, 2, -1, -4],
 * 
 * A solution set is:
 * [
 * ⁠ [-1, 0, 1],
 * ⁠ [-1, -1, 2]
 * ]
 * 
 * 
 */

#include <vector>

using std::vector;

class Solution {
public:
    vector< vector<int> > threeSum(vector<int>& nums) {
        vector< vector<int> > rs;

        if (nums.size() == 0) return rs;
        std::sort(nums.begin(), nums.end());

        for (int i = 0; i + 2 < nums.size(); i++) {
            int j = i + 1, k = nums.size() - 1;
            while (j < k) {
                int tm = nums[i] + nums[j] + nums[k];
                if (tm < 0) {
                    j++;
                } else if (tm > 0) {
                    k--;
                } else {
                    // we have found the combination
                    vector<int> tv;
                    tv.push_back(nums[i]);
                    tv.push_back(nums[j]);
                    tv.push_back(nums[k]);
                    rs.push_back(tv);

                    // skipping j and k on duplicate elements
                    while (j + 1 < nums.size() && nums[j] == nums[j + 1]) j++;
                    while (k > j && nums[k - 1] == nums[k]) k--;
                    j++;
                    k--;
                }
            }
            // we need to skip duplicate elements on i also
            while (i + 1 < nums.size() && nums[i] == nums[i + 1]) i++;
        }

        return rs;
    }
};
