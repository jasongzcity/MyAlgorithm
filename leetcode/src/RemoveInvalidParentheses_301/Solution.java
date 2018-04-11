package RemoveInvalidParentheses_301;

import java.util.*;

/**
 * Remove the minimum number of invalid parentheses in order to
 * make the input string valid. Return all possible results.
 *
 * Note: The input string may contain letters other than the parentheses ( and ).
 *
 * Examples:
 * "()())()" -> ["()()()", "(())()"]
 * "(a)())()" -> ["(a)()()", "(a())()"]
 * ")(" -> [""]
 */
public class Solution {

    // second session
    // thought:
    // "valid": the number of left parentheses never drop belows 0
    // and should be 0 at the end of the string.
    // we delete the minimum parentheses according the "balance"
    // rule.
    // most voted on leetcode
    public List<String> removeInvalidParenthesesII(String s){
        List<String> rs = new ArrayList<>();
        remove2(s, 0,0, new char[]{'(', ')'}, rs);
        return rs;
    }

    // if arr = {'(', ')'} we deal with invalid ')'
    //        = {')', '('} we deal with '('
    private void remove2(String s, int last_pos, int last_del, char[] arr, List<String> rs){
        for(int i=last_pos, stack=0;i<s.length();i++){
            // use variable "stack" to check invalid parentheses.
            if(s.charAt(i)==arr[0]) ++stack;
            else if(s.charAt(i)==arr[1]) --stack;
            if(stack>=0) continue;
            // now we need to delete some parentheses
            // last_del is actually last deletion position plus 1.
            for(int del=last_del;del<=i;del++){
                // avoid producing duplicates
                if(s.charAt(del)==arr[1]&&(del==last_del||s.charAt(del)!=s.charAt(del-1)))
                    remove2(s.substring(0, del)+s.substring(del+1), i, del, arr, rs);
            }
            return;
        }

        // now the string is valid!
        String rev = new StringBuilder(s).reverse().toString();
        // check it in reverse direction
        if(arr[0]=='(') remove2(rev, 0, 0, new char[]{')', '('}, rs);
        else rs.add(rev);
    }

    public List<String> removeInvalidParentheses(String s) {
        List<String> rs = new ArrayList<>(s.length());
        StringBuilder sb = new StringBuilder(s.length());
        char[] sa = s.toCharArray();
        // check number of invalid parentheses first
        int left = 0,right = 0;
        for(char c:sa){
            if(c=='(') left++;
            else if(c==')'){
                if(left==0) ++right;
                else --left;
            }
        }
        if(left==0&&right==0) rs.add(s);
        else dfs(sa,rs,0,0,left,right,sb);
        return rs;
    }

    private void dfs(char[] s,List<String> rs,int stack,int begin,
                     int left,int right,StringBuilder sb){
        int len = s.length,curLen = sb.length();
        for(int i=begin;i<len&&len-i>=left+right;i++){
            if(s[i]==')'){
                if(right>0&&(i==begin||s[i]!=s[i-1])){
                    // skip duplicate situations.
                    // delete all the right parentheses first
                    dfs(s,rs,stack,i+1,left,right-1,sb);
                }
                // not delete
                // if stack == 0, this right parentheses must be deleted
                // set it invalid and break, no more checking
                if(--stack<0) break;
            }else if(s[i]=='('){
                if(right==0&&left>0&&(i==begin||s[i]!=s[i-1])){
                    // delete
                    dfs(s,rs,stack,i+1,left-1,right,sb);
                }
                // not delete
                ++stack;
            }
            sb.append(s[i]);
        }
        // check if valid
        if(left+right==0&&stack==0) rs.add(sb.toString());
        sb.delete(curLen,sb.length()); // backtrack builder
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.removeInvalidParenthesesII("(a)())()"));
        System.out.println(s.removeInvalidParentheses(")("));
        System.out.println(s.removeInvalidParentheses("())"));
        System.out.println(s.removeInvalidParentheses("(()"));
        System.out.println(s.removeInvalidParentheses("((()))))()()())(("));
        System.out.println(s.removeInvalidParentheses(""));
//        ((())()()()), ((()))(()()), ((()))()(()), ((()))()()()
    }

    // another 2ms solution
    public List<String> removeInvalidParentheses2(String s) {
        List<String> ans = new ArrayList<>();
        char[] para = new char[]{'(', ')'};
        remove(s, 0, 0, para, ans);
        return ans;
    }

    void remove(String s, int lastI, int lastJ, char[] para, List<String> ans) {
        int count = 0;
        for (int i = lastI; i < s.length(); i++) {
            if (s.charAt(i) == para[0]) {
                count++;
            }
            if (s.charAt(i) == para[1]) {
                count--;
            }
            if (count < 0) {
                for (int j = lastJ; j <= i; j++) {
                    if (s.charAt(j) == para[1] &&(j == 0 ||
                            s.charAt(j - 1) != para[1])) {
                        remove(s.substring(0, j) + s.substring(j + 1), i,
                                j, para, ans);
                    }
                }
                return;
            }
        }
        // finished remove invalid parentheses
        String reversed = new StringBuilder(s).reverse().toString();
        if(para[0] == '(') {
            remove(reversed, 0, 0, new char[] {')', '('}, ans);
        } else {
            ans.add(reversed);
        }
    }

    // another BFS solution
    // though slow, the idea is really interesting!
    public List<String> removeInvalidParentheses3(String s) {
        List<String> res = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        visited.add(s);
        queue.add(s);
        boolean isFound = false;
        while(!queue.isEmpty()){
            int size = queue.size();
            for(int i = 0; i < size; i ++){
                String cur = queue.poll();
                if(isValid(cur)) {
                    res.add(cur);
                    isFound = true;
                }
                if(isFound) continue;
                for(int j = 0; j < cur.length(); j ++){
                    if(cur.charAt(j) != '(' && cur.charAt(j) != ')') continue;
                    String neighbor = cur.substring(0, j) + cur.substring(j + 1);
                    if(!visited.contains(neighbor)){
                        queue.offer(neighbor);
                        visited.add(neighbor);
                    }
                }
            }
            if(isFound) break;
        }
        return res;
    }

    private boolean isValid(String s){
        int count = 0;
        char[] chars = s.toCharArray();
        for(char c: chars){
            if(c == '(') count ++;
            else if(c == ')') count --;
            if(count < 0) return false;
        }
        return count == 0;
    }
}
