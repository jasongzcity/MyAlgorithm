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
//        int[] nums = new int[]{-1,2,2,-5,0,-1,4};
//
        Solution s = new Solution();
//        List<List<Integer>> list = s.fourSum(nums,3);
//        System.out.println(list);

        int[] nums2 = new int[]{-1,-1,-1,0,0,0,0,0,0,1,1,1,1,2,2,2,2,3,3,4,4,4,4};

        System.out.println(s.twoSumDup(nums2,1));
    }

    // Second session
    // Do the KSum again
    // first we assume the array is sorted
    // and we assume we need to get rid of duplicate elements.
    // thoughts: hardest part is to notice dealing duplicates
    private void dfs(int[] a, int tar, List<Integer> prevAdded, int k,
                     int begin, int end, List<List<Integer>> rs){
        if(k==2){
            // two sum problem
            int i = begin, j = end;
            while(i < j) {
                // avoid duplicate
                if(i > begin && a[i] == a[i-1]){
                    ++i;
                    continue;
                }
                if(j < end && a[j] == a[j+1]){
                    --j;
                    continue;
                }
                if (a[i] + a[j] > tar) --j;
                else if (a[i] + a[j] < tar) ++i;
                else {
                    List<Integer> l = new ArrayList<>(prevAdded);
                    l.add(a[i++]); // move two pointer to search next pair
                    l.add(a[j--]);
                    rs.add(l);
                }
            }
        }else{
            for(int i=begin;i<=end-k+1&&a[i]*k<=tar;i++){
                if(i>begin&&a[i]==a[i-1]) continue;
                prevAdded.add(a[i]);
                dfs(a,tar-a[i],prevAdded,k-1,i+1,end,rs);
                prevAdded.remove(prevAdded.size()-1);
            }
        }
    }

    public List<List<Integer>> fourSum(int[] nums, int target){
        List<Integer> tm = new ArrayList<>(2); //4-2
        List<List<Integer>> rs = new ArrayList<>();
        Arrays.sort(nums);
        dfs(nums,target,tm,4,0,nums.length-1,rs);
        return rs;
    }

    // given an array, find out all the combinations sum up to target.
    // no duplicate combinations.
    public List<List<Integer>> twoSumDup(int[] a, int target){
        List<List<Integer>> rs = new ArrayList<>();
        int i = 0,j = a.length-1;
        while(i<j){
            if(i > 0 && a[i]==a[i-1]){
                ++i;
                continue;
            }
            if (j<a.length-1&&a[j]==a[j+1]) {
                --j;
                continue;
            }
            if(a[i]+a[j]>target) --j;
            else if(a[i]+a[j]<target) ++i;
            else{
                List<Integer> l = new ArrayList<>(2);
                l.add(a[i++]);
                l.add(a[j--]);
                rs.add(l);
            }
        }
        return rs;
    }
}
