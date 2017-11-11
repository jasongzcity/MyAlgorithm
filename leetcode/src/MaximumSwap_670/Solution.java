package MaximumSwap_670;

/**
 * Given a non-negative integer,
 * you could swap two digits at most once to get the maximum valued number.
 * Return the maximum valued number you could get.
 *
 * Example 1:
 * Input: 2736
 * Output: 7236
 * Explanation: Swap the number 2 and the number 7.
 *
 * Example 2:
 * Input: 9973
 * Output: 9973
 * Explanation: No swap.
 *
 * 1993 -> 9913
 *
 * Note:
 * The given number is in the range [0, 10^8]
 */
public class Solution {
    // thought: to achieve what the problem asked for,
    // we should find the largest digit, and swap it to the
    // most significant bit which is smaller than it.
    // turning it into string would be easier to deal with.
    // Accepted..
    public int maximumSwap(int num) {
        // preprocess
        if(num<10) return num;
        char[] s = Integer.toString(num).toCharArray();

        int i = 1;
        for (; i < s.length && s[i] <= s[i-1]; i++) ;
        if(i==s.length) return num;
        // now find the maximum & least significant digit since i
        int max = i;
        for(int j = i+1;j<s.length;j++)
            if(s[j]>=s[max])
                max = j;

        // now do binary search between 0 and i to find position for swapping
        int lo = 0, hi = i;
        while(lo<hi){
            int mid = lo+(hi-lo)/2;
            if(s[mid]>=s[max]) lo = mid+1;
            else hi = mid;
        }
        char tm = s[lo];
        s[lo] = s[max];
        s[max] = tm;
        return Integer.parseInt(new String(s));
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.maximumSwap2(173829));
        System.out.println(s.maximumSwap2(985588));
        System.out.println(s.maximumSwap2(9));
        System.out.println(s.maximumSwap2(95));
        System.out.println(s.maximumSwap2(59));
        System.out.println(s.maximumSwap2(1993));
    }

    // Another solution using mapping, so-called "greedy" ??
    public int maximumSwap2(int num){
        char[] a = Integer.toString(num).toCharArray();
        int[] map = new int[10];
        // mark down their last positions..
        for(int i=0;i<a.length;i++)
            map[a[i]-'0'] = i;
        for(int i=0;i<a.length;i++){
            for(int j=9;j>a[i]-'0';j--){
                if(map[j]>i){
                    char tm = a[map[j]];
                    a[map[j]] = a[i];
                    a[i] = tm;
                    return Integer.parseInt(new String(a));
                }
            }
        }
        return num;
    }
}
