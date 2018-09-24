//
//  main.cpp
//  SongList
//
//  Created by Wenzhe Lu on 2018/4/5.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

// Song List: problem from tencent
// Given x different class A songs with length in la, y different class B songs with length
// in lb. Without consideration the order of different songs, how many song orderings can we have to
// construct a song list with length in l?
// Note: the result can be very large, return the module of 1000000007

#include <cstdio>

const static int MOD = 1000000007;

// pick m from n
long long pick(int n, int m) {
    long long tm = 1;
    for (int i = 0; i < m; i++) tm *= n - i;
    for (int i = 1; i <= m; i++) tm /= i;
    return tm;
}

int songList_2(int la, int x, int lb, int y, int N) {
    int numa = N / la, rs = 0;
    for (int i = numa; i >= 0; i--) {
        if ((N - i * la) % lb == 0) {
            int numb = (N - i * la) / lb;
            long long tm = pick(x, i) * pick(y, numb);
            rs = (tm + rs) % MOD;
        }
    }
    
    return rs;
}

// suppose la < lb
int dfs(int acount, int bcount, int cur, int x, int y, int la, int lb, bool flag) {
    if (cur == 0) {
        // combination get
        long long tm = pick(x, acount) * pick(y, bcount);
//        printf("number of a: %d, number of b: %d, result: %d\n\n", acount, bcount, tm);
//        printf("picking acount: %d from x: %d = %d\n\n", acount, x, pick(x, acount));
//        printf("picking bcount: %d from y: %d = %d\n\n", bcount, y, pick(y, bcount));
        return tm % MOD;
    } else if (cur < 0) return 0;
    
    int sum = dfs(acount, bcount + 1, cur - lb, x, y, la, lb, false);
    if (flag) sum = (dfs(acount + 1, bcount, cur - la, x, y, la, lb, true) + sum) % MOD;
    
    return sum;
}

// suppose la < lb
int songList(int la, int x, int lb, int y, int N) {
    return dfs(0, 0, N, x, y, la, lb, true);
}

int main(int argc, const char * argv[]) {
    printf("%d\n", songList(2, 8, 3, 7, 12));
    printf("%d\n", songList_2(2, 8, 3, 7, 12));
    
    
    return 0;
}
