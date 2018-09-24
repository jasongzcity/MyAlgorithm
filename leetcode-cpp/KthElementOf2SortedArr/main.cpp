//
//  main.cpp
//  KthElementOf2SortedArr
//
//  Created by Wenzhe Lu on 2018/1/25.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

#include <iostream>
#include <vector>

using namespace std;

class Solution {
public:
    // return the kth smallest number of two sorted array
    // in range a1[begin1, begin1 + len1 - 1], a2[begin2, begin2 + len2 - 1]
    int kth(vector<int>& a1, int begin1, int len1, vector<int>& a2, int begin2, int len2, int k) {
        // assume k always valid.
        // now deal with corner cases
        if (len2 == 0) return a1[begin1 + k - 1];
        if (len1 == 0) return a2[begin2 + k - 1];
        if (k == 1) return min(a1[begin1], a2[begin2]);
        
        // The big idea is that keep "cutting" the smaller half away
        int i = min(len1, k / 2), j = min(len2, k / 2); // avoid out of bound
        // now abandon k/2 elements
        if (a1[begin1 + i - 1] > a2[begin2 + j - 1]) {
            // abandon elements in a2
            return kth(a1, begin1, len1, a2, begin2 + j, len2 - j, k - j);
        } else {
            return kth(a1, begin1 + i, len1 - i, a2, begin2, len2, k - i);
        }
    }
    
    // another not so good solution
    // we can see its better to construct the mid point using k/2 directly.
    // In that way, we don't need to decide which part the kth element lies in.
    int kth(int *arr1, int *arr2, int *end1, int *end2, int k) {
        if (arr1 == end1)
            return arr2[k];
        if (arr2 == end2)
            return arr1[k];
        int mid1 = (end1 - arr1) / 2;
        int mid2 = (end2 - arr2) / 2;
        if (mid1 + mid2 < k)
        {
            if (arr1[mid1] > arr2[mid2])
                return kth(arr1, arr2 + mid2 + 1, end1, end2,
                           k - mid2 - 1);
            else
                return kth(arr1 + mid1 + 1, arr2, end1, end2,
                           k - mid1 - 1);
        }
        else
        {
            if (arr1[mid1] > arr2[mid2])
                return kth(arr1, arr2, arr1 + mid1, end2, k);
            else
                return kth(arr1, arr2, end1, arr2 + mid2, k);
        }
    }

};

int main(int argc, const char * argv[]) {
    vector<int> a1{1,2,6,7,8,14,88,188,1000}, a2{4,67,77,89,100,176,3333};
    Solution s;
    std::cout << s.kth(a1, 0, a1.size() - 1, a2, 0, a2.size() - 1, 15) << endl;
    return 0;
}
