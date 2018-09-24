//
//  main.cpp
//  CountOfRangeSum_327
//
//  Created by Wenzhe Lu on 2018/3/17.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//Given an integer array nums, return the number of range sums that lie in [lower, upper] inclusive.
//Range sum S(i, j) is defined as the sum of the elements in nums between indices i and j (i ≤ j), inclusive.
//
//Note:
//A naive algorithm of O(n^2) is trivial. You MUST do better than that.
//
//Example:
//Given nums = [-2, 5, -1], lower = -2, upper = 2,
//Return 3.
//The three ranges are : [0, 0], [2, 2], [0, 2] and their respective sums are: -2, -1, 2.

#include <iostream>
#include <vector>

using namespace std;

class Solution {
public:
    // this problem don't require updating, so binary indexed tree or segment tree is
    // no use here.
    // one if the advantage of BIT or segment tree is that
    // it not only can query fast (which you can do that with a simple summing array)
    // you can also update fast (summing array can't give you that)
    // copy from solution
    // https://leetcode.com/problems/count-of-range-sum/discuss/77990/Share-my-solution
    
    // NOTE: haven't solved this problem yet!!
    int countRangeSum(vector<int>& nums, int lower, int upper) {
        int size=nums.size();
        if(size==0)  return 0;
        vector<long> sums(size+1, 0);
        for(int i=0; i<size; i++)  sums[i+1]=sums[i]+nums[i];
        return help(sums, 0, size+1, lower, upper);
    }
    
    /*** [start, end)  ***/
    int help(vector<long>& sums, int start, int end, int lower, int upper){
        /*** only-one-element, so the count-pair=0 ***/
        if(end-start<=1)  return 0;
        int mid=(start+end)/2;
        int count=help(sums, start, mid, lower, upper)
        + help(sums, mid, end, lower, upper);
        
        int m=mid, n=mid, t=mid, len=0;
        /*** cache stores the sorted-merged-2-list ***/
        /*** so we use the "len" to record the merged length ***/
        vector<long> cache(end-start, 0);
        for(int i=start, s=0; i<mid; i++, s++){
            /*** wrong code: while(m<end && sums[m++]-sums[i]<lower);  ***/
            while(m<end && sums[m]-sums[i]<lower) m++;
            while(n<end && sums[n]-sums[i]<=upper) n++;
            count+=n-m;
            /*** cache will merge-in-the-smaller-part-of-list2 ***/
            while(t<end && sums[t]<sums[i]) cache[s++]=sums[t++];
            cache[s]=sums[i];
            len=s;
        }
        
        for(int i=0; i<=len; i++)  sums[start+i]=cache[i];
        return count;
    }
};

int main(int argc, const char * argv[]) {
    Solution s;
    vector<int> v{1, 3, 1, -8, 6};
    s.countRangeSum(v, 1, 4);
    return 0;
}
