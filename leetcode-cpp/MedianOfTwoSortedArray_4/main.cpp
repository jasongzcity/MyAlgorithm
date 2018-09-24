//
//  main.cpp
//  MedianOfTwoSortedArray_4
//
//  Created by Wenzhe Lu on 2018/1/26.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

#include <iostream>
#include <vector>

using namespace std;

class Solution {
public:
    // the "old" method for this problem is to maintain the
    // "balance" for the smaller half and the larger half
    // draw a graph to better understand this
    // lesson taken: don't search on longer array, too much corner cases.
    // Running time is O(log(min(m,n)))
    double findMedianSortedArrays(vector<int>& nums1, vector<int>& nums2) {
        int sum = nums1.size() + nums2.size(), half = (sum + 1) / 2;
        vector<int> *longer = &nums1, *shorter = &nums1;
        if (nums1.size() > nums2.size()) shorter = &nums2;
        else longer = &nums2;
        int lo = 0, hi = shorter -> size(), smid, lmid;
        while (1) {
            smid = (lo + hi) / 2;
            lmid = half - smid;
            if (smid < shorter -> size() && (*longer)[lmid - 1] > (*shorter)[smid]) {
                // expand the shorter smaller half
                lo = smid + 1;
            } else if (smid > 0 && (*shorter)[smid - 1] > (*longer)[lmid]) {
                // shrink the shorter smaller half
                hi = smid - 1;
            } else {
                // now smaller half and larger half stable
                int maxLeft, minRight;
                if (smid == 0) maxLeft = (*longer)[lmid - 1];
                else if (lmid == 0) maxLeft = (*shorter)[smid - 1];
                else maxLeft = max((*longer)[lmid - 1], (*shorter)[smid - 1]);
                
                if ((sum & 1) == 1) return maxLeft;
                
                if (smid == shorter -> size()) minRight = (*longer)[lmid];
                else if (lmid == longer -> size()) minRight = (*shorter)[smid];
                else minRight = min((*longer)[lmid], (*shorter)[smid]);
                
                return (double) (maxLeft + minRight) / 2;
            }
        }
    }
    
    // use the method in "kth element in two sorted arrays"
    // now this method is O(logk) where k = (m+n)/2 in this case
    // the problem of this problem is that there are way tooooooo many
    // corner cases, and they also mixed together.
    double findMedianSortedArraysII(vector<int>& nums1, vector<int>& nums2) {
        int totalLen = nums1.size() + nums2.size(), mid = totalLen / 2 + 1;
        for (int i : nums1) {
            
        }
        bool odd = (totalLen & 1) == 1;
        if (nums2.size() == 0) {
            if (odd) return nums1[mid - 1];
            else return (double) (nums1[mid - 1] + nums1[mid - 2]) / 2;
        } else if (nums1.size() == 0) {
            if (odd) return nums2[mid - 1];
            else return (double) (nums2[mid - 2] + nums2[mid - 1]) / 2;
        }
        return kth(nums1, 0, nums1.size() - 1, nums2, 0, nums2.size() - 1,
                   mid, odd);
    }
    
    // begin and end inclusive
    double kth(vector<int>& nums1, int begin1, int end1, vector<int>& nums2, int begin2, int end2, int k, bool odd) {
        // deal with corner cases
        // suppose k is always valid
        if (begin1 > end1) {
            if (odd) return nums2[begin2 + k - 1];
            else if (k > 1) return (double) (nums2[begin2 + k - 1] + max(nums2[begin2 + k - 2], nums1[end1])) / 2;
        } else if (begin2 > end2) {
            if (odd) return nums1[begin1 + k - 1];
            else if (k > 1) return (double) (nums1[begin1 + k - 1] + max(nums1[begin1 + k - 2], nums2[end2])) / 2;
        }
        if (k == 1) {
            int minRight, maxLeft;
            if (begin1 == nums1.size()) minRight = nums2[begin2];
            else if (begin2 == nums2.size()) minRight = nums1[begin1];
            else minRight = min(nums1[begin1], nums2[begin2]);

            if (odd) return minRight;
            if (begin1 == 0) maxLeft = nums2[begin2 - 1];
            else if (begin2 == 0) maxLeft = nums1[begin1 - 1];
            else maxLeft = max(nums2[begin2 - 1], nums1[begin1 - 1]);
            return (double) (maxLeft + minRight) / 2;
        }
        
        int i = min(end1 - begin1 + 1, k / 2), j = min(end2 - begin2 + 1, k / 2);
        if (nums1[begin1 + i - 1] > nums2[begin2 + j - 1]) {
            // abandon nums2 part
            return kth(nums1, begin1, end1, nums2, begin2 + j, end2, k - j, odd);
        } else {
            // abandon num1 part
            return kth(nums1, begin1 + i, end1, nums2, begin2, end2, k - i, odd);
        }
    }
    
    static int f(int k) {
        return 0;
    }
};

int main(int argc, const char * argv[]) {
    vector<int> a{1,3,4,77,88,89,134,156,222}, b{2,4,6,55,66,87,133}, c{1,3}, d{2,4,5,6};
    Solution s;
    string st("s");
    
    std::cout << s.findMedianSortedArraysII(c, d) << endl;
    cout << Solution::f(3) << endl;
    

    return 0;
}
