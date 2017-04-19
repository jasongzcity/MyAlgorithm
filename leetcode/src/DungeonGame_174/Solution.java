package DungeonGame_174;

public class Solution {
    // DP: 2O(n^2)
    // incorrect..
//    public static int calculateMinimumHP(int[][] d) {
//        int rowLen = d[0].length,columnLen = d.length;
//        int[][] minCost = new int[columnLen][rowLen],dp = new int[columnLen][rowLen];
//        // minCost stands for the minimum health which cost the knight to
//        // this position.
//        minCost[0][0] = d[0][0];
//        for(int i=1;i<rowLen;i++) minCost[0][i] = minCost[0][i-1]+d[0][i];
//        for(int i=1;i<columnLen;i++) minCost[i][0] = minCost[i-1][0]+d[i][0];
//
//        if(rowLen>1&&columnLen>1){
//            for(int i=1;i<columnLen;i++)
//                for(int j=1;j<rowLen;j++)
//                    minCost[i][j] = Math.max(minCost[i-1][j],minCost[i][j-1])+d[i][j];
//        }
//
//        // dp stands for the maximum health which the knight have cost
//        // come to this position.
//        dp[0][0] = Math.min(minCost[0][0],0);// skip positive
//        for(int i=1;i<rowLen;i++) dp[0][i] = Math.min(dp[0][i-1],minCost[0][i]);
//        for(int i=1;i<columnLen;i++) dp[i][0] = Math.min(dp[i-1][0],minCost[i][0]);
//
//        if(rowLen>1&&columnLen>1){
//            for(int i=1;i<columnLen;i++)
//                for(int j=1;j<rowLen;j++)
//                    dp[i][j] = Math.min(Math.max(dp[i-1][j],dp[i][j-1]),minCost[i][j]);
//        }
//        return -dp[columnLen-1][rowLen-1]+1;
//    }

    public static void main(String[] args) {
        int[][] d = new int[][]{
                {-2,-3,3},
                {-5,-10,1},
                {10,30,-5},
        };
        System.out.println(calculateMinimumHP(d));
    }

    // according to the most voted C++ solution on leetcode.
    public static int calculateMinimumHP(int[][] d){
        int rowLen = d[0].length,columnLen = d.length;
        int[][] hpneed = new int[columnLen][rowLen];
        // the hpneed[i][j] represents the hp needed at position i,j
        // Notice: calculate sum backward and set value to 1
        // when needed negative hp. This method "skip"
        // the position value in the dungeon subtly.
        hpneed[columnLen-1][rowLen-1] = (d[columnLen-1][rowLen-1]<0)?
                (1-d[columnLen-1][rowLen-1]):1;
        for(int i=rowLen-2;i>-1;i--){
            int need = hpneed[columnLen-1][i+1] - d[columnLen-1][i];
            hpneed[columnLen-1][i] = need<=0?1:need;
        }
        for(int i=columnLen-2;i>-1;i--){
            int need = hpneed[i+1][rowLen-1] - d[i][rowLen-1];
            hpneed[i][rowLen-1] = need<=0?1:need;
        }

        for(int i=columnLen-2;i>-1;i--){
            for(int j=rowLen-2;j>-1;j--){
                int need = Math.min(hpneed[i+1][j],hpneed[i][j+1])-d[i][j];
                hpneed[i][j] = need<=0?1:need;
            }
        }
        return hpneed[0][0];
    }
}
