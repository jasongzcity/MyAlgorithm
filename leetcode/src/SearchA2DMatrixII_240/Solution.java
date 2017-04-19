package SearchA2DMatrixII_240;

/**
 * Write an efficient algorithm that searches for a value in an m x n matrix.
 * This matrix has the following properties:
 *
 * Integers in each row are sorted in ascending from left to right.
 * Integers in each column are sorted in ascending from top to bottom.
 *
 * For example,
 * Consider the following matrix:
 * [
 *  [1,   4,  7, 11, 15],
 *  [2,   5,  8, 12, 19],
 *  [3,   6,  9, 16, 22],
 *  [10, 13, 14, 17, 24],
 *  [18, 21, 23, 26, 30]
 * ]
 * Given target = 5, return true.
 * Given target = 20, return false.
 */
public class Solution {
    public static boolean searchMatrix(int[][] m, int target) {
        int rows = m.length;
        if(rows==0) return false;
        int cols = m[0].length;
        return searchInMatrix(m,target,0,rows-1,0,cols-1);
    }

    private static boolean searchInMatrix(int[][] m,int target,int rowlo,
                                          int rowhi,int collo,int colhi){
        if(rowlo>rowhi||collo>colhi) return false;

        int rl = rowlo,rh = rowhi,cl = collo,ch = colhi,rm,cm;
        while(rl<rh||cl<ch){
            rm = rl+((rh-rl)>>1);
            cm = cl+((ch-cl)>>1);
            if(target<=m[rm][cm]){
                rh = rm;
                ch = cm;
            }else{
                if(rl<rh)rl = rm+1;
                if(cl<ch)cl = cm+1;
            }
        }
        return m[rl][cl]==target||searchInMatrix(m,target,rl,rowhi,collo,cl-1)||
                            searchInMatrix(m,target,rowlo,rl-1,cl,colhi);
    }

    public static void main(String[] args) {
        int[][] m = new int[][]{
                {1,   4,  7, 11, 15},
                {2,   5,  8, 12, 19},
                {3,   6,  9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}
        };
        System.out.println(searchMatrix(m,15));
        System.out.println(searchMatrix(m,8));
        System.out.println(searchMatrix(m,31));
        System.out.println(searchMatrix(m,0));
        System.out.println(searchMatrix(m,1));
        System.out.println(searchMatrix(m,17));
        System.out.println(searchMatrix(m,6));

        System.out.println();
        int[][] m2 = new int[][]{
                {1,2,4,8,9}
        };
        System.out.println(searchMatrix(m2,0));
        System.out.println(searchMatrix(m2,10));
        System.out.println(searchMatrix(m2,4));
        System.out.println(searchMatrix(m2,7));
    }

    // most voted solution on leetcode
    public static boolean searchMatrix2(int[][] m,int target){
        int rows = m.length;
        if(rows==0) return false;
        int cols = m[0].length;
        int r = 0,c = cols-1;
        while(r<rows&&c>-1){
            if(m[r][c]==target) return true;
            else if(m[r][c]<target) ++r;
            else --c;
        }
        return false;
    }
}
