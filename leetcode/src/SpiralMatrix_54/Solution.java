package SpiralMatrix_54;

import java.util.ArrayList;
import java.util.Collections;
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

    // "layer" solution
    public static List<Integer> spiralOrderII(int[][] m){
        if(m.length==0) return Collections.emptyList();
        List<Integer> l = new ArrayList<>(m.length*m[0].length);
        int top = 0, down = m.length-1, left = 0, right = m[0].length-1;
        while(top<=down&&left<=right){
            for (int i = left; i <= right; i++) l.add(m[top][i]);
            for (int i = top+1; i <= down; i++) l.add(m[i][right]);
            // notice that you should check if
            // top==down or left == right
            // if so, the operation below will result in "revisit"
            if(left<right&&top<down){
                for (int i = right-1; i >= left; i--) l.add(m[down][i]);
                for (int i = down-1; i >= top; i--) l.add(m[i][left]);
            }
            // "shrinking the bound"
            top++;
            down--;
            left++;
            right--;
        }
        return l;
    }

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

    // Improved solution
    public List<Integer> spiralOrder2(int[][] matrix) {
        List<Integer> ans = new ArrayList<>();
        if (matrix.length == 0)
            return ans;
        int r1 = 0, r2 = matrix.length - 1;
        int c1 = 0, c2 = matrix[0].length - 1;
        while (r1 <= r2 && c1 <= c2) {
            for (int c = c1; c <= c2; c++) ans.add(matrix[r1][c]);
            for (int r = r1 + 1; r <= r2; r++) ans.add(matrix[r][c2]);
            if (r1 < r2 && c1 < c2) {
                for (int c = c2 - 1; c > c1; c--) ans.add(matrix[r2][c]);
                for (int r = r2; r > r1; r--) ans.add(matrix[r][c1]);
            }
            r1++;
            r2--;
            c1++;
            c2--;
        }
        return ans;
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
