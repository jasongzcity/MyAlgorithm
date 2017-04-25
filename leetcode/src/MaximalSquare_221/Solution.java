package MaximalSquare_221;

import java.util.Arrays;

/**
 * Given a 2D binary matrix filled with 0's and 1's,
 * find the largest square containing only 1's and return its area.
 *
 * For example, given the following matrix:
 * 1 0 1 0 0
 * 1 0 1 1 1
 * 1 1 1 1 1
 * 1 0 0 1 0
 *
 * Return 4.
 */
public class Solution {
    // I will use the same DP solution as #85 maximal rectangle
    public static int maximalSquare(char[][] m) {
        if(m.length==0) return 0;
        int rows = m.length, cols = m[0].length;
        int max = 0;
        int[] left = new int[cols],right = new int[cols],height = new int[cols];
        Arrays.fill(right,cols-1);
        for (int r = 0; r < rows; r++) {
            int currentLeft = 0,currentRight = cols-1;
            for (int c = 0; c < cols; c++) {
                if(m[r][c]=='1'){
                    height[c]++;
                    left[c] = Math.max(left[c],currentLeft);
                }else{
                    height[c] = 0;
                    left[c] = 0;
                    currentLeft = c+1;
                }
            }

            for (int c = cols-1; c > -1; c--) {
                if(m[r][c]=='1'){
                    right[c] = Math.min(right[c],currentRight);
                }else{
                    currentRight = c-1;
                    right[c] = cols-1;
                }
            }

            for (int c = 0; c < cols; c++) {
                int wide = right[c]-left[c]+1;
                if(wide>=height[c])
                    max = Math.max(max,height[c]*height[c]);
            }
        }
        return max;
    }

    public static void main(String[] args) {
        char[][] m1 = new char[][]{
                "10100".toCharArray(),
                "10111".toCharArray(),
                "11111".toCharArray(),
                "01110".toCharArray()
        };
        System.out.println(maximalSquare(m1));

        char[][] m2 = new char[][]{
                "100000".toCharArray(),
                "100000".toCharArray()
        };
        System.out.println(maximalSquare(m2));

//        System.out.println(maximalSquareDP2(m1));
//        System.out.println(maximalSquareDP2(m2));

        char[][] m3 = new char[][]{
                "10111".toCharArray(),
                "01010".toCharArray(),
                "11011".toCharArray(),
                "11011".toCharArray(),
                "01111".toCharArray()
        };
        System.out.println(maximalSquareDP2(m3));
    }

    // standard DP solution
    public static int maximalSquareDP(char[][] m){
        if(m.length==0) return 0;
        int cols = m[0].length,rows = m.length,maxedge = 0;
        int[][] edges = new int[rows][cols];
        // edges[i][j] stands for the maximal square edge can achieve
        // at position (i,j),
        for(int i=0;i<cols;i++){
            edges[0][i] = m[0][i] - '0';
            maxedge = Math.max(edges[0][i],maxedge);
        }

        for(int i=0;i<rows;i++){
            edges[i][0] = m[i][0] - '0';
            maxedge = Math.max(edges[i][0],maxedge);
        }

        for(int i=1;i<rows;i++){
            for(int j=1;j<cols;j++){
                if(m[i][j]=='1') {
                    // transition equation
                    edges[i][j] = Math.min(Math.min(edges[i - 1][j - 1],
                            edges[i - 1][j]), edges[i][j - 1]) + 1;

                    maxedge = Math.max(maxedge, edges[i][j]);
                }
            }
        }

        return maxedge*maxedge;
    }

    // DP using only one vector
    public static int maximalSquareDP2(char[][] m){
        if(m.length==0) return 0;
        int rows = m.length,cols = m[0].length,maxedge = 0;
        int[] dp = new int[cols];
        for(int i=0;i<cols;i++){
            if(m[0][i]=='1'){
                dp[i] = 1;
                maxedge = 1;
            }
        }
        for(int i=1;i<rows;i++){
            int temp = dp[0];
            dp[0] = m[i][0] - '0';
            maxedge = Math.max(maxedge,dp[0]);
            for(int j=1;j<cols;j++){
                if(m[i][j]=='1'){
                    int newval = Math.min(Math.min(temp, dp[j - 1]), dp[j]) + 1;
                    maxedge = Math.max(newval, maxedge);
                    temp = dp[j];
                    dp[j] = newval;
                }else{ // no need to update temp here
                    dp[j] = 0;
                }
            }
        }
        return maxedge*maxedge;
    }
}
