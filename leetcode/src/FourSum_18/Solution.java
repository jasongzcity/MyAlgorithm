package FourSum_18;

import java.util.*;

/**
 * Given an array S of n integers, are there elements a, b, c,
 * and d in S such that a + b + c + d = target?
 * Find all unique quadruplets in the array which gives the sum of target.
 */
public class Solution
{
    /*
     * The nSumForTarget solution is inspired by the optimal
     * solution on leetcode.
     * And I rewrite it in a recursive way, which is easier to
     * expand to "nSum" problem.
     * Time complexity: O(n^(required number-1))
     * For example: 6Sum cost O(n^5)
     */

    /*
     * Find n numbers in the integer array in range lo to hi(both inclusive)
     * and sum up in target.
     * Return these combinations in the rs list.
     * pass the element in the list and use eleList to storage prior integer
     * LinkedList as the rs list is recommended for faster insertion.
     */
    public static void nSumForTarget(int[] nums,int target,List<List<Integer>> rs,
                                        int lo,int hi,Integer[] eleList,int sumNum)
    {
        // Assume the array is already sorted.
        if(sumNum==2) // O(n) search in the array
        {
            for(int i=lo,j=hi;i<j;)
            {
                if(nums[i]+nums[j]<target)
                {
                    i++;
                    continue;
                }
                if(nums[i]+nums[j]>target)
                {
                    j--;
                    continue;
                }
                // combination got!
                List<Integer> list = new ArrayList<>();
                list.add(nums[i]);
                list.add(nums[j]);
                if(eleList!=null) // it is null when 2Sum
                    for(Integer e : eleList)
                        list.add(e);
                rs.add(list);
                while(i<j&&nums[i+1]==nums[i]) i++; // skip to the last duplicate
                while(i<j&&nums[j-1]==nums[j]) j--;
                i++;
                j--;
            }
        }
        else    // downgrade to 2Sum recursively
        {
            for(int i=lo;i<=hi-sumNum+1;i++)
            {
                if(i!=lo&&nums[i]==nums[i-1]) continue;
                if(nums[i]+nums[hi]*(sumNum-1)<target) continue;    // i too small
                if(nums[i]*sumNum>target) break;                    // i too large
                if(nums[i]*sumNum==target&&nums[i]!=nums[i+sumNum-1]) break;
                eleList[sumNum-3] = nums[i];
                nSumForTarget(nums,target-nums[i],rs,i+1,hi,eleList,sumNum-1);
            }
        }
    }

    public static List<List<Integer>> fourSum2(int[] nums, int target)
    {
        List<List<Integer>> rs = new LinkedList<>();
        if(nums==null||nums.length<4) return rs;
        Arrays.sort(nums);
        Integer[] space = new Integer[2];//4-2
        nSumForTarget(nums,target,rs,0,nums.length-1,space,4);
        return rs;
    }

    public static void main(String[] args)
    {
        int[] nums = new int[]{1,0,-1,0,-2,2};
//        Integer[] temp = new Integer[10];
//        temp[3] = 10;
//        temp[8] = 1000;
//        for(Integer e:temp)
//            System.out.println(e);
        List<List<Integer>> list = fourSum2(nums,0);
        System.out.println(list);
    }

    // below are the brute-force solution written in C++
    // the author writes: The key idea is to downgrade
    // the problem to a 2Sum problem eventually.
    // And the same algorithm can be expand to NSum problem.

//    vector<vector<int> > fourSum(vector<int> &num, int target) {
//
//        vector<vector<int> > res;
//
//        if (num.empty())
//            return res;
//
//        std::sort(num.begin(),num.end());
//
//        for (int i = 0; i < num.size(); i++) {
//
//            int target_3 = target - num[i];
//
//            for (int j = i + 1; j < num.size(); j++) {
//
//                int target_2 = target_3 - num[j];
//
//                int front = j + 1;
//                int back = num.size() - 1;
//
//                while(front < back) {
//
//                    int two_sum = num[front] + num[back];
//
//                    if (two_sum < target_2) front++;
//
//                    else if (two_sum > target_2) back--;
//
//                    else {
//
//                        vector<int> quadruplet(4, 0);
//                        quadruplet[0] = num[i];
//                        quadruplet[1] = num[j];
//                        quadruplet[2] = num[front];
//                        quadruplet[3] = num[back];
//                        res.push_back(quadruplet);
//
//                        // Processing the duplicates of number 3
//                        while (front < back && num[front] == quadruplet[2]) ++front;
//
//                        // Processing the duplicates of number 4
//                        while (front < back && num[back] == quadruplet[3]) --back;
//
//                    }
//                }
//
//                // Processing the duplicates of number 2
//                while(j + 1 < num.size() && num[j + 1] == num[j]) ++j;
//            }
//
//            // Processing the duplicates of number 1
//            while (i + 1 < num.size() && num[i + 1] == num[i]) ++i;
//
//        }
//
//        return res;
//
//    }
}
