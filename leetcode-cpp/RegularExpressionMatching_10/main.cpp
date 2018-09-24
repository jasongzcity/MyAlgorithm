#include <string>
#include <iostream>

// Given an input string (s) and a pattern (p), 
// implement regular expression matching with support for '.' and '*'.

// '.' Matches any single character.
// '*' Matches zero or more of the preceding element.

// The matching should cover the entire input string (not partial).

// Note:

//     s could be empty and contains only lowercase letters a-z.
//     p could be empty and contains only lowercase letters a-z, 
//     and characters like . or *.

// Example 1:

// Input:
// s = "aa"
// p = "a"
// Output: false
// Explanation: "a" does not match the entire string "aa".

// Example 2:

// Input:
// s = "aa"
// p = "a*"
// Output: true
// Explanation: '*' means zero or more of the precedeng element, 'a'.
// Therefore, by repeating 'a' once, it becomes "aa".

// Example 3:

// Input:
// s = "ab"
// p = ".*"
// Output: true
// Explanation: ".*" means "zero or more (*) of any character (.)".

// Example 4:

// Input:
// s = "aab"
// p = "c*a*b"
// Output: true
// Explanation: c can be repeated 0 times, 
// a can be repeated 1 time. Therefore it matches "aab".

// Example 5:

// Input:
// s = "mississippi"
// p = "mis*is*p*."
// Output: false

// Such a classic problem, but it's indeed fun.

// follow up: '?' - one or none of previous character. 

using namespace std;

class Solution {
public:
    bool isMatch(string s, string p) {
        bool dp[s.length() + 1][p.length() + 1];

        // corner cases: 
        dp[0][0] = true; // both empty
        for (int i = 1; i <= p.length(); i++) {
            dp[0][i] = (p[i - 1] == '*' || p[i - 1] == '?') && dp[0][i - 2];
        }

        // no matches for empty pattern 
        for (int i = 1; i <= s.length(); i++) {
            dp[i][0] = false;
        }

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= p.length(); j++) {
                if (p[j - 1] == s[i - 1] || p[j - 1] == '.') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (p[j - 1] == '*') {
                    dp[i][j] = dp[i][j - 1] || dp[i][j - 2] || 
                                ((s[i - 1] == p[j - 2] || p[j - 2] == '.') && dp[i - 1][j]);
                                // last situation: as long as the string char is matched with 
                                // the char before *, extend that single char into matching
                } else if (p[j - 1] == '?') {
                    dp[i][j] = dp[i][j - 2] || dp[i][j - 1];
                } else {
                    dp[i][j] = false;
                }
            }
        }
        return dp[s.length()][p.length()];
    }
};


int main(int argc, const char * argv[]) {
    Solution s;
    s.isMatch("aaa", "ab*a"); 
    return 0;
}