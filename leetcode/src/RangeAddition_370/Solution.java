package RangeAddition_370;

/**
 * Assume you have an array of length n initialized with
 * all 0's and are given k update operations.
 *
 * Each operation is represented as a triplet:
 * [startIndex, endIndex, inc]
 * which increments each element of subarray A[startIndex ... endIndex]
 * (startIndex and endIndex inclusive) with inc.
 *
 * Return the modified array after all k operations were executed.
 *
 * Example:
 * Given:
 * length = 5,
 * updates = [
 *   [1,  3,  2],
 *   [2,  4,  3],
 *   [0,  2, -2]
 * ]
 *
 * Output:
 * [-2, 0, 3, 5, 3]
 *
 * Explanation:
 * Initial state:
 * [ 0, 0, 0, 0, 0 ]
 *
 * After applying operation [1, 3, 2]:
 * [ 0, 2, 2, 2, 0 ]
 *
 * After applying operation [2, 4, 3]:
 * [ 0, 2, 5, 5, 3 ]
 *
 * After applying operation [0, 2, -2]:
 * [-2, 0, 3, 5, 3 ]
 */
public class Solution {
    // slow.
    public int[] getModifiedArray2(int length, int[][] updates) {
        int[] rs = new int[length];
        for(int rows=0;rows<updates.length;rows++)
            for(int i=updates[rows][0];i<=updates[rows][1];i++)
                rs[i]+=updates[rows][2];
        return rs;
    }

    public int[] getModifiedArray(int length, int[][] updates){
        int[] rs = new int[length];
        for(int[] a:updates){
            rs[a[0]] += a[2];
            if(a[1]+1<length) rs[a[1]+1] += -a[2];
        }
        for(int i=1;i<length;i++) rs[i] += rs[i-1];
        return rs;
    }
}
