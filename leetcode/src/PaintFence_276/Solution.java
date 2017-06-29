package PaintFence_276;

/**
 * There is a fence with n posts, each post can be painted with one of the k colors.
 *
 * You have to paint all the posts such that
 * no more than two adjacent fence posts have the same color.
 *
 * Return the total number of ways you can paint the fence.
 *
 * Note:
 * n and k are non-negative integers.
 */
public class Solution {
    public int numWays(int n, int k) {
        if(n==0) return 0;
        int[] dup = new int[n],noDup = new int[n];
        dup[0] = 0;
        noDup[0] = k;
        for(int i=1;i<n;i++){
            dup[i] = noDup[i-1];
            noDup[i] = (noDup[i-1]+dup[i-1])*(k-1);
        }
        return dup[n-1]+noDup[n-1];
    }

    // improve it to O(1) space very easily
    public int numWays2(int n,int k){
        if(n==0) return 0;
        int noDup = k,dup = 0;
        for(int i=1;i<n;i++){
            int tm = dup;
            dup = noDup;
            noDup = (noDup+tm)*(k-1);
        }
        return noDup+dup;
    }
}
