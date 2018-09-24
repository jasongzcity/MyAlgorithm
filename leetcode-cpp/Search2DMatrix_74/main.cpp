//
//  main.cpp
//  Search2DMatrix_74
//
//  Created by Wenzhe Lu on 2018/3/9.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//


//Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
//
//Integers in each row are sorted from left to right.
//The first integer of each row is greater than the last integer of the previous row.
//For example,
//
//Consider the following matrix:
//
//[
// [1,   3,  5,  7],
// [10, 11, 16, 20],
// [23, 30, 34, 50]
// ]
//Given target = 3, return true.


#include <iostream>
#include <vector>

using namespace std;

class Solution {
public:
    // With each row larger than the previous row,
    // we can regard the whole matrix as an array,
    // then do simple binary search
    // All need to do is row-column transforming
    bool searchMatrix(vector<vector<int>>& matrix, int target) {
        if (matrix.size() == 0) return false;
        int rows = matrix.size(), cols = matrix[0].size();
        int lo = 0, hi = rows * cols;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (matrix[mid / cols][mid % cols] >= target) hi = mid;
            else lo = mid + 1;
        }
        
        return lo != rows * cols && matrix[lo / cols][lo % cols] == target;
    }
};

int main(int argc, const char * argv[]) {
    // insert code here...
    std::cout << "Hello, World!\n";
    return 0;
}
