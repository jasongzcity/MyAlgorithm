package MinimumSizeSubarraySum_209;

import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of n positive integers and a positive integer s,
 * find the minimal length of a contiguous subarray of which the sum ≥ s.
 * If there isn't one, return 0 instead.
 *
 * For example, given the array [2,3,1,2,4,3] and s = 7,
 * the subarray [4,3] has the minimal length under the problem constraint.
 *
 * More practice:
 * If you have figured out the O(n) solution,
 * try coding another solution of which the time complexity is O(n log n).
 */
public class Solution {
    public int minSubArrayLen2(int s, int[] a){
        // classic two pointer
        int left = 0,right = 0,sum = 0,min = Integer.MAX_VALUE;
        while(true){
            while(right<a.length&&sum<s) sum+=a[right++];
            if(sum<s) break;
            // move the left pointer to "shrink" to get the minimum subarray
            while(sum>=s) sum-=a[left++];
            if(right-left+1<min) min = right-left+1;
        }
        return min==Integer.MAX_VALUE?0:min;
    }

    public static void main(String[] args) {
        int[] a = {1,1,1,1,1,3,2};
        Solution s = new Solution();
        System.out.println(s.minSubArrayLen(5,a));
    }

    // faster two pointer solution
    // it first find a "window", and maintain a "window"
    public int minSubArrayLen3(int s, int[] a){
        int left = 0,right = 0,sum = 0,min = 0;
        while(right<a.length&&sum<s) sum+=a[right++];
        if(sum<s) return 0;
        while(sum>=s) sum-=a[left++];
        min = right-left+1;
        if(min==1) return 1;

        while(right<a.length){
            // window move forward
            // notice the current window sum is smaller than s
            sum+=a[right++]-a[left++];
            while(sum>=s){
                // once sum >= s again, we got a smaller subarray!
                sum-=a[left++];
                --min;
                if(min==1) return 1;
            }
        }
        return min;
    }

    // O(nlgn) solution
    public int minSubArrayLen(int s, int[] a){
        int[] sums = new int[a.length+1];
        for(int i=1;i<=a.length;i++){
            if(a[i-1]>=s) return 1;
            sums[i] = sums[i-1]+a[i-1];
        }

        int min = Integer.MAX_VALUE;
        for(int i=a.length;sums[i]>=s;i--){
            int target = sums[i]-s,len;
            if(target==0) len = i;
            else{
                int p = binarySearch(sums,0,i,target);
                if(sums[p]==target) len = i-p;
                else len = i-p+1;
            }
            if(len<min) min = len;
        }
        return min==Integer.MAX_VALUE?0:min;
    }

    // binary search target element
    // find the largest element which is smaller than or equals to arget
    // hi exclusive
    private int binarySearch(int[] a,int lo,int hi,int target){
        int l = lo,h = hi,mid;
        while(l<h){
            mid = (l+h)>>1;
            if(a[mid]<target) l = mid+1;
            else h = mid;
        }
        return l;
    }
}
