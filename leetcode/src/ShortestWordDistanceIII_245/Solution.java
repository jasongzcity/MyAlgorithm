package ShortestWordDistanceIII_245;

/**
 * This is a follow up of Shortest Word Distance.
 * The only difference is now word1 could be the same as word2.
 *
 * Given a list of words and two words word1 and word2,
 * return the shortest distance between these two words in the list.
 *
 * word1 and word2 may be the same and they represent two individual words
 * in the list.
 *
 * For example,
 * Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
 *
 * Given word1 = “makes”, word2 = “coding”, return 1.
 * Given word1 = "makes", word2 = "makes", return 3.
 *
 * Note:
 * You may assume word1 and word2 are both in the list.
 */
public class Solution {
    public int shortestWordDistance(String[] words, String word1, String word2) {
        int minDiff = Integer.MAX_VALUE,p1 = 0,p2 = 0;
        if(word1.equals(word2)){
            while(!words[p1].equals(word1)) ++p1;
            int prev = p1++;
            while(true){
                while(p1<words.length&&!words[p1].equals(word1)) ++p1;
                if(p1==words.length) return minDiff;
                minDiff = Math.min(minDiff,p1-prev);
                prev = p1++;
            }
        }else{
            p1 = p2 = -words.length;
            for(int i=0;i<words.length;i++){
                if(words[i].equals(word1))
                    minDiff = Math.min(minDiff,(p1=i)-p2);
                else if(words[i].equals(word2))
                    minDiff = Math.min(minDiff,(p2=i)-p1);
            }
        }
        return minDiff;
    }

    // optimal
    public int shortestWordDistance2(String[] words, String word1, String word2) {
        long dist = Integer.MAX_VALUE, i1 = dist, i2 = -dist;
        boolean same = word1.equals(word2);
        for (int i=0; i<words.length; i++) {
            if (words[i].equals(word1)) {
                if (same) {
                    i1 = i2;
                    i2 = i;
                } else {
                    i1 = i;
                }
            } else if (words[i].equals(word2)) {
                i2 = i;
            }
            dist = Math.min(dist, Math.abs(i1 - i2));
        }
        return (int) dist;
    }
}
