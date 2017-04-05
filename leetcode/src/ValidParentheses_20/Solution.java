package ValidParentheses_20;

import java.util.Stack;

/**
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']',
 * determine if the input string is valid.
 *
 * The brackets must close in the correct order, "()" and "()[]{}"
 * are all valid but "(]" and "([)]" are not.
 */
public class Solution
{
    /*
     * This is a classic problem which can be easily solved by
     * using a stack.
     */
    public static boolean isValid(String s)
    {
        Stack<Character> stack = new Stack<>();
        for(int i=0;i<s.length();i++)
        {
            char c = s.charAt(i);
            if(c==')'||c=='}'||c==']')
            {
                if(stack.empty()) return false;
                char top = stack.pop();
                if(c==')')
                {
                    if(top!='(') return false;
                }
                else if(c==']')
                {
                    if(top!='[') return false;
                }
                else
                {
                    if(top!='{') return false;
                }
            }
            else // containing just '(', ')', '{', '}', '[' and ']'
                stack.push(c);
        }
        return stack.empty();
    }

    // better solution on leetcode.
    public static boolean isValid2(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(')
                stack.push(')');
            else if (c == '{')
                stack.push('}');
            else if (c == '[')
                stack.push(']');
            else if (stack.isEmpty() || stack.pop() != c)
                return false;
        }
        return stack.empty();
    }

    public static void main(String[] args)
    {
        String s = "{[}]";
        System.out.println(isValid(s));
    }
}
