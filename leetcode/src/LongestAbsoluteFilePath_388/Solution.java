package LongestAbsoluteFilePath_388;

import java.util.LinkedList;

/**
 * Suppose we abstract our file system by a string in the following manner:
 *
 * The string "dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext" represents:
 *
 * dir
 *    subdir1
 *    subdir2
 *        file.ext
 * The directory dir contains an empty sub-directory subdir1 and
 * a sub-directory subdir2 containing a file file.ext.
 *
 * The string "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2
 * \n\t\tsubsubdir2\n\t\t\tfile2.ext" represents:
 *
 * dir
 *    subdir1
 *       file1.ext
 *       subsubdir1
 *    subdir2
 *       subsubdir2
 *          file2.ext
 * The directory dir contains two sub-directories subdir1 and subdir2.
 * subdir1 contains a file file1.ext and
 * an empty second-level sub-directory subsubdir1.
 * subdir2 contains a second-level sub-directory
 * subsubdir2 containing a file file2.ext.
*
 * We are interested in finding the longest
 * (number of characters) absolute path to a file within our file system.
 * For example, in the second example above, the longest absolute path is
 * "dir/subdir2/subsubdir2/file2.ext", and its length is 32
 * (not including the double quotes).
*
 * Given a string representing the file system in the above format,
 * return the length of the longest absolute path to file in the abstracted file system.
 * If there is no file in the system, return 0.
*
 * Note:
 * The name of a file contains at least a . and an extension.
 * The name of a directory or sub-directory will not contain a ..
 * Time complexity required: O(n) where n is the size of the input string.
*
 * Notice that a/aa/aaa/file1.txt is not the longest file path,
 * if there is another path aaaaaaaaaaaaaaaaaaaaa/sth.png.
 */
public class Solution {
    public int lengthLongestPath2(String input) {
        // we assume the input strings are strictly valid
        LinkedList<Integer> dirs = new LinkedList<>();
        int begin = 0,len = input.length(),longest = 0;
        int dirlen = 0; // the current length of the directories prefix
        char[] s = input.toCharArray();
        while(begin<len){
            // count \t first
            int tcount = 0;
            while(s[begin]=='\t'){
                ++begin;
                ++tcount;
            }
            int end = begin;
            while(end<len&&s[end]!='\n'&&s[end]!='.') ++end;
            if(end==len) return longest; // the last directory is useless
            if(s[end]=='.'){
                // we got a file
                // search end
                while(end<len&&s[end]!='\n') ++end;
                // and the number of dirs should be one less than the tcount
                while(dirs.size()!=tcount) dirlen-=dirs.removeLast();
                longest = Math.max(dirlen+(end-begin),longest);
            }else{
                // then we must have a directory
                // set the directories to the right level
                while(dirs.size()!=tcount) dirlen-=dirs.removeLast();
                int currentDir = end-begin+1; // including slash
                dirlen+=currentDir;
                dirs.add(currentDir);
            }
            begin = end+1;
        }
        return longest;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.lengthLongestPath("dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext"));
    }

    // my solution is too naive...
    // optimal solution on leetcode
    public int lengthLongestPath(String input){
        String[] strs = input.split("\n");
        int[] dirlen = new int[input.length()+1];
        int longest = 0;
        for(String s:strs){
            int tcount = s.lastIndexOf('\t')+1;
            dirlen[tcount+1] = dirlen[tcount]+s.length()-tcount;
            if(s.contains(".")&&dirlen[tcount+1]+tcount>longest) longest = dirlen[tcount+1]+tcount;
        }
        return longest;
    }
}
