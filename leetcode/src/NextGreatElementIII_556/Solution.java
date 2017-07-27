package NextGreatElementIII_556;

/**
 * Given a positive 32-bit integer n,
 * you need to find the smallest 32-bit integer which
 * has exactly the same digits existing in the integer n
 * and is greater in value than n.
 * If no such positive 32-bit integer exists, you need to return -1.
 * `
 * Example 1:
 * Input: 12
 * Output: 21
 *
 * Example 2:
 * Input: 21
 * Output: -1
 */
public class Solution {
    public int nextGreaterElement(int n) {
        char[] ca = intToChars(n);
        int p = ca.length-1;
        while(p>0&&ca[p]<=ca[p-1]) --p;
        if(--p==-1) return -1;
        int pp = ca.length-1;
        while(ca[pp]<=ca[p]) --pp;
        // now swap pp and p
        swap(ca,p++,pp);
        reverse(ca,p,ca.length-1);
        return charsToInt(ca);
    }

    private char[] intToChars(int n){
        int count = 0;
        for(int i=n;i>0;count++,i/=10);
        char[] a = new char[count--];
        for(;n>0;count--,n/=10) a[count] = (char)(n%10+'0');
        return a;
    }

    // return -1 if overflow
    private int charsToInt(char[] a){
        int rs = 0;
        for(char c:a){
            int newRs = rs*10+(c-'0');
            if(newRs/10!=rs) return -1; // deal with overflow.
            rs = newRs;
        }
        return rs;
    }

    private void swap(char[] a,int i,int j){
        char c = a[i];
        a[i] = a[j];
        a[j] = c;
    }

    private void reverse(char[] a,int begin,int end){
        while(begin<end) swap(a,begin++,end--);
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.nextGreaterElement(12));
        System.out.println(s.nextGreaterElement(0));
        System.out.println(s.nextGreaterElement(499999));
        System.out.println(s.nextGreaterElement(499999));
        System.out.println(s.nextGreaterElement(3866875));
    }
}
