package ReverseInteger_7;

import java.util.Stack;

/**
 * Created by Administrator on 2017/3/10.
 */
public class Solution
{
    public static int reverse(int x)
    {
        int result = 0;
        while(x!=0)
        {
            int tail = x%10;  // get the last digit
            x/=10;
            int newResult = result*10+tail;
            if((newResult-tail)/10!=result)//doing backward to check overflow
                return 0;
            result = newResult;
        }
        return result;
    }

    // this method will use long to escape overflow problems
    public static int reverse2(int x)
    {
        long result = 0;
        while(x!=0)
        {
            result = result*10+x%10;
            x /= 10;
        }
        if(result > Integer.MAX_VALUE||result<Integer.MIN_VALUE)
            return 0;
        return (int)result;
    }

    public static void main(String[] args)
    {
        int i = 4896785;
        System.out.println("origin: "+i+", reverse:"+reverse(i));
        i = 182849392;
        System.out.println("origin: "+i+", reverse:"+reverse(i));
        i = 1000203043; // this will overflow
        System.out.println("origin: "+i+", reverse:"+reverse(i));
        i = -83728900;
        System.out.println("origin: "+i+", reverse:"+reverse(i));
        i = 1534236469;
        System.out.println("origin: "+i+", reverse:"+reverse(i));
        i = 2147483647;
        System.out.println("origin: "+i+", reverse:"+reverse(i));
    }
}
