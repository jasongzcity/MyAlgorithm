package RmDuplicateInSortedArray_26;

/**
 *  Given a sorted array,remove the duplicates in place such that
 *  each element appear only once and return the new length.
 *  Do not allocate extra space for another array,
 *  you must do this in place with constant memory.
 */
public class Solution
{
    public static int removeDuplicates(int[] nums)
    {
        int slow=0,fast=0;
        for(;fast<nums.length;slow++,fast++)
        {
            while(fast<nums.length-1&&nums[fast]==nums[fast+1]) fast++;// jump to the last duplicate
            nums[slow] = nums[fast];
        }
        return slow;
    }
}
