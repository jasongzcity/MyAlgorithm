//
//  main.cpp
//  NumberOfSubarraysWithBoundedMaximum_795
//
//  Created by Wenzhe Lu on 2018/3/5.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//We are given an array A of positive integers, and two positive integers L and R (L <= R).
//
//Return the number of (contiguous, non-empty) subarrays such that the value
//of the maximum array element in that subarray is at least L and at most R.
//
//Example :
//Input:
//A = [2, 1, 4, 3]
//L = 2
//R = 3
//Output: 3
//Explanation: There are three subarrays that meet the requirements: [2], [2, 1], [3].
//Note:
//
//L, R  and A[i] will be an integer in the range [0, 10^9].
//The length of A will be in the range of [1, 50000].

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

class Solution {
public:
    // O(n^2)
    // maintaining the largest element in specific range
    // can we do better?
    // Even though you cut out those number too large and
    // find subarrays in the range, it's still O(n^2)
    int numSubarrayBoundedMax2(vector<int>& A, int L, int R) {
        int rs = 0;
        for (int begin = 0; begin < A.size(); begin++) {
            for (int end = begin, maxi = 0; end < A.size(); end++) {
                maxi = std::max(maxi, A[end]);
                if (maxi > R) break;
                if (maxi >= L) rs++;
            }
        }
        return rs;
    }
    
    
    // even better solution
    // O(n)
    int numSubarrayBoundedMax(vector<int>& A, int L, int R) {
        int rs = 0, smaller = 0, inc = 0;
        for (int i = 0; i < A.size(); i++) {
            if (A[i] < L) {
                // include this because it can construct a subarray
                smaller++;
                inc++;
            } else if (A[i] > R) {
                // count all the subarray before then clear
                rs += subArray(inc) - subArray(smaller);
                smaller = inc = 0;
            } else {
                // A[i] in between. It should be included, and calculated the lower subarray before.
                rs -= subArray(smaller);
                smaller = 0;
                inc++;
            }
        }
        // don't forget to add last subarrays
        rs += subArray(inc) - subArray(smaller);
        return rs;
    }
    
    // number of subarrays with n elements
    int subArray(int n) {
        return n * (n + 1) / 2;
    }
};

int main(int argc, const char * argv[]) {

    return 0;
}
