package FindTheCelebrity_277;

/**
 * Suppose you are at a party with n people (labeled from 0 to n - 1) and among them,
 * there may exist one celebrity.
 * The definition of a celebrity is that all the other n - 1 people
 * know him/her but he/she does not know any of them.
 *
 * Now you want to find out who the celebrity is or verify that there is not one.
 * The only thing you are allowed to do is to
 * ask questions like: "Hi, A. Do you know B?" to get information of whether A knows B.
 * You need to find out the celebrity (or verify there is not one)
 * by asking as few questions as possible (in the asymptotic sense).
 *
 * You are given a helper function bool knows(a, b) which tells you whether A knows B.
 * Implement a function int findCelebrity(n),
 * your function should minimize the number of calls to knows.
 *
 * Note: There will be exactly one celebrity if he/she is in the party.
 * Return the celebrity's label if there is a celebrity in the party.
 * If there is no celebrity, return -1.
 */
public class Solution {

    private boolean[][] relations = new boolean[][]{
            {true,false,true,true},
            {true,true,false,true},
            {false,false,true,false},
            {false,false,true,true}
    };
    // dummy method
    private boolean knows(int a, int b) {
        return relations[a][b];
    }

    public int findCelebrity(int n) {
        int a = 0,b = n-1;
        while(a<b){
            boolean aknowsb = knows(a,b),bknowsa = knows(b,a);
            if(aknowsb==bknowsa){
                // if they don't know each other or they know each other
                a++;
                b--;
            }else if(aknowsb) a++;
            else b--;
        }
        for(int i=0;i<n;i++){
            if(i!=a){
                if(knows(a,i)||!knows(i,a)) return -1;
            }
        }
        return a;
    }

    // This solution is wrong!!!
    /* Explaination: for n=5 people, the relationship is shown as 2d array below
    *     0 1 2 3| 4
    *  0  y y n n| y
    *  1  y y y n| y
    *  2  n y y n| y
    *  3  y y y y| y
    *  -------------
    *  4  n y y n| y
    * we move the pointer vertically down. If we found this person knows other(s),
    * that means he is not a candidant of celebrity, so move to next person in the array.
    * In this way, the pointer will go diagonally down.If there is a celebrity (3 in this case),
    * we must meet such a "wall" which eventually forward and trap the pointer in a "tunnel".
    * Last step is to verify if target is a valid celebrity. For example above,
    * we may have situation that person 4 knows nobody, or person 3 knows 0
    */
    public int findCelebrity2(int n){
        int candidate = 0;
        for (int i = 0; i < n; i++) if (knows(candidate, i)) candidate = i;
        // validate candidate
        for (int i = 0; i < candidate; i++) if (knows(candidate, i)) return -1;
        for (int i = candidate + 1; i < n; i++) if (!knows(i, candidate)) return -1;
        return candidate;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.findCelebrity(4));
    }

    public int findCelebrity3(int n){
        int candi = 0;
        for(int i=0;i<n;i++)
            if(knows(candi,i))
                candi = i;
        // The candi must be the only celebrity, if exists
        for(int i=0;i<n;i++){
            if(i<candi){
                if(!knows(i,candi)||knows(candi,i)) return -1;
            }else if(i>candi){
                if(!knows(i,candi)) return -1;
            }
        }
        return candi;
    }
}
