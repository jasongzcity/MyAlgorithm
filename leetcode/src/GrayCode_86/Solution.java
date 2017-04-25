package GrayCode_86;

import java.util.ArrayList;
import java.util.List;

/**
 * The gray code is a binary numeral system where two successive values
 * differ in only one bit.
 *
 * Given a non-negative integer n representing the total number of bits in the code,
 * print the sequence of gray code. A gray code sequence must begin with 0.
 *
 * For example, given n = 2, return [0,1,3,2]. Its gray code sequence is:
 * 00 - 0
 * 01 - 1
 * 11 - 3
 * 10 - 2
 *
 * Note:
 * For a given n, a gray code sequence is not uniquely defined.
 *
 * For example, [0,2,3,1] is also a valid gray code sequence according to
 * the above definition.
 * For now, the judge is able to judge based on one instance of gray code sequence.
 */
public class Solution {
    public static List<Integer> grayCode(int n) {
        List<Integer> rs = new ArrayList<>((int)Math.pow(2,n));
        rs.add(0);
        for(int i=0;i<n;i++){
            int num = (int)Math.pow(2,i);
            for(int j=rs.size()-1;j>-1;j--){
                rs.add(rs.get(j)+num);
            }
        }
        return rs;
    }

    public static void main(String[] args) {
        List<Integer> l = grayCode(3);
        System.out.println(l.toString());
        l = grayCode(1);
        System.out.println(l.toString());
    }
}
