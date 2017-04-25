package TextJustification_68;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an array of words and a length L,
 * format the text such that each line has exactly L characters
 * and is fully (left and right) justified.
 *
 * You should pack your words in a greedy approach;
 * that is, pack as many words as you can in each line.
 * Pad extra spaces ' ' when necessary so that each line has exactly L characters.
 *
 * Extra spaces between words should be distributed as evenly as possible.
 * If the number of spaces on a line do not divide evenly between words,
 * the empty slots on the left will be assigned more spaces than the slots on the right.
 *
 * For the last line of text, it should be left justified and no extra space
 * is inserted between words.
 *
 * For example,
 * words: ["This", "is", "an", "example", "of", "text", "justification."]
 * L: 16.
 * Return the formatted lines as:
 * [
 *   "This    is    an",
 *   "example  of text",
 *   "justification.  "
 * ]
 * Note: Each word is guaranteed not to exceed L in length.
 *
 * Corner Cases:
 *
 * A line other than the last line might contain only one word.
 * What should you do in this case?
 * In this case, that line should be left-justified.
 */
public class Solution {
    public static List<String> fullJustify(String[] words, int L) {
        List<String> rs = new ArrayList<>(words.length);
        StringBuilder sb = new StringBuilder(L);
        int begin=0,end;
        while(begin<words.length){
            end = begin;
            int len = words[end++].length();
            while(end<words.length&&len<=L) len += words[end++].length()+1;

            if(len>L){ // the last word at end-1 is redundant.
                --end;
                len -= words[end].length()+1;
                int wordNum = end - begin;
                int spacesToFill = L - len;
                if(wordNum!=1){
                    int spacesPlus = spacesToFill/(wordNum-1);
                    int remainder = spacesToFill%(wordNum-1);
                    sb.append(words[begin++]); // add first
                    while(begin<end){
                        for (int j = 0; j <= spacesPlus; j++) sb.append(' ');
                        if(remainder>0){
                            sb.append(' ');
                            remainder--;
                        }
                        sb.append(words[begin++]);
                    }
                }else{
                    sb.append(words[begin++]);
                    for(int j=0;j<spacesToFill;j++) sb.append(' ');
                }
            }else{ // end out of bound,the last line
                sb.append(words[begin++]);
                while(begin<end) sb.append(' ').append(words[begin++]);
                for (int j = 0; j < L - len; j++) sb.append(' ');
            }
            rs.add(sb.toString());
            sb.delete(0,sb.length()); // clear
        }
        return rs;
    }

    // alike DP solution
    public static List<String> fullJustifyDP(String[] words, int width) {
        int cost[][] = new int[words.length][words.length];
        List<String> res =  new ArrayList<>();
        for(int i=0 ; i < words.length; i++){
            cost[i][i] = width - words[i].length();
            for(int j=i+1; j < words.length; j++){
                cost[i][j] = cost[i][j-1] - words[j].length() - 1;
            }
        }
        for(int[] a:cost)
            System.out.println(Arrays.toString(a));
        System.out.println();

        for(int i=0; i < words.length; i++){
            for(int j=i; j < words.length; j++){
                if(cost[i][j] < 0){
                    cost[i][j] = Integer.MAX_VALUE;
                }else{
                    //cost[i][j] = (int)Math.pow(cost[i][j], 2);
                }
            }
        }

        for(int[] a:cost)
            System.out.println(Arrays.toString(a));
        System.out.println();

        int minCost[] = new int[words.length];
        int result[] = new int[words.length];
        for(int i = words.length-1; i >= 0 ; i--){
            minCost[i] = cost[i][words.length-1];
            result[i] = words.length;
            for(int j=words.length-1; j > i; j--){
                if(cost[i][j-1] == Integer.MAX_VALUE){
                    continue;
                }
                if(minCost[i] > minCost[j] + cost[i][j-1]){
                    minCost[i] = minCost[j] + cost[i][j-1];
                    result[i] = j;
                }
            }
        }
        int i = 0;
        int j;

        do{
            j = result[i];
            StringBuilder builder = new StringBuilder();
            for(int k=i; k < j; k++){
                builder.append(words[k] + " ");
            }
            res.add(builder.toString());

            i = j;
        }while(j < words.length);

        for(int[] a:cost)
            System.out.println(Arrays.toString(a));
        System.out.println();
        System.out.println(Arrays.toString(minCost));
        System.out.println();
        System.out.println(Arrays.toString(result));

        return res;
    }

    public static void main(String[] args) {
        String[] words = new String[]{
                "This","is","an","example","of","text","justification"
        };
//        List<String> l = fullJustifyDP(words,16);
//        for (String s:l) System.out.println(s);
        List<String> l = fullJustify(words,16);
        for(String s:l) System.out.println(s);
    }
}
