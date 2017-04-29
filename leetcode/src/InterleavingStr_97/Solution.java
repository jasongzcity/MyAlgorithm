package InterleavingStr_97;

/**
 * Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.
 *
 * For example,
 * Given:
 * s1 = "aabcc",
 * s2 = "dbbca",
 *
 * When s3 = "aadbbcbcac", return true.
 * When s3 = "aadbbbaccc", return false.
 */
public class Solution {
    public static boolean isInterleave(String s1, String s2, String s3) {
        int l1 = s1.length(), l2 = s2.length(),l3 = s3.length();
        if(l3!=l1+l2) return false;
        boolean[][] dp = new boolean[l1+1][l2+1];
        dp[0][0] = true; // special case for empty s3
        for(int i=0;i<l2;i++){
            if(s2.charAt(i)==s3.charAt(i)) dp[0][i+1] = true;
            else break;
        }
        for(int i=0;i<l1;i++){
            if(s1.charAt(i)==s3.charAt(i)) dp[i+1][0] = true;
            else break;
        }
        for(int i=1;i<=l1;i++){
            for(int j=1;j<=l2;j++){
                dp[i][j] = (dp[i][j-1]&&s2.charAt(j-1)==s3.charAt(i+j-1))||
                            (dp[i-1][j]&&s1.charAt(i-1)==s3.charAt(i+j-1));
            }
        }
        return dp[l1][l2];
    }

    public static void main(String[] args) {
//        String s1 = "ab",s2 = "",s3 = "ab";
//        System.out.println(isInterleave(s1,s2,s3));
//        System.out.println(isInterleave(s2,s1,s3));
//        s1 = "aabcc";
//        s2 = "dbbca";
//        s3 = "aadbbcbcac";
//        System.out.println(isInterleave(s1,s2,s3));
//        s3 = "aadbbbaccc";
//        System.out.println(isInterleave(s1,s2,s3));
        String s1 = "ab",s2 = "",s3 = "ab";
        System.out.println(isInterleave2(s1,s2,s3));
        System.out.println(isInterleave2(s2,s1,s3));
        s1 = "aabcc";
        s2 = "dbbca";
        s3 = "aadbbcbcac";
        System.out.println(isInterleave2(s1,s2,s3));
        s3 = "aadbbbaccc";
        System.out.println(isInterleave2(s1,s2,s3));
    }

    // DFS solution, even faster.
    public static boolean isInterleave2(String s1, String s2, String s3){
        if(s1.length()+s2.length()!=s3.length()) return false;
        char[] c1 = s1.toCharArray(),c2 = s2.toCharArray(),c3 = s3.toCharArray();
        return isInterleaveDFS(c1,c2,c3,0,0,0,new int[s1.length()][s2.length()]);
    }

    // like a top-down DP
    // judge if s3[p3..end] is interleave of
    // s1[p1..end] and s2[p2..end]
    private static boolean isInterleaveDFS(char[] s1,char[] s2,char[] s3,
                                           int p1,int p2,int p3,int[][] memo){
        if(p1==s1.length){
            for(;p2<s2.length;p2++){
                if(s2[p2]!=s3[p3++])
                    return false;
            }
            return true;
        }
        if(p2==s2.length){
            for(;p1<s1.length;p1++){
                if(s1[p1]!=s3[p3++])
                    return false;
            }
            return true;
        }
        if(memo[p1][p2]!=0) return memo[p1][p2]==1;
        boolean rs = (s1[p1]==s3[p3]&&isInterleaveDFS(s1,s2,s3,p1+1,p2,p3+1,memo))||
                    (s2[p2]==s3[p3]&&isInterleaveDFS(s1,s2,s3,p1,p2+1,p3+1,memo));
        memo[p1][p2] = rs?1:2;
        return rs;
    }
}