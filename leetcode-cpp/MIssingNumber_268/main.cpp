//
//  main.cpp
//  MissingNumber_268
//
//  Created by Wenzhe Lu on 2018/1/25.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

#include <iostream>
#include <vector>
#include <assert.h>
#include <ctime>
#include <cstdlib>

//Given an array containing n distinct numbers taken from 0, 1, 2, ..., n, find the one that is missing from the array.
//
//Example 1
//Input: [3,0,1]
//Output: 2
//
//Example 2
//Input: [9,6,4,2,3,5,7,0,1]
//Output: 8
//
//Note:
//Your algorithm should run in linear runtime complexity.
//Could you implement it using only constant extra space complexity?

// similar:
// missing two number
// the array is sorted, find the missing one.

using namespace std;

class Solution {
public:
    
    // from 505
    // an array from a[1 .. n - 1] contains all number from 0 to n except two missing.
    // find them
    // could dfs work?? should be, lets try
    vector<int> missingTwoNumber(vector<int>& nums) {
        for (int i = 1; i < nums.size(); i++) {
            dfs3(nums, nums[i]);
        }
        vector<int> rs(2);
        int pt = 0;
        for (int i = 0; i < nums.size(); i++) {
            if (nums[i] != -1)
                rs[pt++] = i;
        }
        if (pt < 2) rs[1] = nums.size();
        return rs;
    }
    
    void dfs3(vector<int>& nums, int cur) {
        if (cur < 0 || cur >= nums.size()) return;
        int next = nums[cur];
        nums[cur] = -1;
        if (cur != 0) dfs3(nums, next); // little workaround
    }
    
    // follow up:
    // the conditions are the same, and we have the array sorted
    // we should solve it better than O(n) time
    // checked: should be right
    int missingNumberII3(vector<int>& nums) {
        // using binary search
        int lo = 0, hi = nums.size();
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (nums[mid] > mid) hi = mid;
            else lo = mid + 1;  // mid is not the missing number
        }
        return lo;
    }
    
    // second session
    // using O(n) space to solve this problem is trivial:
    // an auxiliry array will do fine
    
    // still I have missed a solution! using sum!
    // so in this kind of problem, consider using sum
    
    // dfs
    int missingNumberII2(vector<int>& nums) {
        for (int& num : nums) dfs2(nums, num);
        for (int i = 0; i < nums.size(); i++) {
            if (nums[i] != -1)
                return i;
        }
        return nums.size();
    }
    
    void dfs2(vector<int>& nums, int cur) {
        if (cur < 0 || cur >= nums.size()) return;
        int next = nums[cur];
        nums[cur] = -1;     // number "cur" has appeared in this array
        dfs2(nums, next);
    }
    
    // using bit manipulation
    int missingNumberII(vector<int>& nums) {
        int rs = 0;
        for (int i = 0; i < nums.size(); i++) {
            rs ^= nums[i];
            rs ^= i;
        }
        rs ^= nums.size();
        return rs;
    }
    
    // first session below  ////////////////
    
    int missingNumber(vector<int>& nums) {
        for (int i = 0; i < nums.size(); i++) {
            dfs(nums, nums[i]);
        }
        
        for (int i = 0; i < nums.size(); i++) {
            if (nums[i] >= 0) return i;
        }
        return nums.size();
    }
    
    void dfs(vector<int>& nums, int index) {
        if (index < nums.size() && nums[index] >= 0) {
            int tm = nums[index];
            nums[index] = -1;
            dfs(nums, tm);
        }
    }
    
    // easier solution using bit manipulation
    int missingNumber2(vector<int>& nums) {
        int rs = nums.size();
        for (int i = 0; i < nums.size(); i++) {
            rs ^= i ^ nums[i];
        }
        return rs;
    }
    
    // thought on writing test cases
    // first should given an N.
    // then generate either missing one number / missing two number / in order / out of order
    
    // pick missing n number from 0-N
    // and they are sorted..
    // 0 and N inclusive
    vector<int> pickMissingNumbers(int N, int n) {
        std::srand(std::time(nullptr));
        assert(n > 0 && n <= N);
        vector<int> rs;
        for (int i = 0; i < n; ) {
            int num = std::rand() % (N + 1);
            int posi = binSearch(rs, num);

            if (posi == 0 || rs[posi - 1] != num) {
                rs.insert(rs.begin() + posi, num);
                i++;
            }
        }
        return rs;
    }
    
    int binSearch(vector<int>& rs, int num) {
        int lo = 0, hi = rs.size();
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (rs[mid] > num) hi = mid;
            else lo = mid + 1;
        }
        return lo;
    }
    
    vector<int> missingNumberUnsorted(const vector<int>& missing, int N) {
        int len = N + 1 - missing.size();
        vector<int> rs(len, -1);
        int pm = 0, posi;
        for (int num = 0; num <= N; num++) {
            if (num == missing[pm]) {
                pm++;
            } else {
                // assign it to certain places
                do {
                    posi = std::rand() % len;
                } while (rs[posi] != -1);
                rs[posi] = num;
            }
        }
        return rs;
    }
    
    // construct a sorted array with missing numbers from 0-N
    vector<int> missingNumberSorted(const vector<int>& missing, int N) {
        vector<int> rs(N + 1 - missing.size());
        size_t pm = 0, pr = 0;
        int num = 0;
        while (num <= N) {
            if (num == missing[pm]) {
                pm++;
            } else {
                rs[pr++] = num;
            }
            num++;
        }
        return rs;
    }
};

int main(int argc, const char * argv[]) {
    Solution s;
////    vector<int> v{1, 2, 0};
////    std::cout << s.missingNumber(v) << endl;
//    // test case
//    vector<int> sorted = s.missingNumberUnsorted(s.pickMissingNumbers(10, 1), 10);
//    for (int& num : sorted)
//        std::cout << num << " ";
//    std::cout << std::endl;
//
////    vector<int> v{1,2,3,4,5,6,7,8,9};
////
////    std::cout << s.missingNumberII3(sorted) << std::endl;
////    std::cout << s.missingNumberII3(v) << std::endl;
//
//    vector<int> rs = s.missingTwoNumber(sorted);
//    for (int& num : rs)
//        std::cout << num << " ";
//    std::cout << std::endl;
    
    vector<int> v{90,2,4,1};
    vector<int> rs = s.missingTwoNumber(v);
    for (int& num : rs)
        std::cout << num << " ";
    std::cout << std::endl;
    
    for (int& num : v)
        std::cout << num << " ";
    std::cout << std::endl;
    return 0;
}
