package LexicographicalNumbers_386;

import java.util.ArrayList;
import java.util.List;

/**
 * Given an integer n, return 1 - n in lexicographical order.
 * For example, given 13, return: [1,10,11,12,13,2,3,4,5,6,7,8,9].
 *
 * Please optimize your algorithm to use less time and space.
 * The input size may be as large as 5,000,000.
 */
public class Solution {
    public List<Integer> lexicalOrder2(int n) {
        List<Integer> rs = new ArrayList<>(n+1);
        dfs(rs,n,0);
        return rs;
    }

    private void dfs(List<Integer> rs,int n,int cur){
        for(int i=0;i<10&&cur+i<=n;i++){
            if((cur+i)!=0){
                rs.add(cur+i);
                dfs(rs,n,(cur+i)*10);
            }
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.lexicalOrder(158).toString());
    }

    // Optimal iterative solution on leetcode
    public List<Integer> lexicalOrder(int n){
        List<Integer> list = new ArrayList<>(n);
        int curr = 1;
        for(int i=1;i<=n;i++){
            list.add(curr);
            if(curr*10<=n) curr*=10;
            else if(curr%10!=9&&curr+1<=n) curr++; // plus one
            else{
                while((curr/10)%10==9) curr/=10;
                curr = curr/10+1; // back one digit
            }
        }
        return list;
    }
}
