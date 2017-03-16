package ZigZagConversion_6;

/**
 * Created by Administrator on 2017/3/9.
 */
public class Solution
{
    public static String convert(String s, int numRows)
    {
        if(numRows==1) return s;
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<numRows;i++)
        {
            int jumper = i;
            while(jumper<s.length())
            {
                sb.append(s.charAt(jumper));
                if(i==0||i==numRows-1)
                    jumper = jumper+(numRows<<1)-2;// plus 2n-2
                else
                {
                    jumper = jumper+((numRows<<1)-2)-(i<<1);
                    if(jumper<s.length())
                        sb.append(s.charAt(jumper));
                    jumper = jumper+(i<<1);
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args)
    {
        String s = "PAYPALISHIRING";
        System.out.println("origin:"+s+", convert3rows:"+convert(s,3));
        System.out.println("origin:"+s+", convert2rows:"+convert(s,2));

        s = "CHECKTHISLONGSTRINGOUTWHYDONTYOUTRYITITMUSTBEFUN";
        String str = convert(s,7);
        System.out.println("origin:"+s+",length:"+s.length()+
                ", convert7rows:"+str+", length:"+str.length());

    }
}
