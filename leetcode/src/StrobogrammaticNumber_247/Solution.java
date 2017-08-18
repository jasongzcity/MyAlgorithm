package StrobogrammaticNumber_247;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A strobogrammatic number is a number that looks the same when
 * rotated 180 degrees (looked at upside down).
 *
 * Find all strobogrammatic numbers that are of length = n.
 *
 * For example,
 * Given n = 2, return ["11","69","88","96"].
 */
public class Solution {

    private final char[] same = {'0','1','8'};
    private final char[] notSame = {'6','9'};

    public List<String> findStrobogrammatic(int n) {
        if(n==1) return Arrays.asList("0","1","8");
        char[] ca = new char[n];
        List<String> rs = new ArrayList<>((int)Math.pow(3,n));
        if((n&1)==1)
            dfs(ca,n>>>1,n>>>1,true,rs);
        else
            dfs(ca,(n>>>1)-1,n>>>1,false,rs);
        return rs;
    }

    private void dfs(char[] ca,int left,int right,boolean mustSame,
                     List<String> rs){
        if(left==-1){
            rs.add(new String(ca));
            return;
        }
        for(int i=0;i<same.length;i++) {
            if(left==0&&i==0) continue;
            ca[left] = ca[right] = same[i];
            dfs(ca,left-1,right+1,false,rs);
        }
        if(!mustSame){
            for(int i=0;i<2;i++){
                ca[left] = notSame[i%2];
                ca[right] = notSame[(i+1)%2];
                dfs(ca,left-1,right+1,false,rs);
            }
        }
    }

    // This problem could be better solved by using char matrix.
    // char[][] cm = new char[][]{{'0','0'},.....,{'6','9'},{'9','6'}}

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.findStrobogrammatic(2));
        System.out.println(s.findStrobogrammatic(3));
        System.out.println(s.findStrobogrammatic(4));
        System.out.println(s.findStrobogrammatic(5));
    }
}
