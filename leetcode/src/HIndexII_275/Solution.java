package HIndexII_275;

/**
 * Follow up for H-Index: What if the citations array is sorted in ascending order?
 * Could you optimize your algorithm?
 *
 * Tips: BinarySearch
 * I have tried the linear scanning solution, which is slow..
 * Maybe the input of the test cases are large arrays.
 */
public class Solution {
    public int hIndex(int[] c) {
        int hi = c.length,lo = 0;
        while(hi>lo){
            int mid = lo+((hi-lo)>>1);
            if(c.length-mid>c[mid]) lo = mid+1; // position mid is illegal
            else hi = mid; // search for larger h index.
        }
        return c.length-lo;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.hIndex(new int[]{0}));
        System.out.println(s.hIndex(new int[]{1}));
        System.out.println(s.hIndex(new int[]{1,100,100}));
        System.out.println(s.hIndex(new int[]{0,0,0}));
        System.out.println(s.hIndex(new int[]{1000,1000,1001}));
    }
}
