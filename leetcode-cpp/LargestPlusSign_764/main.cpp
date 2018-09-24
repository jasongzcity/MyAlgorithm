//
//  main.cpp
//  LargestPlusSign_764
//
//  Created by Wenzhe Lu on 2018/2/7.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//


//In a 2D grid from (0, 0) to (N-1, N-1), every cell contains a 1,
//except those cells in the given list mines which are 0.
//What is the largest axis-aligned plus sign of 1s contained in the grid?
//Return the order of the plus sign. If there is none, return 0.
//
//An "axis-aligned plus sign of 1s of order k" has some center grid[x][y] = 1
//along with 4 arms of length k-1 going up, down, left, and right, and made of 1s.
//This is demonstrated in the diagrams below.
//Note that there could be 0s or 1s beyond the arms of the plus sign,
//only the relevant area of the plus sign is checked for 1s.
//
//Examples of Axis-Aligned Plus Signs of Order k:
//
//Order 1:
//000
//010
//000
//
//Order 2:
//00000
//00100
//01110
//00100
//00000
//
//Order 3:
//0000000
//0001000
//0001000
//0111110
//0001000
//0001000
//0000000
//
//Example 1:
//
//Input: N = 5, mines = [[4, 2]]
//Output: 2
//Explanation:
//11111
//11111
//11111
//11111
//11011
//In the above grid, the largest plus sign can only be order 2.  One of them is marked in bold.
//
//Example 2:
//
//Input: N = 2, mines = []
//Output: 1
//Explanation:
//There is no plus sign of order 2, but there is of order 1.
//
//Example 3:
//
//Input: N = 1, mines = [[0, 0]]
//Output: 0
//Explanation:
//There is no plus sign, so return 0.
//
//Note:
//
//N will be an integer in the range [1, 500].
//mines will have length at most 5000.
//mines[i] will be length 2 and consist of integers in the range [0, N-1].
//(Additionally, programs submitted in C, C++, or C# will be judged with a slightly smaller time limit.)

#include <iostream>
#include <vector>

using std::vector;
using std::min;
using std::max;

// good example of matrix manipulation
class Solution {
public:
    // thought: use binary search to approach the result
    // but we have to figuar out a way to validate certain position in a fast way.
    // iterate the mines, putting restrictions in the matrix
    int orderOfLargestPlusSign(int N, vector<vector<int>>& mines) {
        vector<vector<int>> m(N, vector<int>(N, N));
        int maximum = (N + 1) / 2, rs = 0;
        for (auto& v : mines) {
            reach(m, maximum, v[0], v[1], N);
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                rs = max(rs, min(m[i][j], min(i + 1, min(j + 1, min(N - i, N - j)))));
            }
        }
        return rs;
    }
    
    // reach out to 4 directions, update their maximum possibility.
    void reach(vector<vector<int>>& m, int max, int row, int col, int N) {
        m[row][col] = 0;
        for (int i = 1; i < max; i++) {
            if (row - i >= 0) m[row - i][col] = min(m[row - i][col], i);
            if (row + i < N) m[row + i][col] = min(m[row + i][col], i);
            if (col - i >= 0) m[row][col - i] = min(m[row][col - i], i);
            if (col + i < N) m[row][col + i] = min(m[row][col + i], i);
        }
    }
};

int main(int argc, const char * argv[]) {

    return 0;
}
