package RangeSumQuery2DMutable_308;

/**
 * Given a 2D matrix matrix, find the sum of the elements inside the
 * rectangle defined by its upper left corner
 * (row1, col1) and lower right corner (row2, col2).
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
 * update(3, 2, 2)
 * sumRegion(2, 1, 4, 3) -> 10
 * Note:
 * The matrix is only modifiable by the update function.
 * You may assume the number of calls to update and sumRegion function
 * is distributed evenly.
 * You may assume that row1 ≤ row2 and col1 ≤ col2.
 */
public class NumMatrix {

    // simply use 2D Binary Index Tree
    private int lowestbit(int i){ return i&-i; }

    private int[][] m;
    private int[][] BIT;

    public NumMatrix(int[][] matrix) {
        if(matrix.length==0) return;
        m = matrix;
        BIT = new int[m.length+1][m[0].length+1];
        for(int i=0;i<m.length;i++)
            for(int j=0;j<m[0].length;j++)
                updateBIT(i,j,m[i][j]);
    }

    public void update(int row, int col, int val) {
        int diff = val-m[row][col];
        m[row][col] = val;
        updateBIT(row,col,diff);
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        return sum(row2,col2)-sum(row2,col1-1)-sum(row1-1,col2)+sum(row1-1,col1-1);
    }

    private void updateBIT(int row,int col,int diff){
        for(int r=row+1;r<BIT.length;r+=lowestbit(r))
            for(int c=col+1;c<BIT[0].length;c+=lowestbit(c))
                BIT[r][c]+=diff;
    }

    private int sum(int row,int col){
        int sum = 0;
        for(int r=row+1;r>0;r-=lowestbit(r))
            for(int c=col+1;c>0;c-=lowestbit(c))
                sum+=BIT[r][c];
        return sum;
    }
}
