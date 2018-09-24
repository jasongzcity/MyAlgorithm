//
//  main.cpp
//  ReversePairs_493
//
//  Created by Wenzhe Lu on 2018/3/17.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//Given an array nums, we call (i, j) an important reverse pair if i < j and nums[i] > 2*nums[j].
//
//You need to return the number of important reverse pairs in the given array.
//
//Example1:
//Input: [1,3,2,3,1]
//Output: 2
//
//Example2:
//Input: [2,4,3,5,1]
//Output: 3
//
//Note:
//The length of the given array will not exceed 50,000.
//All the numbers in the input array are in the range of 32-bit integer.

#include <vector>

using namespace std;

// similar question:
// count of smaller number after self_315
// but now it makes the condition to nums[i] > 2 * nums[j]
// so simple counting while merging does not work..
// but counting then merging could do this, and will take only O(nlogn) time in total
// better solution???
class Solution {
public:
    // Using Binary indexed tree can avoid "counting then merging"
    // unaccepted: max can be long, too large!
//    int reversePairs2(vector<int>& nums) {
//        if (nums.size() == 0) return 0;
//        int min = INT_MAX;
//        long max = LONG_MIN;
//        for (int& i : nums) min = std::min(min, i);
//
//        // make them positive!
//        vector<long> copy(nums.size(), 0L);
//        for (int i = 0; i < nums.size(); i++) {
//            copy[i] = nums[i] - min + 1;
//            max = std::max(max, copy[i]);
//        }
//
//        int tree[max + 1];
//        memset(tree, 0, sizeof(int) * (max + 1));
//
//        int count = 0;
//        for (int i = nums.size() - 1; i > -1; i--) {
//            count += get(tree, (copy[i] - 1) / 2);
//            set(tree, copy[i], max);
//        }
//
//        return count;
//    }
    
    void merge(vector<int>& A, int start, int mid, int end)
    {
        int n1 = (mid - start + 1);
        int n2 = (end - mid);
        int L[n1], R[n2];
        for (int i = 0; i < n1; i++)
            L[i] = A[start + i];
        for (int j = 0; j < n2; j++)
            R[j] = A[mid + 1 + j];
        int i = 0, j = 0;
        for (int k = start; k <= end; k++) {
            if (j >= n2 || (i < n1 && L[i] <= R[j]))
                A[k] = L[i++];
            else
                A[k] = R[j++];
        }
    }
    
    int mergesort_and_count(vector<int>& A, int start, int end)
    {
        if (start < end) {
            int mid = (start + end) / 2;
            int count = mergesort_and_count(A, start, mid) + mergesort_and_count(A, mid + 1, end);
            int j = mid + 1;
            for (int i = start; i <= mid; i++) {
                while (j <= end && A[i] > A[j] * 2LL)
                    j++;
                count += j - (mid + 1);
            }
            merge(A, start, mid, end);
            return count;
        }
        else
            return 0;
    }
    
    int reversePairs(vector<int>& nums)
    {
        return mergesort_and_count(nums, 0, nums.size() - 1);
    }
};

int main(int argc, const char * argv[]) {
    return 0;
}
