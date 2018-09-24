//
//  main.cpp
//  LongestPalindromeSubstr_5
//
//  Created by Wenzhe Lu on 2018/3/6.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.
//
//Example:
//
//Input: "babad"
//
//Output: "bab"
//
//Note: "aba" is also a valid answer.
//
//
//Example:
//
//Input: "cbbd"
//
//Output: "bb"

#include <iostream>
#include <string>

using namespace std;

// Notice that this problem has many solutions
class Solution {
public:
    // a rather intuitive solution
    // notice: not optimal!
    // most part of the code look the same, can we improve this?????
    string longestPalindrome2(string s) {
        if (s.size() < 2) return s;
        int len = 1, begin = 0;
        for (int i = 1; i < s.size(); i++) {
            // check i-center
            int left = i - 1, right = i + 1;
            for (; left >= 0 && right < s.size() && s[left] == s[right]; left--, right++) ;
            if (len < right - left - 1) {
                len = right - left - 1;
                begin = left + 1;
            }
            
            // check even length palindrome
            left = i - 1;
            right = i;
            for (; left >= 0 && right < s.size() && s[left] == s[right]; left--, right++) ;
            if (len < right - left - 1) {
                len = right - left - 1;
                begin = left + 1;
            }
        }
        
        return s.substr(begin, len);
    }
    
    // this solution cover the situations in the solution above subtly
    string longestPalindrome3(string s) {
        if (s.size() < 2) return s;
        int len = 1, begin = 0;
        for (int i = 0; i < s.size(); ) {
            int left = i - 1, right = i + 1;
            // expand right to the left most position
            while (right < s.size() && s[right] == s[i]) right++;
            i = right;
            for ( ;left >= 0 && right < s.size() && s[left] == s[right]; right++, left--) ;
            if (len < right - left - 1) {
                len = right - left - 1;
                begin = left + 1;
            }
        }
        
        return s.substr(begin, len);
    }
    
    // dp solution
    // accepted
    string longestPalindrome4(string s) {
        if (s.size() < 2) return s;
        int n = s.size(), len = 1, begin = 0;
        bool dp[n][n];
        memset(dp, true, sizeof(bool) * n * n);
        for (int dis = 1; dis < n; dis++) {
            for (int col = dis, row = 0; col < n; col++, row++) {
                dp[row][col] = s[row] == s[col] && dp[row + 1][col - 1];
                if (dp[row][col] && col - row + 1 > len) {
                    len = col - row + 1;
                    begin = row;
                }
            }
        }
        
        return s.substr(begin, len);
    }
    
    // use the longest common substring technique
    // accepted
    string longestPalindrome(string s) {
        if (s.size() < 2) return s;
        int n = s.size(), begin = 0, len = 0;
        string rs(n, 0);
        for (int i = 0; i < n; i++) rs[n - 1 - i] = s[i];
        int dp[n + 1][n + 1];
        memset(dp, 0, sizeof(int) * (n + 1) * (n + 1));
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (s[i] == rs[j]) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                    int newLen = dp[i + 1][j + 1];
                    if (newLen > len && j - newLen + 1 + i == n - 1 && i - newLen + 1 + j == n - 1) {
                        len = newLen;
                        begin = i - newLen + 1;
                    }
                }
            }
        }
        
        return s.substr(begin, len);
    }
};

int main(int argc, const char * argv[]) {
    return 0;
}
