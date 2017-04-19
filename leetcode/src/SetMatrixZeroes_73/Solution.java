package SetMatrixZeroes_73;

import java.util.*;

/**
 * Given a m x n matrix, if an element is 0,
 * set its entire row and column to 0.
 * Do it in place.
 *
 * Tips:A simple improvement uses O(m + n) space,
 * but still not the best solution.
 */
public class Solution {
    // make a guess : the O(m+n) solution uses two
    // array as mapping for the number of row and column
    // pending to be set

    // in-place method,O(2(n^2))
    public static void setZeroesInPlace(int[][] matrix) {
        int cols = matrix[0].length, rows = matrix.length;
        // method: we can use the first row and column as
        // the map
        boolean firstcol = false;

        for(int i=0;i<rows;i++){
            if(matrix[i][0]==0) firstcol = true;
            for(int j=1;j<cols;j++){
                if(matrix[i][j]==0){
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        for(int i=rows-1;i>-1;i--){
            for(int j=1;j<cols;j++){
                if(matrix[i][0]==0||matrix[0][j]==0)
                    matrix[i][j] = 0;
            }
            if(firstcol) matrix[i][0] = 0;
        }
    }

    public static void setZeroes(int[][] matrix) {
        int cols = matrix[0].length, rows = matrix.length;
        boolean[] r = new boolean[rows],c = new boolean[cols];
        for(int i = 0;i<rows;i++){
            for(int j = 0;j<cols;j++){
                if(matrix[i][j]==0) {
                    r[i] = true;
                    c[j] = true;
                }
            }
        }

        for(int i=0;i<rows;i++)
            if(r[i]==true)
                Arrays.fill(matrix[i],0);
        for(int i=0;i<cols;i++){
            if(c[i]==true)
                for(int x=0;x<rows;x++)
                    matrix[x][i] = 0;
        }
    }

    public static void main(String[] args) {
        int[][] m = new int[][]{
                {1},
                {0}
        };
        setZeroesInPlace(m);
        for(int[] a:m){
            for(int i:a){
                System.out.print(i+" ");
            }
            System.out.println();
        }
    }
}