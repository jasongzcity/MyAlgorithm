//
//  main.cpp
//  CoinChange_322
//
//  Created by Wenzhe Lu on 2018/2/25.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//You are given coins of different denominations and a total amount of money amount.
//Write a function to compute the fewest number of coins that you need to make up that amount.
//If that amount of money cannot be made up by any combination of the coins, return -1.
//
//Example 1:
//coins = [1, 2, 5], amount = 11
//return 3 (11 = 5 + 5 + 1)
//
//Example 2:
//coins = [2], amount = 3
//return -1.
//
//Note:
//You may assume that you have an infinite number of each kind of coin. 

#include <vector>
#include <algorithm>
#include <cmath>
#include <functional>

using std::vector;

class Solution {
public:
    // we can figure out a quite straight forward dfs solution
    // but can we do DP?
    // dfs wrong!
    // case:
    // [186,419,83,408]
    // 6249
    // expected:20
    // output:26
    // should use dp
    int coinChangeII(vector<int>& coins, int amount) {
        std::sort(coins.begin(), coins.end());
        return dfs(coins.size() - 1, 0, coins, amount);
    }
    
    int dfs(int posi, int count, const vector<int>& coins, int target) {
        if (posi == -1) return -1;
        if (target % coins[posi] == 0) {
            return count + target / coins[posi];
        }
        for (int i = target / coins[posi]; i >= 0; i--) {
            int rs = dfs(posi - 1, count + i, coins, target - coins[posi] * i);
            if (rs > 0) return rs;
        }
        return -1;
    }
    
    // dp solution
    int coinChange(vector<int>& coins, int amount) {
        if (coins.size() == 0) return 0;
        vector<int> dp(amount + 1, amount + 1);
        dp[0] = 0;
        std::sort(coins.begin(), coins.end());
        for (int i = coins[0]; i <= amount; i++) {
            for (const int& coin : coins){
                if (i - coin >= 0 && dp[i - coin] + 1 < dp[i]) {
                    dp[i] = dp[i - coin] + 1;
                }
            }
        }
        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }
    
    // faster dfs
    // the above dfs is wrong is that I haven't consider all the possibilities
    int coinChangeIII(vector<int>& coins, int amount) {
        if (coins.size() == 0 || amount == 0) return 0;
        int count = amount + 1;
        std::sort(coins.begin(), coins.end(), std::greater<int>());
        dfs(coins, 0, amount, count, 0);
        return count == amount + 1 ? -1 : count;
    }
    
    // why we are iterating from larger to smaller?
    // so that it's easier to find out a "dead end" and return early
    void dfs(const vector<int>& coins, const int& posi, const int& target, int& count, const int& currentCount) {
        if (target < 0 || currentCount + ceil((double) target / coins[posi]) >= count) return; // best effort could be
        if (target == 0) {
            count = std::min(count, currentCount);
            return;
        }
        for (int i = posi; i < coins.size(); i++) {
            dfs(coins, i, target - coins[i], count, currentCount + 1);
        }
    }
    
    // follow up:
    // find the makeup of the coins
    // use a static list and a dynamic list.
    // static changes with the dynamic list.
};

int main(int argc, const char * argv[]) {

    return 0;
}
