//
//  main.cpp
//  PerfectSquares_279
//
//  Created by Wenzhe Lu on 2018/3/10.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//Given a positive integer n, find the least number of perfect square numbers (for example, 1, 4, 9, 16, ...)
//which sum to n.
//
//For example, given n = 12, return 3 because 12 = 4 + 4 + 4; given n = 13, return 2 because 13 = 4 + 9.

#include <iostream>
#include <cmath>
#include <queue>

using std::queue;

class Solution {
public:
    // thought : dp
    // this is the most intuitive solution
    // must have better ways
    int numSquares2(int n) {
        int dp[n + 1];
        for (int i = 0; i <= n; i++) dp[i] = i;
        
        for (int i = 4; i <= n; i++) {
            for (int j = 1; i - j * j >= 0; j++) {
                if (dp[i - j * j] + 1 < dp[i]) {
                    dp[i] = dp[i - j * j] + 1;
                }
            }
        }
        
        return dp[n];
    }
    
    // in some way this problem looks like coin change
    // try dfs
    // except that now the "changes" are fixed 1, 4, 9, 16 ....
    // how to terminate early in DFS is important to effiency of the program
    int numSquares(int n) {
        int count = n;
        dfs(n, count, 0);
        return count;
    }
    
    void dfs(const int& cur, int& count, const int& currentCount) {
        if (cur == 0) {
            if (currentCount < count) count = currentCount;
            return;
        }
        
        if (currentCount + 1 >= count) return;
        
        for (int i = (int) pow(cur, 0.5); i > 0; i--) {
            dfs(cur - i * i, count, currentCount + 1);
        }
    }
    
    // bfs
    // since "steps" in this solution is also "counts"
    // so as soon as we hit 0 we got the answer
    int numSquaresBFS(int n) {
        int rs = 0;
        queue<int> q;
        q.emplace(n);
        while (!q.empty()) {
            int size = q.size();
            ++rs;
            for (int i = 0; i < size; i++) {
                int cur = q.front();
                q.pop();
                for (int j = pow(cur, 0.5); j > 0; j--) {
                    if (cur - j * j == 0) return rs;
                    q.emplace(cur - j * j);
                }
            }
        }
        
        return rs;
    }
    
    // NOT CORRECT
    // think of 12
    int numSquares3(int n) {
        int count = 0;
        while (n > 0) {
            int tm = (int) pow(n, 0.5);
            n -= tm * tm;
            ++count;
        }
        return count;
    }
};

int main(int argc, const char * argv[]) {
    
//    std::cout << pow(5, 0.5) << " " << pow(4, 0.5) << " " << (int) pow(6, 0.5) << std::endl;
    Solution s;
//    std::cout << s.numSquares(49) << std::endl;
//    std::cout << s.numSquares(2) << std::endl;
//    std::cout << s.numSquares(3) << std::endl;
//    std::cout << s.numSquares(1) << std::endl;
//    std::cout << s.numSquares(18) << std::endl;
    using std::cout;
    using std::endl;
//    cout << !!4 << " " << !3 << endl;
    std::cout << s.numSquaresBFS(12) << std::endl;
    std::cout << s.numSquaresBFS(13) << std::endl;
    return 0;
}
