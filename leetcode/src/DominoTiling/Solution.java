package DominoTiling;

/**
 * B503 midterm
 * Fill 2*n floor with 1*2 domino tilings. How many ways could the
 * tilings be?
 * Hint: DP
 */
public class Solution {
    public int ways(int n){
        if(n<3) return n;
        int[] dpA = new int[n], dpB = new int[n];
        dpA[0] = 1;
        dpA[1] = 1;
        dpB[1] = 1;
        for (int i = 2; i < n; i++) {
            dpA[i] = dpA[i-1] + dpB[i-1];
            dpB[i] = dpA[i-2] + dpB[i-2];
        }
        return dpA[n-1]+dpB[n-1];
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.ways(3));
        System.out.println(s.ways(10));
        System.out.println(s.ways(4));
    }

    // Follow up:
    // Now you are asked to design an algorithm to compute the number of ways
    // to tile a 3 x n grid using 1 x 2 dominos.
    // There may be exponentially many ways,
    // so you are only asked to figure out this number modulo a big
    // number M which is also given as input.
    // You may also assume the arithmetic operations on integers
    // with absolute value at most M take O(1) time.
    // You will receive half of the possible points if your algorithm runs in time O(n).
    // You will receive full points if your algorithm runs in time O(log n).
    public int ways(int n, int M){
        // notice that n can't not be odd
        // 3^(n/2).
        return -1;
    }
}
