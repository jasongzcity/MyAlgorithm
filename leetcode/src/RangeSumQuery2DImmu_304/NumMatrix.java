package RangeSumQuery2DImmu_304;

/**
 * Given a 2D matrix matrix, find the sum of the elements inside the rectangle
 * defined by its upper left corner (row1, col1) and lower right corner (row2, col2).
 *
 * Example:
 * Given matrix = [
 * [3, 0, 1, 4, 2],
 * [5, 6, 3, 2, 1],
 * [1, 2, 0, 1, 5],
 * [4, 1, 0, 1, 7],
 * [1, 0, 3, 0, 5]
 * ]
 *
 * sumRegion(2, 1, 4, 3) -> 8
 * sumRegion(1, 1, 2, 2) -> 11
 * sumRegion(1, 2, 2, 4) -> 12
 * Note:
 * You may assume that the matrix does not change.
 * There are many calls to sumRegion function.
 * You may assume that row1 ≤ row2 and col1 ≤ col2.
 */
public class NumMatrix {

    private int[][] m;

    public NumMatrix(int[][] matrix) {
        int rows = matrix.length;
        if(rows==0) return;
        int cols = matrix[0].length;
        m = new int[rows+1][cols+1];

        for(int i=1;i<=rows;i++)
            for(int j=1;j<=cols;j++)
                m[i][j] = m[i][j-1]+m[i-1][j]-m[i-1][j-1]+matrix[i-1][j-1];
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        return m[row2+1][col2+1]-m[row2+1][col1]-m[row1][col2+1]+m[row1][col1];
    }

    public static void main(String[] args) {
        int[][] am = new int[][]{
                {3, 0, 1, 4, 2},
                {5, 6, 3, 2, 1},
                {1, 2, 0, 1, 5},
                {4, 1, 0, 1, 7},
                {1, 0, 3, 0, 5}};
        NumMatrix n = new NumMatrix(am);
        System.out.println(n.sumRegion(2,1,4,3));
        System.out.println(n.sumRegion(1,1,2,2));
    }
}
