package ShortestWordDistanceII_244;

import java.util.*;

import static javafx.scene.input.KeyCode.H;

/**
 * This is a follow up of Shortest Word Distance(#243).
 * The only difference is now you are given the list of words and your method
 * will be called repeatedly many times with different parameters.
 * How would you optimize it?
 *
 * Design a class which receives a list of words in the constructor,
 * and implements a method that takes two words word1 and word2 and
 * return the shortest distance between these two words in the list.
 *
 * For example,
 * Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
 *
 * Given word1 = “coding”, word2 = “practice”, return 3.
 * Given word1 = "makes", word2 = "coding", return 1.
 *
 * Note:
 * You may assume that word1 does not equal to word2,
 * and word1 and word2 are both in the list.
 */
public class WordDistance {

    private Map<String,List<Integer>> map;

    public WordDistance(String[] words) {
        map = new HashMap<>(words.length<<1);
        for(int i=0;i<words.length;i++){
            List<Integer> l = map.get(words[i]);
            if(l==null){
                l = new ArrayList<>();
                map.put(words[i],l);
            }
            l.add(i);
        }
    }

    public int shortest(String word1, String word2) {
        List<Integer> l1 = map.get(word1),l2 = map.get(word2);
        // Notice: at here, we met the circumstances just like we
        // met at (#475) Heaters,given two sorted list
        // find the biggest/smallest differences between two
        // lists' elements.
        int p1 = 0,p2 = 0,minDiff = Integer.MAX_VALUE;
        while(minDiff!=1&&p2<l2.size()){
            int i2 = l2.get(p2);
            while(p1<l1.size()&&l1.get(p1)<i2) ++p1;
            if(p1>0) minDiff = Math.min(i2-l1.get(p1-1),minDiff);
            if(p1<l1.size()) minDiff = Math.min(minDiff,l1.get(p1)-i2);
            else break;
            ++p2;
        }
        return minDiff;
    }

    // Another two pointer solution
    public int shortest2(String s1,String s2){
        List<Integer> l1 = map.get(s1),l2 = map.get(s2);
        int p1 = 0,p2 = 0,minDiff = Integer.MAX_VALUE;
        while(p1<l1.size()&&p2<l2.size()){
            int i1 = l1.get(p1),i2 = l2.get(p2);
            if(i1<i2){
                minDiff = Math.min(minDiff,i2-i1);
                p1++;
            }else{
                minDiff = Math.min(minDiff,i1-i2);
                p2++;
            }
        }
        return minDiff;
    }
}
