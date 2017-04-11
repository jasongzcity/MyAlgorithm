package SpiralMatrix_54;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a matrix of m x n elements (m rows, n columns),
 * return all elements of the matrix in spiral order.
 *
 * For example,
 * Given the following matrix:
 *
 * [
 *   [ 1, 2, 3 ],
 *   [ 4, 5, 6 ],
 *   [ 7, 8, 9 ]
 * ]
 * You should return [1,2,3,6,9,8,7,4,5].
 */
public class Solution {
    public static List<Integer> spiralOrder(int[][] matrix) {
        if(matrix.length==0||matrix[0].length==0) return new ArrayList<>();
        int rowLen = matrix[0].length,columnLen = matrix.length-1;
        int currentRow = 0,currentColumn = 0;
        List<Integer> list = new ArrayList<>(rowLen*(columnLen+1));
        while(rowLen>0){
            for (int i = 0; i < rowLen; i++) {
                list.add(matrix[currentRow][currentColumn]);
                currentColumn++;
            }
            currentColumn--;
            currentRow++;
            if(columnLen==0) break;
            for (int i = 0; i < columnLen; i++){
                list.add(matrix[currentRow][currentColumn]);
                currentRow++;
            }
            currentRow--;
            rowLen--;
            currentColumn--;
            if(rowLen==0) break;
            for (int i = 0; i < rowLen; i++) {
                list.add(matrix[currentRow][currentColumn]);
                currentColumn--;
            }
            currentColumn++;
            columnLen--;
            currentRow--;
            if(columnLen==0) break;
            for (int i = 0; i < columnLen; i++) {
                list.add(matrix[currentRow][currentColumn]);
                currentRow--;
            }
            currentRow++;
            currentColumn++;
            columnLen--;
            rowLen--;
        }
        return list;
    }

    public static void main(String[] args) {
        int[][] nums = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12},
                {13, 14, 15},
                {16, 17, 18},
                {19, 20, 21}
        };
        System.out.println(spiralOrder(nums));
        int[][] nums2 = new int[][]{
                {1, 2, 3},
                {4, 5, 6}
        };
        System.out.println(spiralOrder(nums2));

        int[][] nums3 = new int[][]{
                {1, 2, 3, 4, 5, 6},
        };
        System.out.println(spiralOrder(nums3));
        int[][] nums4 = new int[][]{
                {1},
                {4},
                {7}
        };
        System.out.println(spiralOrder(nums4));
        int[][] nums5 = new int[][]{
                {1,2},
                {4,5},
                {7,8},
                {11,12}
        };
        System.out.println(spiralOrder(nums5));
    }
}
