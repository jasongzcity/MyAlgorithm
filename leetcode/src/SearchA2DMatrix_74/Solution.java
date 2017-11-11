package SearchA2DMatrix_74;

/**
 * Write an efficient algorithm that searches for a value in an m x n matrix.
 * This matrix has the following properties:
 *
 * Integers in each row are sorted from left to right.
 * The first integer of each row is greater than the last integer of the previous row.
 *
 * For example,
 * Consider the following matrix:
 * [
 *   [1,   3,  5,  7],
 *   [10, 11, 16, 20],
 *   [23, 30, 34, 50]
 * ]
 * Given target = 3, return true.
 */
public class Solution {

    // Second session:
    // notice the relation between row and col and the
    // total standing of the element in the whole matrix.
    public static boolean searchMatrix(int[][] matrix, int target) {
        if(matrix.length==0) return false;
        int rows = matrix.length,cols = matrix[0].length;
        int lo = 0,hi = rows*cols;
        while(lo<hi){
            int mid = lo+((hi-lo)>>1);
            if(matrix[mid/cols][mid%cols]>target) hi = mid;
            else lo = mid+1;
        }
        --lo;
        return lo==-1?false:matrix[lo/cols][lo%cols]==target;
    }

    public static void main(String[] args) {
        int[][] m = new int[][]{
            {1,3,5,7},
            {11,15,16,18},
            {20,21,22,23}
        };
        System.out.println(searchMatrix(m,5));
        System.out.println(searchMatrix(m,6));
        System.out.println(searchMatrix(m,1));
        System.out.println(searchMatrix(m,23));
        System.out.println(searchMatrix(m,0));
        System.out.println(searchMatrix(m,245));
    }
}