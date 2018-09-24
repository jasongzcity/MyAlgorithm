//
//  main.cpp
//  LongestSubstrWith2DistinctChar_159
//
//  Created by Wenzhe Lu on 2018/3/9.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//Given a string, find the length of the longest substring T that contains at most 2 distinct characters.
//
//For example, Given s = “eceba”,
//
//T is "ece" which its length is 3.
//

// classic sliding window problem

#include <iostream>
#include <string>

using namespace std;

class Solution {
public:
    int lengthOfLongestSubstringTwoDistinct(string s) {
        if (s.size() < 3) return s.size();
        int map[128];
        memset(map, 0, sizeof(int) * 128);
        int fast = 0, slow = 0, rs = 0, chars = 0;
        while (fast < s.size()) {
            if (++map[s[fast]] == 1) ++chars;
            if (chars > 2) {
                // we reach largest substring, update result
                rs = max(rs, fast - slow);
                while (chars > 2) {
                    if (--map[s[slow++]] == 0) --chars;
                }
            }
            ++fast;
        }
        rs = max(rs, fast - slow);
        
        return rs;
    }
};

int main(int argc, const char * argv[]) {
    return 0;
}
