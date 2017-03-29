package RmElement_27;

/**
 * Given an array and a value, remove all instances of that value
 * in place and return the new length.
 *
 * Do not allocate extra space for another array,
 * you must do this in place with constant memory.
 *
 * The order of elements can be changed. It doesn't matter
 * what you leave beyond the new length.
 */
public class Solution
{
    public static int removeElement(int[] nums, int val)
    {
        int fast = 0,slow = 0;
        for(;fast<nums.length;)
        {
            if(nums[fast]==val)
            {
                fast++;
                continue;
            }
            nums[slow] = nums[fast];
            fast++;
            slow++;
        }
        return slow;
    }
}
