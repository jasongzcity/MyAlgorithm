package StringToInt_8;

/**
 * Created by Administrator on 2017/3/10.
 */
public class Solution
{
    /* must consider all input. */
    /*
    I think we only need to handle four cases:

    discards all leading whitespaces
    sign of the number
    overflow
    invalid input

     I just thought I have to consider Hex and Oct expression...
     */
    public static int myAtoi(String str)
    {
        int p = 0;
        int result = 0;
        boolean positive = true;//default true
        while(p<str.length()&&str.charAt(p)==' ') // get rid of whitespaces
            p++;
        if(p<str.length()&&(str.charAt(p)=='-'||str.charAt(p)=='+'))
        {
            positive = str.charAt(p) == '-' ? false : true;
            p++;
        }
        while(p<str.length()&&str.charAt(p)>='0'&&str.charAt(p)<='9')
        {
            int digit = str.charAt(p) - '0'; // get digit
            if(Integer.MAX_VALUE/10<result||
                    Integer.MAX_VALUE/10==result&&digit>Integer.MIN_VALUE%10)
            {
                if(positive) return Integer.MAX_VALUE;
                else return Integer.MIN_VALUE;
            }
            p++;
            result = result*10+digit;
        }
        if(positive) return result;
        else return -result;
    }

    public static void main(String[] args)
    {
        String s = "-2323233";
        System.out.println("input: "+s+", output:"+myAtoi(s));
    }
}
