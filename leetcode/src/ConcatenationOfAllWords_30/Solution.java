package ConcatenationOfAllWords_30;

import java.util.*;

/**
 *  You are given a string, s, and a list of words, words,
 *  that are all of the same length.
 *  Find all starting indices of substring(s) in s that
 *  is a concatenation of each word in words exactly once and
 *  without any intervening characters.
 *
 *  For example, given:
 *  s: "barfoothefoobarman"
 *  words: ["foo", "bar"]
 *
 *  You should return the indices: [0,9].
 *  (order does not matter).
 */
public class Solution
{
    // This solution is transformed from the highest vote solution on leetcode.
    public static List<Integer> findSubstring(String s, String[] words)
    {
        // Assure s and words not null
        List<Integer> rs = new ArrayList<>();
        int strLen = s.length(),wcount = words.length;
        if(strLen==0||wcount==0) return rs;
        int wl = words[0].length();
        Map<String,Integer> strCount = new HashMap<>();
        for(String str:words)
            strCount.put(str,strCount.getOrDefault(str,0)-1);
        Map<String,Integer> strCountCurrent = new HashMap<>(words.length);

        for(int i=0;i<wl;i++) // The outer loop, to check if this beginning of indice valid
        {
            int left=i,count=0;
            strCountCurrent.putAll(strCount); // reset
            for(int j=i;j<=strLen-wl;j+=wl) // inner loop, to check each substring
            {
                String current = s.substring(j,j+wl);
                Integer curStrCount = strCountCurrent.get(current);
                if(curStrCount!=null) // this is a valid substring
                {
                    strCountCurrent.put(current,curStrCount+1);
                    count++;
                    if(strCountCurrent.get(current)==1) // too much
                    {
                        while(strCountCurrent.get(current)!=0)
                        {
                            String reduce = s.substring(left,left+wl);
                            strCountCurrent.put(reduce,strCountCurrent.get(reduce)-1);
                            count--;
                            left += wl;
                        }
                    }
                    if(count==wcount) // now, each word occurs exactly once in the substring.
                    {
                        // record the beginning and check next.
                        rs.add(left);
                        String reduce = s.substring(left,left+wl);
                        strCountCurrent.put(reduce,strCountCurrent.get(reduce)-1);
                        left+=wl;
                        count--;
                    }
                }
                else // not a valid substring, reset, begin as new substring.
                {
                    strCountCurrent.putAll(strCount);
                    left = j+wl;//the beginning jumps a word len because the current is no more valid.
                    count = 0;
                }
            }
        }
        return rs;
    }

    public static void main(String[] args)
    {
        String[] words = new String[]{"foo","bar","the"};
        String s = "barfoofoobarthefoobar";
        System.out.println(findSubstring(s,words));
        words = new String[]{"word","good","best","good"};
        s = "wordgoodgoodgoodbestword";
        System.out.println(findSubstring(s,words));
    }

    // There is one solution on leetcode, which is so much faster than the solution
    // above. I think it is because the solution above relies on hashmap heavily(so
    // does many other solutions on leetcode)
    // thus it do a lot of hashing and notice that: the hashing of String
    // is slow(though O(1) theoretically),especially when the string is long.
    // I am copying the amazingly fast solution below, change some of its naming
    // and explain it in comment.
    public static List<Integer> findSubstring2(String s, String[] words)
    {
        int strlen = s.length();
        List<Integer> rs = new ArrayList<Integer>(s.length());
        if (words.length == 0) {
            return rs;
        }
        int wordlen = words[0].length();
        if (strlen < wordlen * words.length) {
            return rs;
        }
        int last = strlen - wordlen + 1; // the last index to check

        // The code below builds up a matrix and a map.
        // The first vector of the matrix is for words array.
        // The second vector for scanning.
        // The index in the vector equals to the index of in words array.
        // The values in the matrix stands for the times of occurrence
        // of the word.
        // Variable uniqueWord indicates unique words in words array.
        Map<String, Integer> mapping = new HashMap<String, Integer>(words.length);
        int [][] table = new int[2][words.length];
        int uniqueWord = 0, index = 0;
        for (int i = 0; i < words.length; ++i) {
            Integer mapped = mapping.get(words[i]);
            if (mapped == null) {
                ++uniqueWord;
                mapping.put(words[i], index);
                mapped = index++;
            }
            ++table[0][mapped];
        }

        // The smapping is a mapping from index(and the wordlen length word it stands for)
        // to the index in the matrix.
        // So, when we scan the String, we don't need to look up the
        // word in the map repeatedly.
        int [] smapping = new int[last];
        for (int i = 0; i < last; ++i) {
            String section = s.substring(i, i + wordlen);
            Integer ind = mapping.get(section);
            if (ind == null) {
                smapping[i] = -1;
            } else {
                smapping[i] = ind;
            }
        }

        //fix the number of linear scans
        for (int i = 0; i < wordlen; ++i)
        {
            //reset scan variables
            int currentUniques = uniqueWord; //number of current missing unique words.
            int left = i, right = i;
            Arrays.fill(table[1], 0);// initialize the second vector
            //here, simple solve the minimum-window-substring problem
            while (right < last)  // with right+=wordlen and left+=wordlen, we got "window" expanding and shrinking in wordlen.
            {
                while (currentUniques > 0 && right < last) // while currentUniques>0,word still missing or not enough
                {
                    int target = smapping[right];
                    if (target != -1 && ++table[1][target] == table[0][target])// this means when you "collect" enough occurrence
                        --currentUniques;
                    right += wordlen;
                }
                while (currentUniques == 0 && left < right)// currentUniques == 0,enough words but still may not match
                {
                    int target = smapping[left];
                    if (target != -1 && --table[1][target] == table[0][target] - 1)// check occurrence the same with words array.
                    {
                        int length = right - left;
                        //check length
                        if ((length / wordlen) ==  words.length)
                            rs.add(left);
                        ++currentUniques; // now we miss one more unique word, again...
                    }
                    left += wordlen;
                }
            }
        }
        return rs;
    }
}
