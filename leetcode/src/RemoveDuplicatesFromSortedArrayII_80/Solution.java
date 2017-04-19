package RemoveDuplicatesFromSortedArrayII_80;

import java.util.Arrays;

/**
 * Follow up for "Remove Duplicates"(#26):
 * What if duplicates are allowed at most twice?
 *
 * For example, Given sorted array nums = [1,1,1,2,2,3],
 * Your function should return length = 5,
 * with the first five elements of nums being 1, 1, 2, 2 and 3.
 * It doesn't matter what you leave beyond the new length.
 */
public class Solution {
    public static int removeDuplicates(int[] a) {
        int fast = 0,slow = 0;
        for(;fast<a.length;fast++){
            int temp = fast;
            a[slow++] = a[fast];
            while(fast<a.length-1&&a[fast]==a[fast+1]) ++fast;
            if(temp!=fast) a[slow++] = a[fast];
        }
        return slow;
    }

    public static void main(String[] args) {
        int[] a = new int[]{1,1,1,2,2,3};
        System.out.println(removeDuplicates(a));
        System.out.println(Arrays.toString(a));
    }

    // most voted solution on leetcode
    public static int removeDups(int[] a){
        int fast = 0,slow = 0;
        for(;fast<a.length;fast++)
            if(slow<2||a[fast]>a[slow-2])
                a[slow++] = a[fast];
        return slow;
    }
}
