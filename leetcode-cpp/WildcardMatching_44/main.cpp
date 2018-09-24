// Given an input string (s) and a pattern (p), 
// implement wildcard pattern matching with support for '?' and '*'.

// '?' Matches any single character.
// '*' Matches any sequence of characters (including the empty sequence).
// challenge: '?' match one or none charcter.

// The matching should cover the entire input string (not partial).

// Note:
// s could be empty and contains only lowercase letters a-z.
// p could be empty and contains only lowercase letters a-z, and characters like ? or *.

// Example 1:

// Input:
// s = "aa"
// p = "a"
// Output: false
// Explanation: "a" does not match the entire string "aa".

// Example 2:

// Input:
// s = "aa"
// p = "*"
// Output: true
// Explanation: '*' matches any sequence.

// Example 3:

// Input:
// s = "cb"
// p = "?a"
// Output: false
// Explanation: '?' matches 'c', but the second letter is 'a', which does not match 'b'.

// Example 4:

// Input:
// s = "adceb"
// p = "*a*b"
// Output: true
// Explanation: The first '*' matches the empty sequence, 
// while the second '*' matches the substring "dce".

// Example 5:

// Input:
// s = "acdcb"
// p = "a*c?b"
// Output: false

// dp[i + 1][j + 1] for matching at s[0 .. i], n[0 .. j]
// dp[i + 1][j + 1] = 
// case n[j] = s[i] or n[j] = '?' ==> dp[i][j]
// case n[j] = '*' ==> dp[i][j] (one) or dp[i][j + 1] (one more from string)

#include <string>
#include <iostream>

using namespace std;

class Solution {
public:
    bool isMatch(string s, string n) {
        bool dp[s.length() + 1][n.length() + 1];
        dp[0][0] = true;
        // any non-empty string doesn't match with empty pattern
        for (int i = 1; i <= s.length(); i++) {
            dp[i][0] = false;
        }

        for (int j = 1; j <= n.length(); j++) {
            dp[0][j] = n[j - 1] == '*' && dp[0][j - 1];
        }

        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < n.length(); j++) {
                if (n[j] == s[i] || n[j] == '?') {
                    dp[i + 1][j + 1] = dp[i][j];
                } else if (n[j] == '*') {
                    dp[i + 1][j + 1] = dp[i][j + 1] || dp[i + 1][j];
                } else {
                    dp[i + 1][j + 1] = false;
                }
            }
        }

        return dp[s.length()][n.length()];
    }
};

int main (int argc, char **argv) {
    Solution s;
    s.isMatch("cb", "?a");
    return 0;
}