package ValidPalindrome_125;


public class Solution
{
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
