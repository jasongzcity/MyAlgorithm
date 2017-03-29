package DivideTwoInteger_29;

/**
 * Divide two integers without using multiplication, division and mod operator.
 *
 * If it is overflow, return MAX_INT.
 */
public class Solution
{
    public static int divide(int dividend, int divisor)
    {
        if(divisor==0||(dividend==Integer.MIN_VALUE&&divisor==-1)) return Integer.MAX_VALUE;// overflow
        if(divisor==1) return dividend; // optimization
        boolean isNegative = (dividend<0)^(divisor<0);
        int dvs=Math.abs(divisor),dvd=Math.abs(dividend),rs=0;
        while(dvd>=dvs) // no more multiple
        {
            int multiple = 1;
            int temp = dvs;
            while(dvd>=temp<<1)
            {
                temp<<=1;
                multiple<<=1;
            }
            dvd-=temp;   // get remainder, keep checking
            rs+=multiple;
        }
        return isNegative?-rs:rs;
    }

    // This long version has also been accepted.
    public static int divide_long_version(int dividend, int divisor)
    {
        if(divisor==0||(dividend==Integer.MIN_VALUE&&divisor==-1)) return Integer.MAX_VALUE;// overflow
        if(divisor==1) return dividend; // optimization
        boolean isNegative = (dividend<0)^(divisor<0);
        long dvs=Math.abs((long)divisor),dvd=Math.abs((long)dividend);
        int rs=0;
        while(dvd>=dvs) // no more multiple
        {
            int multiple = 1;
            long temp = dvs;
            while(dvd>=temp<<1)
            {
                temp<<=1;
                multiple<<=1;
            }
            dvd-=temp;   // get remainder, keep checking
            rs+=multiple;
        }
        return isNegative?-rs:rs;
    }

    // below is the original recursive version
    // surprisingly, its slower than the long version(see devide2_long_version)
    // two method is logically the same...
    // it is a problem of the OJ platform or JVM???
    public int divide2(int dividend, int divisor)
    {
        if(divisor==0||(dividend==Integer.MIN_VALUE&&divisor==-1)) return Integer.MAX_VALUE;// overflow
        if ((dividend == 0) || (dividend < divisor))	return 0;
        if(divisor==1) return dividend; // optimization
        boolean isNegative = (dividend<0)^(divisor<0);
        int ldividend = Math.abs(dividend);
        int ldivisor = Math.abs(divisor);

        int lans = ldivide(ldividend, ldivisor);
        return isNegative?-lans:lans;
    }

    private int ldivide(int ldividend, int ldivisor)
    {
        if (ldividend < ldivisor) return 0; // base

        int sum = ldivisor;
        int multiple = 1;
        while ((sum+sum) <= ldividend)
        {
            sum += sum;
            multiple += multiple;
        }
        //Look for additional value for the multiple from the reminder (dividend - sum) recursively.
        return multiple + ldivide(ldividend - sum, ldivisor);
    }

    // below is the recursive version of the accepted java solution
    public int divide2_long_version(int dividend, int divisor)
    {
        if(divisor==0||(dividend==Integer.MIN_VALUE&&divisor==-1)) return Integer.MAX_VALUE;// overflow
        if(divisor==1) return dividend; // optimization
        boolean isNegative = (dividend<0)^(divisor<0);
        long ldividend = Math.abs((long)dividend);
        long ldivisor = Math.abs((long)divisor);
        if ((ldividend == 0) || (ldividend < ldivisor))	return 0;// optimization

        long lans = ldivide(ldividend, ldivisor);
        return isNegative?-(int)lans:(int)lans;
    }

    private long ldivide(long ldividend, long ldivisor)
    {
        if (ldividend < ldivisor) return 0; // base

        long sum = ldivisor;
        int multiple = 1;
        while (sum<<1<=ldividend)
        {
            sum<<=1;
            multiple<<=1;
        }
        //Look for additional value for the multiple from the reminder (dividend - sum) recursively.
        return (long)multiple + ldivide(ldividend - sum, ldivisor);
    }
}
