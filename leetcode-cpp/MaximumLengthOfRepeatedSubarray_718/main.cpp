//
//  main.cpp
//  MaximumLengthOfRepeatedSubarray_718
//
//  Created by Wenzhe Lu on 2018/3/2.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//Given two integer arrays A and B, return the maximum length of an subarray that appears in both arrays.
//
//Example 1:
//
//Input:
//A: [1,2,3,2,1]
//B: [3,2,1,4,7]
//Output: 3
//Explanation:
//The repeated subarray with maximum length is [3, 2, 1].
//
//Note:
//
//1 <= len(A), len(B) <= 1000
//0 <= A[i], B[i] < 100

// from citadel...

#include <iostream>
#include <vector>
#include <algorithm>
#include <cstring>

using namespace std;

class Solution {
public:
    // intuitive O(n^2)
    // try dp - should not be O
    // accepted, though slow
    // what's the meaning of dp.....
    int findLength(vector<int>& A, vector<int>& B) {
        int rs = 0;
        vector<vector<int>> dp(A.size() + 1, vector<int>(B.size() + 1, 0));
        for (int i = 0; i < A.size(); i++) {
            for (int j = 0; j < B.size(); j++) {
                if (A[i] == B[j]) {
                    dp[i + 1][j + 1] = std::max(dp[i][j] + 1, dp[i + 1][j + 1]);
                    rs = std::max(rs, dp[i + 1][j + 1]);
                }
            }
        }
        return rs;
    }
    
    // use matrix!
    // better
    // notice: only when undecided length should use vector
    int findLength2(vector<int>& A, vector<int>& B) {
        int rs = 0;
        int m[A.size() + 1][B.size() + 1];
        memset(m, 0, sizeof(int) * (A.size() + 1) * (B.size() + 1));
        for (int i = 0; i < A.size(); i++) {
            for (int j = 0; j < B.size(); j++) {
                if (A[i] == B[j]) {
                    m[i + 1][j + 1] = std::max(m[i][j] + 1, m[i + 1][j + 1]);
                    rs = std::max(rs, m[i + 1][j + 1]);
                }
            }
        }
        return rs;
    }
};

int main(int argc, const char * argv[]) {
    return 0;
}
