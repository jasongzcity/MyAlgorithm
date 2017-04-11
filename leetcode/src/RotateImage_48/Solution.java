package RotateImage_48;

/**
 * You are given an n x n 2D matrix representing an image.
 *
 * Rotate the image by 90 degrees (clockwise).
 *
 * Follow up:
 * Could you do this in-place?
 */
public class Solution {
    // In-place
    public static void rotate(int[][] matrix) {
        int len = matrix.length;
        int i = 0;
        while(true){
            int right = len-1-i,left = i;
            if(left>=right) break;
            for (int p = left; p < right; p++)
                rotateAt(matrix,i,p);
            ++i;
        }
    }

    private static void rotateAt(int[][] matrix,int row,int column){
        int backup = matrix[row][column],maxIndex = matrix.length-1;
        matrix[row][column] = matrix[maxIndex-column][row];
        matrix[maxIndex-column][row] = matrix[maxIndex-row][maxIndex-column];
        matrix[maxIndex-row][maxIndex-column] = matrix[column][maxIndex-row];
        matrix[column][maxIndex-row] = backup;
    }

    public static void rotate2(int[][] matrix){
        int len = matrix.length,maxIndex = len-1;
        int i = 0;
        while(true){
            int right = len-1-i,left = i;
            if(left>=right) break;
            for (int p = left; p < right; p++){
                int backup = matrix[i][p];
                matrix[i][p] = matrix[maxIndex-p][i];
                matrix[maxIndex-p][i] = matrix[maxIndex-i][maxIndex-p];
                matrix[maxIndex-i][maxIndex-p] = matrix[p][maxIndex-i];
                matrix[p][maxIndex-i] = backup;
            }
            ++i;
        }
    }

    public static void main(String[] args) {
        int[][] matrix = new int[5][5];
        matrix[0] = new int[]{1,2,3,4,5};
        matrix[1] = new int[]{6,7,8,9,10};
        matrix[2] = new int[]{11,12,13,14,15};
        matrix[3] = new int[]{16,17,18,19,20};
        matrix[4] = new int[]{21,22,23,24,25};

        for(int[] a:matrix){
            for(int i:a)
                System.out.print(i+" ");
            System.out.println();
        }
        System.out.println();

        //rotate(matrix);
        rotate2(matrix);

        for(int[] a:matrix){
            for(int i:a)
                System.out.print(i+" ");
            System.out.println();
        }

    }
}
