//
//  main.cpp
//  ExpressionAddOperators_282
//
//  Created by Wenzhe Lu on 2018/2/24.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//


//Given a string that contains only digits 0-9 and a target value,
//return all possibilities to add binary operators (not unary) +, -, or * between
//the digits so they evaluate to the target value.
//
//Examples:
//
//"123", 6 -> ["1+2+3", "1*2*3"]
//"232", 8 -> ["2*3+2", "2+3*2"]
//"105", 5 -> ["1*0+5","10-5"]
//"00", 0 -> ["0+0", "0-0", "0*0"]
//"3456237490", 9191 -> []

#include <iostream>
#include <string>
#include <vector>
#include <unordered_set>

using std::string;
using std::vector;
using std::stoi;
using std::unordered_set;

class Solution {
public:
    // dp solution
    // dp[i][j] = dp[i][k] */+/- dp[k+1][j] (for k from i to j - 1)
    // need declaration, how to deal with paratheses?
    // hint says use dfs
    // this solution is actually listing all possibility
    // buggy, too slow
    vector<string> addOperatorsII(string num, int target) {
        string::size_type len = num.size();

        vector<vector<vector<string>>> ma(len, vector<vector<string>>(len, vector<string>()));
        vector<vector<vector<int>>> vals(len, vector<vector<int>>(len, vector<int>()));
        vector<vector<vector<bool>>> needParas(len, vector<vector<bool>>(len, vector<bool>()));
        
        for (int ln = 1; ln <= len; ln++) {
            for (int begin = 0; begin + ln <= len; begin++) {
                // add whole string first;
                string whole = num.substr(begin, ln);
                if (whole[0] != '0' || ln == 1) {
                    vals[begin][begin + ln - 1].push_back(stoi(whole));
                    ma[begin][begin + ln - 1].push_back(std::move(whole));
                    needParas[begin][begin + ln - 1].push_back(false);
                }
                for (int k = 1; k < ln; k++) {
                    for (int i = 0; i < vals[begin][begin + k - 1].size(); i++){
                        for (int j = 0; j < vals[begin + k][begin + ln - 1].size(); j++){
                            vals[begin][begin + ln - 1].push_back(vals[begin][begin + k - 1][i] + vals[begin + k][begin + ln - 1][j]);
                            ma[begin][begin + ln - 1].push_back(ma[begin][begin + k - 1][i] + "+" + ma[begin + k][begin + ln - 1][j]);
                            needParas[begin][begin + ln - 1].push_back(true);
                            
                            vals[begin][begin + ln - 1].push_back(vals[begin][begin + k - 1][i] - vals[begin + k][begin + ln - 1][j]);
                            ma[begin][begin + ln - 1].push_back(ma[begin][begin + k - 1][i] + "-" + ma[begin + k][begin + ln - 1][j]);
                            needParas[begin][begin + ln - 1].push_back(true);
                            
                            if (!needParas[begin][begin + k - 1][i] && !needParas[begin + k][begin + ln - 1][j]) {
                                vals[begin][begin + ln - 1].push_back(vals[begin][begin + k - 1][i] * vals[begin + k][begin + ln - 1][j]);
                                ma[begin][begin + ln - 1].push_back(ma[begin][begin + k - 1][i] + "*" + ma[begin + k][begin + ln - 1][j]);
                                needParas[begin][begin + ln - 1].push_back(false);
                            }
                        }
                    }
                }
            }
        }
        
        unordered_set<string> set;
        vector<string> rs;
        for (int i = 0; i < ma[0][len - 1].size(); i++) {
            if (vals[0][len - 1][i] == target) {
                set.emplace(std::move(ma[0][len - 1][i]));
            }
        }
        for (auto& str : set) {
            rs.emplace_back(std::move(str));
        }
        return rs;
    }
    
    // use DFS
    // note: not considering paratheses circumstances
    // very brute-force diving
    vector<string> addOperators(string num, int target) {
        vector<string> rs;
        dfs(num, 0, "", rs, 0, 0, target);
        return rs;
    }
    
    void dfs(const string& num, size_t cur, const string& str, vector<string>& rs, long curVal, long multi, const int& target) {
        if (cur == num.size()) {
            if (curVal == target) rs.push_back(str);
            return;
        }
        
        for (size_t i = cur; i < num.size(); i++) {
            if (num[cur] == '0' && i != cur) break;
            string tm = num.substr(cur, i - cur + 1);
            long tmVal = std::stol(tm);
            if (cur == 0) {
                dfs(num, i + 1, tm, rs, tmVal, tmVal, target);
            } else {
                dfs(num, i + 1, str + "+" + tm, rs, curVal + tmVal, tmVal, target);
                dfs(num, i + 1, str + "-" + tm, rs, curVal - tmVal, -tmVal, target);
                dfs(num, i + 1, str + "*" + tm, rs, curVal + tmVal * multi - multi, tmVal * multi, target);
            }
        }
    }
};

int main(int argc, const char * argv[]) {
    Solution s;
    for (auto& str : s.addOperators("3456237490", 9191))
        std::cout << str << std::endl;
    return 0;
}
