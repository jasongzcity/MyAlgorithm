package IntegerToRoman_12;

/**
 * Created by Administrator on 2017/3/10.
 */
public class Solution
{
    public static String intToRoman(int num)
    {
        String[] MS = {"","M","MM","MMM"};
        String[] CS = {"","C","CC","CCC","CD","D","DC","DCC","DCCC","CM"};
        String[] XS = {"","X","XX","XXX","XL","L","LX","LXX","LXXX","XC"};
        String[] IS = {"","I","II","III","IV","V","VI","VII","VIII","IX"};
        return MS[num/1000]+CS[(num%1000)/100]+XS[(num%100)/10]+IS[num%10];
    }
}
