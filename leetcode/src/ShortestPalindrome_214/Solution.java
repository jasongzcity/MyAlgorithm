package ShortestPalindrome_214;

/**
 * Given a string S, you are allowed to convert it to a palindrome
 * by adding characters in front of it.
 * Find and return the shortest palindrome you can find by
 * performing this transformation.
 */
public class Solution
{
    public static String shortestPalindrome(String s)
    {
        int len = s.length();
        if(len==0) return "";
        int midpoint = len>>1,offset=0;
        int head=midpoint-1,tail=midpoint+1;
        String newStr = new String(s);
        //boolean checkBeside = false;
        while(true)
        {
            while(head>-1&&tail<len&&s.charAt(head)==s.charAt(tail))
            {
                head--;
                tail++;
            }
            if(head==-1) // get shortest palindrome!
            {
                while(tail<len)
                {
                    newStr = s.charAt(tail) + newStr;
                    tail++;
                }
                return newStr;
            }
            if(s.charAt(midpoint)==s.charAt(midpoint-offset-1)) // ignore midpoint-offset-1>-1
            {
                offset++;
                tail = midpoint+1;
                head = midpoint-offset-1;
            }
            else
            {
                midpoint -= offset+1;
                offset = 0;
                //head = tail = midpoint;
                head = midpoint-1;
                tail = midpoint+1;
            }
//            if(midpoint>0&&s.charAt(midpoint)==s.charAt(midpoint-1)&&!checkBeside)
//            {
//                checkBeside = true;
//                tail = midpoint;
//                head = midpoint-1;
//            }
//            else
//            {
//                midpoint--;
//                checkBeside = false;
//                tail = midpoint;
//                head = midpoint;
//            }
        }
    }

    public static void main(String[] args)
    {
//        String s = "abcd";
//        String output = shortestPalindrome(s);
//
//        String temp = "aaaaa";
//        String longa = "";
//        for(int i=0;i<10;i++)
//        {
//            longa += temp;
//        }
//        output = shortestPalindrome(longa);
//        System.out.println(output+", length: "+output.length());
        String pattern = "abababcab";
        for(int i:getKMPTable(pattern))
            System.out.print(i+" ");
        System.out.println();
    }

    // This is the cleanest solution from leetcode
    // So elegant..
    // See explanation in "thoughts.txt"
    public static String shortestPalindrome2(String s)
    {
        int axis = 0;
        int len = s.length();
        for(int i=len-1;i>-1;i--)
            if(s.charAt(axis)==s.charAt(i)) axis++;
        if(axis==len) return s;// the s itself is a palindrome
        String sub = s.substring(axis);
        StringBuilder sb = new StringBuilder(sub);
        sb.reverse().append(shortestPalindrome2(s.substring(0,axis))).append(sub);
        return sb.toString();
    }

    // This method use a kmp table to check palindrome
    // Also an elegant method from leetcode
    public static String shortestPalindrome3(String s)
    {
        // This method concat a reverse String to find the
        // palindrome part of the String s itself.
        // So we only need to concat the "non-palindrome" part at
        // the beginning of s.
        String temp = s+'#'+new StringBuilder(s).reverse().toString();
        int[] kmp = getKMPTable(temp);
        return new StringBuilder(s.substring(kmp[kmp.length-1])).reverse().append(s).toString();
    }

    // get KMP Table of String s
    static int[] getKMPTable(String s)
    {
        int[] rs = new int[s.length()];// initially all 0
        int index = 0;//index stands for the last index of currently matched prefix.
        for(int i=1;i<s.length();i++)
        {
            if(s.charAt(i)==s.charAt(index)) // if matched, record the match length +1
            {
                rs[i] = rs[i-1]+1;
                index++;
            }
            else
            {
                index = rs[i-1];
                while(index>0&&s.charAt(index)!=s.charAt(i)) index = rs[index-1]; // search back

                // now index either at boundary(0) or find a match char with i
                if(s.charAt(index)==s.charAt(i)) index++; // if matched, extend.
                rs[i] = index;
            }
        }
        return rs;
    }
}
