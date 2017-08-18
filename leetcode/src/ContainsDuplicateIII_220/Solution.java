package ContainsDuplicateIII_220;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * Given an array of integers, find out whether there are
 * two distinct indices i and j in the array such that
 * the absolute difference between nums[i] and nums[j]
 * is at most t and the absolute difference between i and j is at most k.
 */
public class Solution {
    public boolean containsNearbyAlmostDuplicate2(int[] nums, int k, int t) {
        if(t<0||k<0) return false;
        TreeSet<Integer> ts = new TreeSet<>();
        for(int i=0;i<nums.length;i++){
            if(i>k) ts.remove(nums[i-k-1]);
            Integer lower = ts.lower(nums[i]),higher = ts.higher(nums[i]);
            if(!ts.add(nums[i])||(lower!=null&&(long)(nums[i])-lower<=t)||
                    (higher!=null&&(long)(higher)-nums[i]<=t)) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        int[] a = new int[]{-1,Integer.MAX_VALUE};
        Solution s = new Solution();
        System.out.println(s.containsNearbyAlmostDuplicate(a,1,Integer.MAX_VALUE));
    }

    // Bucket solution
    // https://leetcode.com/problems/contains-duplicate-iii
    // /tabs/solution#approach-3-buckets-accepted
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t){
        if(t<0) return false;
        Map<Long,Long> bucket = new HashMap<>(k<<1);
        long width = (long)t+1;
        for(int i=0;i<nums.length;i++){
            if(i>k) bucket.remove(getIndex(nums[i-k-1],width));
            long index = getIndex(nums[i],width);
            if(bucket.put(index,(long)nums[i])!=null) return true;
            Long smaller = bucket.get(index-1);
            if(smaller!=null&&nums[i]-smaller<=t) return true;
            Long larger = bucket.get(index+1);
            if(larger!=null&&larger-nums[i]<=t) return true;
        }
        return false;
    }

    private long getIndex(long num,long width){
        return num<0?num/width-1:num/width;
    }
}
