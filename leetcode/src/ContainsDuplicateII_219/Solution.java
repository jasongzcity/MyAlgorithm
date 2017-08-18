package ContainsDuplicateII_219;

import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of integers and an integer k,
 * find out whether there are two distinct indices i and j in the array
 * such that nums[i] = nums[j] and the absolute difference
 * between i and j is at most k.
 */
public class Solution {
    // We could also use a set to "maintain" a window.
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer,Integer> map = new HashMap<>(nums.length<<1);
        for(int i=0;i<nums.length;i++){
            Integer prev = map.put(nums[i],i);
            if(prev!=null&&i-prev<=k) return true;
        }
        return false;
    }
}
