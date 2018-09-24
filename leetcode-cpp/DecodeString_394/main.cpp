//
//  main.cpp
//  DecodeString_394
//
//  Created by Wenzhe Lu on 2018/3/12.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//Given an encoded string, return it's decoded string.
//
//The encoding rule is: k[encoded_string], where the encoded_string inside
//the square brackets is being repeated exactly k times. Note that k is guaranteed to be a positive integer.
//
//You may assume that the input string is always valid;
//No extra white spaces, square brackets are well-formed, etc.
//
//Furthermore, you may assume that the original data does not contain any digits
//and that digits are only for those repeat numbers, k. For example, there won't be input like 3a or 2[4].
//
//Examples:
//
//s = "3[a]2[bc]", return "aaabcbc".
//s = "3[a2[c]]", return "accaccacc".
//s = "2[abc]3[cd]ef", return "abcabccdcdcdef".

#include <iostream>
#include <string>
#include <stack>

using namespace std;

class Solution {
public:
    string decodeString2(string s) {
        string origin;
        int pt = 0;
        dfs(origin, s, pt, 1);
        return origin;
    }
    
    void dfs(string& origin, string& s, int& cur, int times) {
        string tm;
        while (cur < s.size() && s[cur] != ']') {
            if (s[cur] <= '0' || s[cur] > '9') {
                tm += s[cur++];
            } else {
                // append the number of times of the substring
                int numbegin = cur;
                while (s[cur++] != '[') ;
                int times = stoi(s.substr(numbegin, cur - numbegin - 1));
                dfs(tm, s, cur, times);  // skip '['
            }
        }
        cur++;  // skip ']'
        while (times-- > 0) {
            origin += tm;
        }
    }
    
    // the crux of this problem is about backtracking
    // appending current string to the previous string
    // so we can do stack instead of using recursion
    string decodeString(string s) {
        stack<string> stk;
        stack<int> numstk;
        stk.push(string());
        int cur = 0;
        while (cur != s.size()) {
            if (isdigit(s[cur])) {
                int time = 0;
                do {
                    time = time * 10 + s[cur++] - '0';
                } while (isdigit(s[cur]));
                cur++;
                stk.push(string());
                numstk.push(time);
            } else if (s[cur] == ']') {
                cur++;
                int time = numstk.top();
                numstk.pop();
                string tm = stk.top();
                stk.pop();
                while (time-- > 0) {
                    stk.top() += tm;
                }
            } else {
                // simple append
                stk.top() += s[cur++];
            }
        }
        return stk.top();
    }
};

int main(int argc, const char * argv[]) {
    Solution s;
    cout << s.decodeString("2[abc]3[cd]ef") << endl;
    cout << s.decodeString("3[a2[c]]") << endl;
    cout << s.decodeString("3[a]2[bc]") << endl;
    cout << s.decodeString("10[a2[c]]") << endl;
    return 0;
}
