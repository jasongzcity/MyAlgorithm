package PascalTriangleII_119;

import java.util.ArrayList;
import java.util.List;

/**
 * Given an index k, return the kth row of the Pascal's triangle.
 *
 * For example, given k = 3,
 * Return [1,3,3,1].
 * Note:
 * Could you optimize your algorithm to use only O(k) extra space?
 */
public class Solution {
    public static List<Integer> getRow(int r) {
        List<Integer> src = new ArrayList<>(r),tar = new ArrayList<>(r),tmp = tar;
        src.add(1);
        for(int i=0;i<r;i++){
            tar.clear();
            tar.add(1);
            Integer last = src.get(0);
            for(int j=0;j<src.size()-1;j++){
                Integer cur = src.get(j+1);
                tar.add(last+cur);
                last = cur;
            }
            tar.add(1);
            // swap two list, get ready for next iteration
            tmp = src;
            src = tar;
            tar = tmp;
        }
        return src;
    }

    public static void main(String[] args) {
        List l = getRow(3);
        System.out.println(l.toString());
    }
}
