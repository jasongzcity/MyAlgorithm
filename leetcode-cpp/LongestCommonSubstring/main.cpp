//
//  main.cpp
//  LongestCommonSubstring
//
//  Created by Wenzhe Lu on 2018/3/8.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

// Longest common substring problem
// Given two strings A and B, return their longest common substring.
// notice the corner case: if two string have nothing in common
// return an empty string
// similar: longest common subarray / longest palindrome substring

#include <iostream>
#include <string>

using namespace std;

class Solution {
public:
    string longestCommonSubstring(string A, string B) {
        int na = A.size(), nb = B.size(), len = 0, begin = 0;
        int dp[na + 1][nb + 1];
        memset(dp, 0, sizeof(int) * (na + 1) * (nb + 1));
        for (int i = 0; i < na; i++) {
            for (int j = 0; j < nb; j++) {
                if (A[i] == B[j]) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                    if (dp[i + 1][j + 1] > len) {
                        len = dp[i + 1][j + 1];
                        begin = i - len + 1;
                    }
                }
            }
        }
        
        return A.substr(begin, len);
    }
};

int main(int argc, const char * argv[]) {
    
    Solution s;
    
    string a = "abdjiojosdmnudmasfqnfdaas", b = "ashuefjiojojuiimjmasfqndvsfsbgd";
    string c = "", d = "", e = "1";
    std::cout << s.longestCommonSubstring(a, b) << endl;
    std::cout << s.longestCommonSubstring(c, d) << endl;
    std::cout << s.longestCommonSubstring(c, e) << endl;
    
    return 0;
}
