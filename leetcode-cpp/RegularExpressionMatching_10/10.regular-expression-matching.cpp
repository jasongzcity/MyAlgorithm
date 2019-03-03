/*
 * @lc app=leetcode id=10 lang=cpp
 *
 * [10] Regular Expression Matching
 *
 * https://leetcode.com/problems/regular-expression-matching/description/
 *
 * algorithms
 * Hard (24.94%)
 * Total Accepted:    273.8K
 * Total Submissions: 1.1M
 * Testcase Example:  '"aa"\n"a"'
 *
 * Given an input string (s) and a pattern (p), implement regular expression
 * matching with support for '.' and '*'.
 * 
 * 
 * '.' Matches any single character.
 * '*' Matches zero or more of the preceding element.
 * 
 * 
 * The matching should cover the entire input string (not partial).
 * 
 * Note:
 * 
 * 
 * s could be empty and contains only lowercase letters a-z.
 * p could be empty and contains only lowercase letters a-z, and characters
 * like . or *.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input:
 * s = "aa"
 * p = "a"
 * Output: false
 * Explanation: "a" does not match the entire string "aa".
 * 
 * 
 * Example 2:
 * 
 * 
 * Input:
 * s = "aa"
 * p = "a*"
 * Output: true
 * Explanation: '*' means zero or more of the precedeng element, 'a'.
 * Therefore, by repeating 'a' once, it becomes "aa".
 * 
 * 
 * Example 3:
 * 
 * 
 * Input:
 * s = "ab"
 * p = ".*"
 * Output: true
 * Explanation: ".*" means "zero or more (*) of any character (.)".
 * 
 * 
 * Example 4:
 * 
 * 
 * Input:
 * s = "aab"
 * p = "c*a*b"
 * Output: true
 * Explanation: c can be repeated 0 times, a can be repeated 1 time. Therefore
 * it matches "aab".
 * 
 * 
 * Example 5:
 * 
 * 
 * Input:
 * s = "mississippi"
 * p = "mis*is*p*."
 * Output: false
 * 
 * 
 */

#include <string>

using std::string;

class Solution {
public:
    bool isMatch(string s, string p) {
        bool match[s.length() + 1][p.length() + 1];
        match[0][0] = true;
        for (int i = 1; i <= s.length(); i++) match[i][0] = false;
        for (int i = 1; i <= p.length(); i++) {
            match[0][i] = i > 1 && p[i - 1] == '*' && match[0][i - 2];
        }

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= p.length(); j++) {
                if (s[i - 1] == p[j - 1] || p[j - 1] == '.') match[i][j] = match[i - 1][j - 1];
                else if (p[j - 1] == '*') {
                    match[i][j] = match[i][j - 2] // skipping
                                    || match[i][j - 1] // single match 
                                    || ((s[i - 1] == p[j - 2] || p[j - 2] == '.') && match[i - 1][j]); // multiple matches
                } else match[i][j] = false;
            }
        }

        return match[s.length()][p.length()];
    }
};
