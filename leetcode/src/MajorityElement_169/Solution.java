package MajorityElement_169;

/**
 * Given an array of size n, find the majority element.
 * The majority element is the element that appears more than ⌊ n/2 ⌋ times.
 *
 * You may assume that the array is non-empty and the majority element
 * always exist in the array.
 */
public class Solution {
    // O(1) space one pass solution
    public int majorityElement(int[] nums) {
        int maj = 0,count = 0;
        for(int i=0;i<nums.length;i++){
            if(count==0){
                maj=nums[i];
                ++count;
            }else{
                if(maj==nums[i]) ++count;
                else --count;
            }
        }
        return maj;
    }
}
