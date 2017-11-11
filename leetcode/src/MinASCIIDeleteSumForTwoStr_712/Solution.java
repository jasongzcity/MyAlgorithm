package MinASCIIDeleteSumForTwoStr_712;

import java.util.*;

/**
 * Given two strings s1, s2,
 * find the lowest ASCII sum of deleted characters to make two strings equal.
 *
 * Example 1:
 * Input: s1 = "sea", s2 = "eat"
 * Output: 231
 * Explanation: Deleting "s" from "sea" adds the ASCII value of "s" (115) to the sum.
 * Deleting "t" from "eat" adds 116 to the sum.
 * At the end, both strings are equal,
 * and 115 + 116 = 231 is the minimum sum possible to achieve this.
 *
 * Example 2:
 * Input: s1 = "delete", s2 = "leet"
 * Output: 403
 * Explanation: Deleting "dee" from "delete" to turn the string into "let",
 * adds 100[d]+101[e]+101[e] to the sum.
 * Deleting "e" from "leet" adds 101[e] to the sum.
 * At the end, both strings are equal to "let", and the answer is 100+101+101+101 = 403.
 * If instead we turned both strings into "lee" or "eet",
 * we would get answers of 433 or 417, which are higher.
 *
 * Note:
 * 0 < s1.length, s2.length <= 1000.
 * All elements of each string will have an ASCII value in [97, 122].
 */
public class Solution {

    // similar question: #583 delete operation for 2 strings
    // #72 Edit distance
    // not accepted..
    public int minimumDeleteSum2(String s1, String s2) {
        char[] c1 = s1.toCharArray(), c2 = s2.toCharArray();
        int[] map1 = new int[128], map2 = new int[128];
        for(char c:c1) ++map1[c];
        for(char c:c2) ++map2[c];
        int sum = 0;
        for(int i=97;i<123;i++){
            sum += Math.abs(map1[i]-map2[i])*i;
        }
        return sum;
    }

    // thought: map all character and their positions
    // not accepted.
    // its turning leet into lee...
    public int minimumDeleteSum3(String s1, String s2){
        char[] c1 = s1.toCharArray(), c2 = s2.toCharArray();
        Map<Character, List<Integer>> map1 = new HashMap<>(100);
        for(int i=0;i<c1.length;i++){
            List<Integer> l = map1.get(c1[i]);
            if(l==null){
                l = new LinkedList<>();
                map1.put(c1[i],l);
            }
            l.add(i);
        }
        int p1 = -1, rs = 0;
        for(int i=0;i<c2.length;i++){
            List<Integer> l = map1.get(c2[i]);
            if(l==null||l.size()==0){
                // delete from c2
                rs+=c2[i];
            }else{
                while(l.size()!=0){
                    if(l.get(0)<p1){
                        rs+=c2[i];
                        l.remove(0); // delete char from c1
                    }else break;
                }
                if(l.size()!=0){
                    p1 = l.remove(0);
                }else{
                    // delete char from c2
                    rs+=c2[i];
                }
            }
        }
        for(Map.Entry e:map1.entrySet()){
            rs+=((Character)e.getKey())*((List<Integer>)e.getValue()).size();
        }
        return rs;
    }

    // optimal solution: DP
    public int minimumDeleteSum(String s1, String s2){
        char[] c1 = s1.toCharArray(), c2 = s2.toCharArray();
        int[][] dp = new int[c1.length+1][c2.length+1];
        // dp[i][j] represents the answer for s1[i:], s2[j:]
        // boundary: two empty string s1[s1.length:] s2[s2.length:] = 0;
        for(int i=c1.length-1;i>=0;i--) dp[i][c2.length] = dp[i+1][c2.length] + c1[i];
        for(int i=c2.length-1;i>=0;i--) dp[c1.length][i] = dp[c1.length][i+1] + c2[i];
        for(int i=c1.length-1;i>=0;i--){
            for(int j=c2.length-1;j>=0;j--){
                if(c1[i]==c2[j]){
                    dp[i][j] = dp[i+1][j+1];
                }else{
                    dp[i][j] = Math.min(dp[i][j+1]+c2[j],   /* delete from c2 */
                                        dp[i+1][j]+c1[i]);  /* delete from c1 */
                }
            }
        }
        return dp[0][0];
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.minimumDeleteSum("delete","leet"));
    }
}
