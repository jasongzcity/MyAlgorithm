package LongestValidParentheses_32;

import java.util.Stack;

/**
 * Given a string containing just the characters '(' and ')',
 * find the length of the longest valid (well-formed) parentheses substring.
 *
 * For "(()", the longest valid parentheses substring is "()", which has length = 2.
 *
 * Another example is ")()())", where the longest valid parentheses
 * substring is "()()", which has length = 4.
 */
public class Solution
{
    public static int longestValidParentheses(String s)
    {
        int longest = 0,currentCount = 0;
        Stack<Integer> stack = new Stack<>();
        for(int i=0;i<s.length();i++)
        {
            char c = s.charAt(i);
            if(c=='(')
            {
                stack.push(i-currentCount);
                currentCount = 0;
            }
            else // ')'
            {
                if(stack.empty()) currentCount = 0;  // invalid,reset
                else
                {
                    currentCount = i-stack.pop()+1;
                    if(currentCount>longest) longest = currentCount;
                }
            }
        }
        return longest;
    }

    public static void main(String[] args)
    {
        System.out.println(longestValidParentheses("()()()(()(()(())))"));
        System.out.println(longestValidParentheses("(()()"));
        System.out.println(longestValidParentheses("()()()((()())"));
    }
}
