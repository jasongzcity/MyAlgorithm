package EvaluateReversePolishNotion_150;

import java.util.Stack;

/**
 * Evaluate the value of an arithmetic expression in Reverse Polish Notation.
 * Valid operators are +, -, *, /. Each operand may be an integer or another expression.
 *
 * Some examples:
 *
 * ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
 * ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6
 */
public class Solution {
    public static int evalRPN(String[] t) {
        Stack<Integer> s = new Stack<>();
        for(int i=0;i<t.length;i++){
            char c = t[i].charAt(0);
            if(c>='0'||t[i].length()>1){
                s.push(Integer.parseInt(t[i]));
            }else{
                Integer two = s.pop();
                Integer one = s.pop();
                if(c=='+') s.push(one+two);
                else if(c=='-') s.push(one-two);
                else if(c=='*') s.push(one*two);
                else s.push(one/two);
            }
        }
        return s.peek();
    }

    public static void main(String[] args) {
        String[] a = new String[]{"4", "13", "5", "/", "+"};
        System.out.println(evalRPN(a));
    }
}
