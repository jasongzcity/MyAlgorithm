package FindAllNumberDisappearInAnArray_448;

import java.util.ArrayList;
import java.util.List;

/**
 * Given an array of integers where 1 ≤ a[i] ≤ n (n = size of array),
 * some elements appear twice and others appear once.
 *
 * Find all the elements of [1, n] inclusive that do not appear in this array.
 *
 * Could you do it without extra space and in O(n) runtime?
 * You may assume the returned list does not count as extra space.
 *
 * Example:
 * Input:
 * [4,3,2,7,8,2,3,1]
 * Output:
 * [5,6]
 */
public class Solution {
    // slow and stupid...
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> l = new ArrayList<>(nums.length);
        for(int i=0;i<nums.length;i++) l.add(0);
        for(int i:nums) l.set(i-1,i);
        for(int i=l.size()-1,j=i+1;i>-1;i--,j--){
            if(l.get(i)==0) l.set(i,j);
            else l.remove(i);
        }
        return l;
    }

    // two-pass fast solution
    public List<Integer> findDisappearedNumbers2(int[] nums){
        List<Integer> l = new ArrayList<>();
        for(int i=0;i<nums.length;i++){
            int tar = (nums[i]<0?-nums[i]:nums[i])-1;
            if(nums[tar]>0) nums[tar] = -nums[tar];
        }
        for(int i=0;i<nums.length;i++)
            if(nums[i]>0)
                l.add(i+1);
        return l;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.findDisappearedNumbers(new int[]{4,3,2,7,8,2,3,1}));
    }
}
