package LongestPalindromicSubstring_5;

/**
 * Created by Administrator on 2017/3/8.
 */
public class Solution
{
    // Palindromic means the same in backward.
    public static String longestPalindrome(String s)
    {
        return longestPStrAtRange(s,0,s.length());
    }

    // hi not inclusive
    public static String longestPStrAtRange(String s,int lo,int hi)
    {
        if(lo==hi)
            return null;
        int mid = (lo+hi)>>>1;
        String across = longestPStrAcrossPosi(s,mid);
        String left = longestPStrAtRange(s,lo,mid);
        String right = longestPStrAtRange(s,mid+1,hi);
        if(left==null)
        {
            if(right==null)
                return across;
            else
                return (across.length()>right.length()?across:right);
        }
        else if(right==null)
            return (across.length()>left.length()?across:left);
        else
            return (across.length()>left.length())?
                (across.length()>right.length()?across:right):
                (left.length()>right.length()?left:right);
    }

    // only check the next character for the same situation.
    public static String longestPStrAcrossPosi(String s, int p)
    {
        int temp = 1;
        int longest = 0;
        while(p-temp>-1&&(p+temp)<s.length()&&s.charAt(p+temp)==s.charAt(p-temp))
            temp++;
        longest = (temp<<1)-1;
        temp = p+1;
        int backup = p;
        while(p>-1&&temp<s.length()&&s.charAt(p)==s.charAt(temp))
        {
            p--;
            temp++;
        }
        //longest = Math.max(longest,temp-p-1);
        if(longest>temp-p-1) //
        {
            return s.substring(backup-(longest>>1),backup+(longest>>1)+1);
        }
        else
        {
            return s.substring(p+1,temp);
        }
    }

    /**
     * This is the optimal solution from the leetcode discussion.
     * This solution handles all situation subtly!
     * @param s
     * @return
     */
    public static String longestPalindrome2(String s)
    {
        // assure s not null
        int len = s.length();
        if(len==1) return s;
        int maxLen = 1;     // use max len & to max start to "memorize"
        int maxStart = 0;   // a longest palindrome
        for(int i=0;i<len;)
        {
            if(len-i<=(maxLen>>1)) break;//no need to keep searching.
            int lp = i,rp = i; // left pointer & right pointer
            while(rp+1<len&&(s.charAt(rp)==s.charAt(rp+1))) //skip duplicates
                rp++;
            // now lp and rp at the 2 sides of duplicate letters
            // or on the same position
            i = rp+1; // next begin search position.
            while(lp>0&&rp<len-1&&s.charAt(lp-1)==s.charAt(rp+1)) // expand the Palindrome
            {
                rp++;
                lp--;
            }
            if(rp-lp+1>maxLen)
            {
                maxLen = rp-lp+1;
                maxStart = lp;
            }
        }
        return s.substring(maxStart,maxStart+maxLen);
    }

    public static void main(String[] args)
    {
        String s = "aaaaabbabababababbbababababababa";
        System.out.println(s+", p: "+longestPalindrome(s));
        System.out.println(s+", p: "+longestPalindrome2(s));
    }
}
