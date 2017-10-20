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
    // second session:
    // first thought: sort it then find those "holes"
    // do it in O(n) time?
    // a valuable information we didn't use:
    // the integer has been given range and it's exactly in the array size
    // this is not accepted....
    public List<Integer> findDisappearedNumbers3(int[] nums){
        for(int i=0;i<nums.length;i++)
            nums[nums[i]-1] = nums[i];

        List<Integer> l = new ArrayList<>(nums.length);
        for(int i=0;i<nums.length;i++)
            if(nums[i]!=i+1)
                l.add(i+1);
        return l;
    }

    // key point is that can we find a way to mark the index has
    // corresponding number?
    public List<Integer> findDisappearedNumbers4(int[] nums){
        for(int i=0;i<nums.length;i++){
            int tar = (nums[i]<0?-nums[i]:nums[i])-1;
            if(nums[tar]>0) nums[tar] = -nums[tar];
        }

        List<Integer> l = new ArrayList<>(nums.length);
        for(int i=0;i<nums.length;i++)
            if(nums[i]>0)
                l.add(i+1);
        return l;
    }

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
        System.out.println(s.findDisappearedNumbers3(new int[]{4,3,2,7,8,2,3,1}));
    }
}
