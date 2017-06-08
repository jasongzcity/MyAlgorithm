package OneEditDistance_161;

/**
 * Given two strings S and T, determine if they are both one edit distance apart.
 */
public class Solution {
    public boolean isOneEditDistance(String s, String t){
        int slen = s.length(),tlen = t.length();
        if(slen-tlen>1||slen-tlen<-1) return false;
        if(slen==tlen){
            int count = 0;
            for(int i=0;i<s.length();i++)
                if(s.charAt(i)!=t.charAt(i))
                    ++count;
            return count==1;
        }else{
            String longer = slen>tlen?s:t,shorter = slen>tlen?t:s;
            int sp=0,lp=0;
            for(;lp-sp<2&&sp<shorter.length();sp++,lp++)
                if(longer.charAt(lp)!=shorter.charAt(sp))
                    sp--; // shorter pointer stays
            return lp-sp<2;
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.isOneEditDistance("abc","acc"));
        System.out.println(s.isOneEditDistance("",""));
        System.out.println(s.isOneEditDistance("abc","add"));
        System.out.println(s.isOneEditDistance("ab","abc"));
        System.out.println(s.isOneEditDistance("abcd","aabcd"));
        System.out.println(s.isOneEditDistance("abcd","qabcd"));
        System.out.println(s.isOneEditDistance("","a"));
        System.out.println(s.isOneEditDistance("ab","adc"));
    }
}
