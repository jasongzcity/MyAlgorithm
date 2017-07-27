package SparseMatrixMultiplication_311;

import java.util.ArrayList;
import java.util.List;

/**
 * Given two sparse matrices A and B, return the result of AB.
 *
 * You may assume that A's column number is equal to B's row number.
 *
 * Example:
 * A = [
 * [ 1, 0, 0],
 * [-1, 0, 3]
 * ]
 *
 * B = [
 * [ 7, 0, 0 ],
 * [ 0, 0, 0 ],
 * [ 0, 0, 1 ]
 * ]
 *
 *      |  1 0 0 |   | 7 0 0 |   |  7 0 0 |
 * AB = | -1 0 3 | x | 0 0 0 | = | -7 0 3 |
 *                   | 0 0 1 |
 */
public class Solution {
    // brute force
    public int[][] multiply(int[][] A, int[][] B) {
        // assume we don't have empty input matrice
        int arow = A.length,acol = A[0].length,bcol = B[0].length;
        int[][] rs = new int[arow][bcol];
        for(int i=0;i<arow;i++)
            for(int j=0;j<acol;j++)
                if(A[i][j]!=0)
                    for(int k=0;k<bcol;k++)
                        if(B[j][k]!=0)
                            rs[i][k] += A[i][j]*B[j][k];
        return rs;
    }

    // another way to store sparse matrice
    // see details at: http://www.cs.cmu.edu/~scandal/cacm/node9.html
    public int[][] multiply2(int[][] A, int[][] B){
        int arow = A.length,acol = A[0].length,bcol = B[0].length;
        List[] lists = new List[arow];
        int[][] rs = new int[arow][bcol];
        for(int i=0;i<arow;i++){
            List<Integer> l = new ArrayList<>(acol);
            for(int j=0;j<acol;j++){
                if(A[i][j]!=0){
                    l.add(j);
                    l.add(A[i][j]);
                }
            }
            lists[i] = l;
        }

        for(int i=0;i<arow;i++){
            List<Integer> l = lists[i];
            for(int j=0;j<l.size();j+=2){
                int Acol = l.get(j);
                int aval = l.get(j+1);
                for(int k=0;k<bcol;k++){
                    if(B[Acol][k]!=0){
                        rs[i][k]+=aval*B[Acol][k];
                    }
                }
            }
        }
        return rs;
    }
}
