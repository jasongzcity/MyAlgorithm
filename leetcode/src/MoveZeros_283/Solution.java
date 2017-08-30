package MoveZeros_283;

/**
 * Given an array nums, write a function to move all 0's to the end of it
 * while maintaining the relative order of the non-zero elements.
 *
 * For example, given nums = [0, 1, 0, 3, 12], after calling your function,
 * nums should be [1, 3, 12, 0, 0].
 *
 * Note:
 * You must do this in-place without making a copy of the array.
 * Minimize the total number of operations.
 */
public class Solution {
    public void moveZeroes2(int[] n) {
        if (n.length < 2) return;
        int p = n.length - 1;
        while (p > 0 && n[p] == 0) --p;
        if (p == 0) return;
        int nonZeroBegin = p, nonZeroEnd = nonZeroBegin, zeroBegin;
        while (nonZeroBegin > -1) {
            while (nonZeroBegin > 0 && n[nonZeroBegin - 1] != 0) --nonZeroBegin;
            zeroBegin = nonZeroBegin - 1;
            if (zeroBegin < 0) return; // done
            while (zeroBegin > 0 && n[zeroBegin - 1] == 0) --zeroBegin;
            int copyPointer = zeroBegin;
            while (nonZeroBegin <= nonZeroEnd) swap(n, copyPointer++, nonZeroBegin++);
            nonZeroBegin = zeroBegin - 1;
            nonZeroEnd = copyPointer - 1;
        }
    }

    private void swap(int[] a, int x, int y) {
        int tm = a[x];
        a[x] = a[y];
        a[y] = tm;
    }

    public void moveZeroes(int[] n) {
        int fast = 0, slow = 0;
        while (fast < n.length) {
            if (n[fast] != 0) n[slow++] = n[fast];
            fast++;
        }
        while (slow < n.length) n[slow++] = 0;
    }
}
