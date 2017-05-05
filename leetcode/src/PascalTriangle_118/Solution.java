package PascalTriangle_118;

import java.util.ArrayList;
import java.util.List;

/**
 * Given numRows, generate the first numRows of Pascal's triangle.
 * For example, given numRows = 5,
 * Return
 * [
 *  [1],
 *  [1,1],
 *  [1,2,1],
 *  [1,3,3,1],
 *  [1,4,6,4,1]
 * ]
 */
public class Solution {
    public static List<List<Integer>> generate(int n) {
        if(n==0) return new ArrayList<>();
        List<List<Integer>> rs = new ArrayList<>(n);
        List<Integer> prev = new ArrayList<>(1);
        prev.add(1);
        rs.add(prev);
        for(int i=1;i<n;i++){
            List<Integer> cur = new ArrayList<>(i+1);
            cur.add(1);
            Integer last = prev.get(0);
            for(int j=0;j<prev.size()-1;j++){
                Integer current = prev.get(j+1);
                cur.add(current+last);
                last = current;
            }
            cur.add(1);
            prev = cur;
            rs.add(cur);
        }
        return rs;
    }

    public static void main(String[] args) {
        List l = generate(5);
        System.out.println(l.toString());
    }
}
