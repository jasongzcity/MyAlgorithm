package MinimumWindowSubstring_76;

import java.util.HashMap;
import java.util.Map;

/**
 *  Given a string S and a string T, find the minimum window in S
 *  which will contain all the characters in T in complexity O(n).
 *
 *  For example,
 *  S = "ADOBECODEBANC"
 *  T = "ABC"
 *  Minimum window is "BANC".
 *
 *  Note:
 *  If there is no such window in S that covers all characters in T,
 *  return the empty string "".
 *  If there are multiple such windows,
 *  you are guaranteed that there will always be only one
 *  unique minimum window in S.
 */
public class Solution
{
    // second session
    // ACC
    public static String minWindowII(String s, String t){
        int minBegin = -1, min = Integer.MAX_VALUE;
        int[] map = new int[128];
        for(char c:t.toCharArray()) ++map[c];
        char[] ss = s.toCharArray();
        int count = t.length();
        for(int left=0,right=0;right<ss.length;){
            if(map[ss[right++]]-->0) --count;
            if(count==0){
                while(count==0){
                    // we now collect all the chars in t, shrink the window
                    if(map[ss[left++]]++==0) ++count;
                }
                if(right-left+1<min){
                    min = right-left+1;
                    minBegin = left-1;
                }
            }
        }
        return min==Integer.MAX_VALUE?"":s.substring(minBegin,minBegin+min);
    }


    public static String minWindow(String s, String t)
    {
        int slen = s.length(),tlen = t.length();
        if(slen<tlen) return "";
        Map<Character,Integer> indexMapping = new HashMap<>(tlen);
        int index=0; // give each character an index.
        int uniqueChars = 0;// number of unique chars in the String t
        int[] ttimes = new int[tlen]; // the charaters occur times in String t
        for(Character c:t.toCharArray())
        {
            Integer cIndex = indexMapping.get(c);
            if(cIndex==null) // first met
            {
                cIndex = index;
                indexMapping.put(c,cIndex);
                index++;
                uniqueChars++;
            }
            ttimes[cIndex]++;
        }

        // The code block below is to map each character in String s
        // to the index given above.
        // This is actually an optimization move:
        // if we don't use this mapping in the "middle",
        // we have to do the hashing twice in the scanning(once right
        // pointer once left pointer)
        // And we only need to do hashing once using this mapping instead.
        // This also means the code block below could be removed for better
        // readability.
        int[] smapping = new int[slen];
        for(int temp=0;temp<slen;temp++)
        {
            Character c = s.charAt(temp);
            Integer cIndex = indexMapping.get(c);
            if(cIndex==null) smapping[temp] = -1;// This character does not appear in String t
            else smapping[temp] = cIndex;
        }

        int minLength = Integer.MAX_VALUE,minPosition = 0;
        int left = 0,right = 0,missingChars = uniqueChars;
        int[] stimes = new int[tlen];// count character appearance times in String s.
        while(right<slen)
        {
            while(right<slen&&missingChars>0) // keep right pointer scanning
            {
                Integer ind = smapping[right];
                if(ind>-1) // this char is in String t
                {
                    ++stimes[ind];
                    if(stimes[ind]==ttimes[ind]) --missingChars; // appear same times
                }
                right++;
            }
            while(left<right&&missingChars==0) // we got all chars in String t, check if shortest.
            {
                Integer ind = smapping[left];
                if(ind>-1) // this char in String t
                {
                    if(stimes[ind]==ttimes[ind])
                    {
                        // check shortest
                        if(right-left<minLength)
                        {
                            minLength = right-left;
                            minPosition = left;
                        }
                        ++missingChars; // This char is missing again, cuz pointer left go right
                    }
                    --stimes[ind];
                }
                left++;
            }
        }
        return minLength==Integer.MAX_VALUE?"":s.substring(minPosition,minPosition+minLength);
    }

    // Since hashing for Character is very fast, we don't need to avoid it.
    // This could also be a template for window problems.
    // Also, we could use a vector instead of a hashmap as a mapping.
    public static String minWindow2(String s, String t)
    {
        int slen = s.length(),tlen = t.length();
        if(slen<tlen) return "";
        Map<Character,Integer> ttimes = new HashMap<>(tlen);
        for(Character c:t.toCharArray())
            ttimes.put(c,ttimes.getOrDefault(c,0)+1);

        int missingchar = tlen,left=0,right=0,minLength = Integer.MAX_VALUE,minPosition = 0;
        while(right<slen)
        {
            Character c = s.charAt(right);
            Integer cCount = ttimes.get(c);
            if(cCount!=null) // valid char
            {
                if(cCount>0) missingchar--; // not enough c
                ttimes.put(c,cCount-1);
            }
            right++;
            while(missingchar==0) // we MAY get the shortest substring.
            {
                Character l = s.charAt(left);
                Integer lCount = ttimes.get(l);
                if(lCount!=null)
                {
                    if(lCount==0) // number of chars matched!
                    {
                        if(right-left<minLength) // check shortest first
                        {
                            minLength = right-left;
                            minPosition = left;
                        }
                        missingchar++;           // make the window missing char again
                    }
                    ttimes.put(l,lCount+1);
                }
                left++;
            }
        }
        return minLength==Integer.MAX_VALUE?"":s.substring(minPosition,minPosition+minLength);
    }

    // the solutions above are far too complicated
    // I am going to rewrite a simpler one
    // accepted
    public String minWindow3(String s, String t){
        int slen = s.length(),tlen = t.length(),min = Integer.MAX_VALUE,minBegin = 0;
        int begin = 0,end = 0,chars = 0;
        int[] map = new int[128];
        for(int i=0;i<tlen;i++)
            if(++map[t.charAt(i)]==1)
                chars++;

        while(end<slen){
            while(end<slen&&chars!=0)
                if(--map[s.charAt(end++)]==0)
                    --chars;
            if(chars!=0) break; // no such window
            // now shrink the window
            while(chars==0)
                if(++map[s.charAt(begin++)]==1)
                    ++chars; // now chars go missing again

            if(min>end-begin+1){
                min = end-begin+1;
                minBegin = begin-1;
            }
        }
        return min==Integer.MAX_VALUE?"":s.substring(minBegin,minBegin+min);
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.minWindow3("feersfdabccdefefsdaw","ffesd"));
    }
}
