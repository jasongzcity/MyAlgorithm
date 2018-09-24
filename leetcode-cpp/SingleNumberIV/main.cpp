//
//  main.cpp
//  SingleNumberIV
//
//  Created by Wenzhe Lu on 2018/2/27.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

// follow up of single number I: all number appears twice except one number
// appears once.
// all the the same number are next to each other.

#include <vector>
#include <iostream>

using std::vector;

class Solution {
public:
    // using bit manipulation is trivial
    // there must be a even better way
    // better than the O(n) solution
    
    // using binary search.
    int singleNumber(const vector<int>& nums) {
        int lo = 0, hi = nums.size();
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (mid - 1 >= 0 && nums[mid] == nums[mid - 1]) {
                if ((mid - 1 - lo) % 2 == 1) {
                    hi = mid - 2;
                } else {
                    lo = mid + 1;
                }
            } else if (mid + 1 < nums.size() && nums[mid + 1] == nums[mid]) {
                if ((hi - mid - 2) % 2 == 1) {
                    lo = mid + 2;
                } else {
                    hi = mid - 1;
                }
            } else {
                return nums[mid];
            }
        }
        return nums[lo];
    }
};

int main(int argc, const char * argv[]) {
    Solution s;
    using std::cout;
    using std::endl;
    cout << s.singleNumber(vector<int>{1,2,2}) << endl;
    cout << s.singleNumber(vector<int>{1,1,2}) << endl;
    cout << s.singleNumber(vector<int>{3,3,4,4,1,2,2}) << endl;
    cout << s.singleNumber(vector<int>{1,1,2,2,3}) << endl;
    cout << s.singleNumber(vector<int>{1,1,5,5,6,6,7,7,8,2,2}) << endl;
    cout << s.singleNumber(vector<int>{1,1,4,4,12,12,7,8,8,2,2}) << endl;
    cout << s.singleNumber(vector<int>{1,1,4,4,12,12,8,8,2,2,7}) << endl;
    cout << s.singleNumber(vector<int>{6,1,1,4,4,12,12,8,8,2,2}) << endl;
    cout << s.singleNumber(vector<int>{1}) << endl;

    
    return 0;
}
