package RestoreIPAddress_93;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a string containing only digits,
 * restore it by returning all possible valid IP address combinations.
 *
 * For example:
 * Given "25525511135",
 *
 * return ["255.255.11.135", "255.255.111.35"]. (Order does not matter)
 */
public class Solution {
    public static List<String> restoreIpAddresses(String s) {
        int len = s.length();
        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder(len+3);
        getNextPosition(1,0,s,sb,list);
        return list;
    }

    private static void getNextPosition(int p,int begin,String s,
                                        StringBuilder sb,List<String> rs){
        if(p==5){
            if(begin==s.length()) rs.add(sb.toString());
            return;
        }

        if(p!=1) sb.append('.');

        for(int i=1;i<=3&&begin+i<=s.length();i++){
            if(s.charAt(begin)=='0'){
                sb.append('0');
                getNextPosition(p+1,begin+1,s,sb,rs);
                sb.delete(sb.length()-1,sb.length());
                break;
            }
            String temp = s.substring(begin,begin+i);
            int posi = Integer.parseInt(temp);
            if(posi>255) break;
            sb.append(temp);
            getNextPosition(p+1,begin+i,s,sb,rs);
            sb.delete(sb.length()-i,sb.length()); // backtrack
        }

        if(p!=1) sb.delete(sb.length()-1,sb.length()); // remove dot
    }

    public static void main(String[] args) {
        List<String> l = restoreIpAddresses("25525511135");
        System.out.println(l.toString());
        l = restoreIpAddresses("127001");
        System.out.println(l.toString());
    }
}
