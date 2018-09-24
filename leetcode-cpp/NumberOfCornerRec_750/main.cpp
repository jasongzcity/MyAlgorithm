//
//  main.cpp
//  NumberOfCornerRec_750
//
//  Created by Wenzhe Lu on 2018/2/24.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//Given a grid where each entry is only 0 or 1, find the number of corner rectangles.
//
//A corner rectangle is 4 distinct 1s on the grid that form an axis-aligned rectangle.
//Note that only the corners need to have the value 1. Also, all four 1s used must be distinct.
//
//
//
//Example 1:
//
//Input: grid =
//[[1, 0, 0, 1, 0],
// [0, 0, 1, 0, 1],
// [0, 0, 0, 1, 0],
// [1, 0, 1, 0, 1]]
//Output: 1
//Explanation: There is only one corner rectangle, with corners grid[1][2], grid[1][4], grid[3][2], grid[3][4].
//
//
//
//Example 2:
//
//Input: grid =
//[[1, 1, 1],
// [1, 1, 1],
// [1, 1, 1]]
//Output: 9
//Explanation: There are four 2x2 rectangles, four 2x3 and 3x2 rectangles, and one 3x3 rectangle.
//
//
//
//Example 3:
//
//Input: grid =
//[[1, 1, 1, 1]]
//Output: 0
//Explanation: Rectangles must have four distinct corners.
//
//
//
//Note:
//
//The number of rows and columns of grid will each be in the range [1, 200].
//Each grid[i][j] will be either 0 or 1.
//The number of 1s in the grid will be at most 6000.

#include <vector>
#include <iostream>

using std::vector;

class Solution {
public:
    // intuitive thought:
    // use each element as the leftupmost corner of the rectangle
    // and search accordingly.
    // O(N^3)
    int countCornerRectangles(vector<vector<int>>& grid) {
        int rs = 0;
        if (grid.size() == 0) return 0;
        for (int i = 0; i + 1 < grid.size(); i++) {
            for (int j = 0; j + 1 < grid[0].size(); j++) {
                if (grid[i][j] == 1) {
                    for (int k = j + 1; k < grid[0].size(); k++) {
                        if (grid[i][k] == 1) {
                            for (int l = i + 1; l < grid.size(); l++) {
                                if (grid[l][j] == 1 && grid[l][k] == 1) {
                                    rs++;
                                }
                            }
                        }
                    }
                }
            }
        }

        return rs;
    }
    
    // search if target in the vector
    // assume vector is sorted.
    bool binsearch(const vector<int>& vec, const int& target) {
        int lo = 0, hi = vec.size();
        while (lo < hi) {
            int mid = (lo + hi) >> 1;
            if (vec[mid] < target) lo = mid + 1;
            else hi = mid;
        }
        return lo == vec.size() ? false : vec[lo] == target;
    }
    
    // use a matrix to store '1's positions, and
    // find them in pairs later.
    // lesson learned : size_type is unsigned.
    // so m[x].size() - 1 will be very large if m[x].size() = 0
    // this not work! too slow.
    int countCornerRectanglesII(vector<vector<int>>& grid) {
        if (grid.size() == 0) return 0;
        vector<vector<int>> m;
        for (int i = 0; i < grid.size(); i++) {
            vector<int> tm;
            for (int j = 0; j < grid[0].size(); j++) {
                if (grid[i][j] == 1) {
                    tm.push_back(j);
                }
            }
            m.push_back(std::move(tm));
        }
        int rs = 0;

        for (int i = 0; i + 1 < grid.size(); i++) {
            for (int j = 0; j + 1 < m[i].size(); j++) {
                for (int k = j + 1; k < m[i].size(); k++) {
                    for (int l = i + 1; l < grid.size(); l++) {
                        if (binsearch(m[l], m[i][j]) && binsearch(m[l], m[i][k])) {
                            rs++;
                        }
                    }
                }
            }
        }
        return rs;
    }
    
    // smarter solution indeed!
    int countCornerRectanglesIII(vector<vector<int>>& grid) {
        int rs = 0;
        for (int i = 0; i + 1 < grid.size(); i++) {
            for (int j = i + 1; j < grid.size(); ++j) {
                int counter = 0;
                for (int k = 0; k < grid[0].size(); ++k) {
                    if (grid[i][k] == 1 && grid[j][k] == 1) {
                        ++counter;
                    }
                }
                rs += counter * (counter - 1) / 2;
            }
        }
        return rs;
    }
};

int main(int argc, const char * argv[]) {
    vector<vector<int>> grid;
    
    grid.push_back(vector<int>{1, 0, 1, 0});
    grid.push_back(vector<int>{0, 0, 0, 0});
    grid.push_back(vector<int>{0, 0, 1, 1});
    Solution s;
    
    std::cout << s.countCornerRectangles(grid) << std::endl;
    return 0;
}
