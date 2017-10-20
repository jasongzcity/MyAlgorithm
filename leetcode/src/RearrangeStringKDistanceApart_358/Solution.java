package RearrangeStringKDistanceApart_358;

import java.util.*;

/**
 * Given a non-empty string s and an integer k,
 * rearrange the string such that the same characters are
 * at least distance k from each other.
 *
 * All input strings are given in lowercase letters.
 * If it is not possible to rearrange the string, return an empty string "".
 *
 * Example 1:
 * s = "aabbcc", k = 3
 * Result: "abcabc"
 * The same letters are at least distance 3 from each other.
 *
 * Example 2:
 * s = "aaabc", k = 3
 * Answer: ""
 * It is not possible to rearrange the string.
 *
 * Example 3:
 * s = "aaadbbcc", k = 2
 * Answer: "abacabcd"
 * Another possible answer is: "abcabcda"
 * The same letters are at least distance 2 from each other.
 */
public class Solution {
    // unaccepted, consider [a,b,b]
    public String rearrangeString2(String s, int k) {
        int[] map = new int[26], posi = new int[26];
        List<Character> l = new ArrayList<>();
        for(char c:s.toCharArray()){
            if(map[c-'a']++==0)
                l.add(c);
        }
        Arrays.fill(posi, -k);
        int pl = 0, ps = 0;
        StringBuilder sb = new StringBuilder(s.length());
        while(ps<s.length()){
            Character c = l.get(pl);
            if(ps-posi[c-'a']>=k){
                // valid
                sb.append(c);
                posi[c-'a'] = ps;
                if(--map[c-'a']==0) l.remove(pl--);
            }else{
                return "";
            }
            ++ps;
            if(++pl>=l.size()) pl = 0;
        }
        return sb.toString();
    }

    // This is still wrong
    public String rearrangeString3(String s, int k){
        int[] count = new int[26];
        TreeMap<Integer, Character> map = new TreeMap<>(Collections.reverseOrder());
        for(char c:s.toCharArray()) count[c-'a']++;
        for(int i=0;i<26;i++)
            if(count[i]>0)
                map.put(count[i],(char)i);
        StringBuilder sb = new StringBuilder(s.length());
        Deque<Object> tm = new LinkedList<>();
        while(map.size()!=0){
            for(int i=0;i<k;i++){
                if(map.size()!=0){
                    Integer times = map.firstKey();
                    Character cc = map.remove(times);
                    sb.append(cc);
                    if(times-1>0){
                        tm.addFirst(times-1);
                        tm.addFirst(cc);
                    }
                }else{
                    return "";
                }
            }
            while(tm.size()!=0){
                Integer times = (Integer)tm.removeLast();
                map.put(times,(Character) tm.removeLast());
            }
        }
        return sb.toString();
    }

    // optimal solution
    public String rearrangeString(String str, int k) {
        int length = str.length();
        int[] count = new int[26];
        int[] valid = new int[26];
        for(char c:str.toCharArray()){
            count[c-'a']++;
        }
        StringBuilder sb = new StringBuilder(length);
        for(int index = 0;index<length;index++){
            int candidatePos = findValidMax(count, valid, index);
            if( candidatePos == -1) return "";
            count[candidatePos]--;
            valid[candidatePos] = index+k;
            sb.append((char)('a'+candidatePos));
        }
        return sb.toString();
    }

    private int findValidMax(int[] count, int[] valid, int index){
        int max = Integer.MIN_VALUE;
        int candidatePos = -1;
        for(int i=0;i<count.length;i++){
            if(count[i]>0 && count[i]>max && index>=valid[i]){
                max = count[i];
                candidatePos = i;
            }
        }
        return candidatePos;
    }
}
