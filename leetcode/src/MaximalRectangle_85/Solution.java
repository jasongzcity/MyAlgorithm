package MaximalRectangle_85;

import java.util.Arrays;

/**
 * Given a 2D binary matrix filled with 0's and 1's,
 * find the largest rectangle containing only 1's and return its area.
 *
 * For example, given the following matrix:
 * 1 0 1 0 0
 * 1 0 1 1 1
 * 1 1 1 1 1
 * 1 0 0 1 0
 *
 * Return 6.
 */
public class Solution {
    public static int maximalRectangle(char[][] m) {
        if(m.length==0) return 0;
        int rows = m.length,cols = m[0].length,rs = 0;
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                if(m[i][j]=='0')
                    continue;
                rs = Math.max(rs,getMaxRec(m,i,j,rows,cols));
            }
        }
        return rs;
    }

    // calculate the largest area of rectangle which has the upper-left corner
    // at (row,col) position
    private static int getMaxRec(char[][] m,int row,int col,int rows,int cols){
        int max = 0,r = row,c = Integer.MAX_VALUE,colbound = cols;
        while(r<rows&&c>col){
            c = col;
            while(c<cols&&m[r][c]=='1'&&c<colbound) ++c;
            colbound = c;
            max = Math.max(max,(r-row+1)*(c-col));
            ++r;
        }
        return max;
    }

    public static void main(String[] args) {
        char[][] m = new char[][]{
                "10100".toCharArray(),
                "10111".toCharArray(),
                "11111".toCharArray(),
                "10010".toCharArray()
        };
        System.out.println(maximalRectangleDP(m));

        char[][] m2 = new char[][]{
                "000000".toCharArray(),
                "000000".toCharArray(),
                "000000".toCharArray(),
                "000001".toCharArray()
        };
        System.out.println(maximalRectangleDP(m2));

        char[][] m3 = new char[][]{
                {'1'}
        };
        System.out.println(maximalRectangleDP(m3));

        char[][] m4 = new char[][]{
                "00000".toCharArray()
        };
        System.out.println(maximalRectangleDP(m4));
    }

    // most voted solution on leetcode
    // To save space, the solution reduce the dp matrix to dp array
    public static int maximalRectangleDP(char[][] m){
        if(m.length==0) return 0;
        int rows = m.length,cols = m[0].length,max = 0;
        int[] right = new int[cols],left = new int[cols],height = new int[cols];
        Arrays.fill(right,cols-1);
        for(int r=0;r<rows;r++){
            int currentLeft = 0,currentRight = cols-1;

            // calculate heights
            for(int c=0;c<cols;c++){
                if(m[r][c]=='1') height[c]++;
                else height[c] = 0;
            }

            // calculate left
            // consider: 001110
            //           011100
            // At second row, the current left should be 1, but
            // at the third column the left have been adjusted to 2.
            for (int c = 0; c < cols; c++) {
                if(m[r][c]=='1'){
                    left[c] = Math.max(left[c],currentLeft);
                }else{
                    left[c] = 0;
                    currentLeft = c+1;
                }
            }

            // calculate right
            // consider: 011100
            //           001110
            for (int c = cols-1; c > -1; c--) {
                if(m[r][c]=='1'){
                    right[c] = Math.min(right[c],currentRight);
                }else{
                    right[c] = cols-1;
                    currentRight = c-1;
                }
            }

            // calculate area
            for (int c = 0; c < cols; c++)
                max = Math.max(max,(right[c]-left[c]+1)*height[c]);
        }
        return max;
    }
}
