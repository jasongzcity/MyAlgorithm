package GenerateParentheses_22;

import java.util.ArrayList;
import java.util.List;

/**
 * Given n pairs of parentheses, write a function to generate
 * all combinations of well-formed parentheses
 */
public class Solution
{
    public static List<String> generateParenthesis(int n)
    {
        List<String> rs = new ArrayList<>();
        StringBuilder sb = new StringBuilder(n<<1);
        sb.setLength(n<<1);
        sb.setCharAt(0,'(');
        generateParentheses(1,n-1,1,rs,sb);
        return rs;
    }

    /**
     * Recursive method to generate the Nth char of the string.
     * @param n the nth position
     * @param lpleft left parentheses left to give.
     * @param lphad left parentheses already had. Restricts when giving right parentheses.
     * @param rs return strings in the list
     * @param sb generated string in previous recursion.
     **/
    static void generateParentheses(int n,int lpleft,int lphad,
                                   List<String> rs,StringBuilder sb)
    {
        if(lphad==1&&lpleft==0) // base
        {
            sb.setCharAt(n,')');
            rs.add(sb.toString());
        }
        if(lphad>0)             // put right parenthesis
        {
            sb.setCharAt(n,')');
            generateParentheses(n+1,lpleft,lphad-1,rs,sb);
        }
        if(lpleft>0)            // put left parenthesis
        {
            sb.setCharAt(n,'(');
            generateParentheses(n+1,lpleft-1,lphad+1,rs,sb);
        }
    }

    public static void main(String[] args)
    {
        List<String> list = generateParenthesis(3);
        System.out.println(list);
    }
}
