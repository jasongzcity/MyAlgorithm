//
//  main.cpp
//  LongestSubstrWithoutRepeatedChar_3
//
//  Created by Wenzhe Lu on 2018/3/6.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//Given a string, find the length of the longest substring without repeating characters.
//
//Examples:
//
//Given "abcabcbb", the answer is "abc", which the length is 3.
//
//Given "bbbbb", the answer is "b", with the length of 1.
//
//Given "pwwkew", the answer is "wke", with the length of 3.
//Note that the answer must be a substring, "pwke" is a subsequence and not a substring.

#include <iostream>
#include <string>

using namespace std;

class Solution {
public:
    // sliding window problem??
    int lengthOfLongestSubstring(string s) {
        if (s.size() < 2) return (int) s.size();
        bool map[128];
        int rs = 0, left = 0, right = 1;
        memset(map, false, sizeof(bool) * 128);
        map[s[0]] = true;
        for (; right < s.size(); right++) {
            if (map[s[right]]) {
                // we have a target substring, get length
                rs = max(rs, right - left);
                while (s[left] != s[right]) map[s[left++]] = false;
                ++left;
            }
            map[s[right]] = true;
        }
        // now right == s.size(), update the rs last time
        rs = max(rs, right - left);
        
        return rs;
    }
    
    // notice that there is even better solution
    // instead of scanning left pointer linearly, we can use the map to remember the positions
    // of the characters, so that the left pointer can jump to required positions
};

int main(int argc, const char * argv[]) {
    Solution s;
    std::cout << s.lengthOfLongestSubstring("bbbbb") << endl;
    return 0;
}
