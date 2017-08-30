package ThreeSumSmaller_259;

import java.util.Arrays;

/**
 * Given an array of n integers nums and a target,
 * find the number of index triplets i, j, k
 * with 0 <= i < j < k < n that
 * satisfy the condition nums[i] + nums[j] + nums[k] < target.
 *
 * For example, given nums = [-2, 0, 1, 3], and target = 2.
 * Return 2. Because there are two triplets which sums are less than 2:
 * [-2, 0, 1]
 * [-2, 0, 3]
 * Follow up:
 * Could you solve it in O(n^2) runtime?
 */
public class Solution {
    // solution 1:
    // using binary search.
    // O(n^2logn)
    public int threeSumSmaller2(int[] a, int target) {
        Arrays.sort(a);
        int count = 0;
        for(int i=0;i<a.length-2&&a[i]*3<=target;i++){
            int tar = target-a[i];
            for(int j=i+1;j<a.length-1&&a[j]*2<=tar;j++){
                int hi = binarySearch(a,j+1,a.length,tar-a[j]);
                count+=hi-j;
            }
        }
        return count;
    }

    // hi exclusive
    private int binarySearch(int[] a,int lo,int hi,int target){
        while(lo<hi){
            int mid = (lo+hi)>>1;
            if(a[mid]<target) lo = mid+1;
            else hi = mid;
        }
        return lo-1;
    }

    // two pointer solution
    public int threeSumSmaller(int[] a, int target){
        Arrays.sort(a);
        int count = 0;
        for(int i=0;i<a.length-2;i++){
            int tar = target-a[i],lo = i+1,hi = a.length-1;
            while(lo<hi){
                if(a[lo]+a[hi]<tar){
                    count+=hi-lo;
                    lo++;
                }else{
                    hi--;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.threeSumSmaller(new int[]{0,0,0},0));
    }
}
