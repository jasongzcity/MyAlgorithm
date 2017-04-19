package SimplifyPath_71;

import java.util.Stack;

/**
 * Given an absolute path for a file (Unix-style), simplify it.
 *
 * For example,
 * path = "/home/", => "/home"
 * path = "/a/./b/../../c/", => "/c"
 *
 * Corner Cases:
 * Did you consider the case where path = "/../"?
 * In this case, you should return "/".
 *
 * Another corner case is the path might contain multiple slashes '/' together,
 * such as "/home//foo/".
 * In this case, you should ignore redundant slashes and return "/home/foo".
 */
public class Solution {
    public static String simplifyPath(String path) {
        if(path.length()==0) return path;
        StringBuilder sb = new StringBuilder(path.length());
        // use some flags to represent status.
        boolean slash = false,dot = false;
        for (int i = 0; i < path.length(); i++) {
            char c = path.charAt(i);
            if(c=='/'){
                dot = false;
                slash = true;
            }else if(c=='.'){
                if(dot){
                    if(i<path.length()-1&&path.charAt(i+1)!='/'){
                        sb.append('/').append('.').append('.');
                        dot = false;
                    }else{ // step back
                        int lastSlash = sb.lastIndexOf("/");
                        if (lastSlash != -1) {
                            sb.delete(lastSlash, sb.length());
                            dot = false;
                        }
                    }
                }else if(slash){
                    dot = true;
                    slash = false;
                }else{
                    sb.append(c);
                }
            }else{ // just add to Builder
                if(slash) {
                    sb.append('/');
                    slash = false;
                }else if(dot){
                    sb.append('/').append('.');
                    dot = false;
                }
                sb.append(c);
            }
        }
        return sb.length()==0?"/":sb.toString();
    }

    public static void main(String[] args) {
        String s = "/a/./b/../../c/";
        System.out.println(simplifyPath(s));
        s = "/home//foo/";
        System.out.println(simplifyPath(s));
        s = "/../";
        System.out.println(simplifyPath(s));
        s = "/...";
        System.out.println(simplifyPath(s));
    }
}
