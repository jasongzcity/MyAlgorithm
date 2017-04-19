package SpiralMatrix_59;

/**
 * Given an integer n, generate a square matrix filled with elements
 * from 1 to n2 in spiral order.
 *
 * For example,
 * Given n = 3,
 * You should return the following matrix:
 *
 * [
 *  [ 1, 2, 3 ],
 *  [ 8, 9, 4 ],
 *  [ 7, 6, 5 ]
 * ]
 */
public class Solution {
    public static int[][] generateMatrix(int n) {
        int[][] m = new int[n][n];
        int num = 1;
        int currentRow = 0,currentColumn = 0,rowLen = n,columnLen = n-1;
        while(rowLen>0){
            for (int i = 0; i < rowLen; i++) {
                m[currentRow][currentColumn] = num++;
                currentColumn++;
            }
            currentColumn--;
            currentRow++;
            if(columnLen==0) break;
            for (int i = 0; i < columnLen; i++){
                m[currentRow][currentColumn] = num++;
                currentRow++;
            }
            currentRow--;
            rowLen--;
            currentColumn--;
            if(rowLen==0) break;
            for (int i = 0; i < rowLen; i++) {
                m[currentRow][currentColumn] = num++;
                currentColumn--;
            }
            currentColumn++;
            columnLen--;
            currentRow--;
            if(columnLen==0) break;
            for (int i = 0; i < columnLen; i++) {
                m[currentRow][currentColumn] = num++;
                currentRow--;
            }
            currentRow++;
            currentColumn++;
            columnLen--;
            rowLen--;
        }
        return m;
    }
}
