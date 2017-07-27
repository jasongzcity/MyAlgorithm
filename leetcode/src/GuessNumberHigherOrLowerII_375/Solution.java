package GuessNumberHigherOrLowerII_375;

/**
 * We are playing the Guess Game. The game is as follows:
 *
 * I pick a number from 1 to n. You have to guess which number I picked.
 *
 * Every time you guess wrong,
 * I'll tell you whether the number I picked is higher or lower.
 *
 * However, when you guess a particular number x, and you guess wrong,
 * you pay $x. You win the game when you guess the number I picked.
 *
 * Example:
 *
 * n = 10, I pick 8.
 *
 * First round:  You guess 5, I tell you that it's higher. You pay $5.
 * Second round: You guess 7, I tell you that it's higher. You pay $7.
 * Third round:  You guess 9, I tell you that it's lower. You pay $9.
 *
 * Game over. 8 is the number I picked.
 *
 * You end up paying $5 + $7 + $9 = $21.
 * Given a particular n ≥ 1, find out how much money you need to have to guarantee a win.
 */
public class Solution {
    // DFS with memo
    public int getMoneyAmount2(int n) {
        int[][] memo = new int[n+1][n+1];
        return dfs(1,n,memo);
    }

    private int dfs(int begin,int end,int[][] memo){
        if(begin>=end) return 0;
        if(memo[begin][end]==0){
            int minCost = Integer.MAX_VALUE;
            for(int i=begin;i<=end;i++){
                int cost = i+Math.max(dfs(begin,i-1,memo),dfs(i+1,end,memo));
                minCost = Math.min(cost,minCost);
            }
            memo[begin][end] = minCost;
        }
        return memo[begin][end];
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.getMoneyAmount(8));
        System.out.println(s.getMoneyAmount(16));
    }

    // bottom up solution
    // based on the solution above.
    // we need to figure out the sequence
    public int getMoneyAmount(int n){
        int[][] memo = new int[n+2][n+2]; // left empty for leftmost and rightmost corner cases
        for(int end=2;end<=n;end++){
            for(int begin=end-1;begin>=1;begin--){
                int minCost = Integer.MAX_VALUE;
                for(int p=begin;p<=end;p++){
                    int cost = p+Math.max(memo[begin][p-1],memo[p+1][end]);
                    if(cost<minCost) minCost = cost;
                }
                memo[begin][end] = minCost;
            }
        }
        return memo[1][n];
    }
}
