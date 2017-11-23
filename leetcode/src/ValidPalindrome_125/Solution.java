package ValidPalindrome_125;


public class Solution
{
    // Second session
    // most intuitive way
    // Notice: another solution is to use regular expression!
    public static boolean isPalindromeII(String str){
        char[] s = str.toCharArray();
        for(int l=0,r=s.length-1;l<r;){
            if(s[l]>='A'&&s[l]<='Z') s[l] += 'a' - 'A';
            if(s[r]>='A'&&s[r]<='Z') s[r] += 'a' - 'A';
            if(!(s[l]>='0'&&s[l]<='9')&&!(s[l]>='a'&&s[l]<='z')){
                ++l;
                continue;
            }
            if(!(s[r]>='0'&&s[r]<='9')&&!(s[r]>='a'&&s[r]<='z')){
                --r;
                continue;
            }
            if(s[r]!=s[l]) return false;
            ++l;
            --r;
        }
        return true;
    }

    public static boolean isPalindrome(String s)
    {
        if(s==null) return false;
        int len = s.length();
        if(len==0) return true;
        char diff = 'a'-'A';
        for(int hp=0,tp=len-1;hp<tp;)
        {
            char head = s.charAt(hp);
            char tail = s.charAt(tp);
            if(!(head>='a'&&head<='z')&&!(head>='A'&&head<='Z')
                    &&!(head>='0'&&head<='9'))// not alphanumeric
            {
                hp++;
                continue;
            }
            if(!(tail>='a'&&tail<='z')&&!(tail>='A'&&tail<='Z')
                    &&!(tail>='0'&&tail<='9'))// not alphanumeric
            {
                tp--;
                continue;
            }
            if(head>'Z') head -= diff;
            if(tail>'Z') tail -= diff;// to upper case
            if(head!=tail)
            {
                return false;   // not alphabetically the same
            }
            hp++;
            tp--;
        }
        return true;
    }

    public static void main(String[] args)
    {
        String s = "0P";
        isPalindrome(s);
    }
}
