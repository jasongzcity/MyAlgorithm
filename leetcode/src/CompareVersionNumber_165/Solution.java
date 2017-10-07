package CompareVersionNumber_165;

/**
 * Compare two version numbers version1 and version2.
 * If version1 > version2 return 1,
 * if version1 < version2 return -1, otherwise return 0.
 *
 * You may assume that the version strings are non-empty
 * and contain only digits and the . character.
 * The . character does not represent a decimal point and
 * is used to separate number sequences.
 * For instance, 2.5 is not "two and a half" or
 * "half way to version three",
 * it is the fifth second-level revision of the second first-level revision.
 *
 * Here is an example of version numbers ordering:
 *
 * 0.1 < 1.1 < 1.2 < 13.37
 */
public class Solution {
    public int compareVersion(String v1, String v2) {
        int p1 = 0,p2 = 0;
        while(p1<v1.length() || p2<v2.length()){
            int e1 = p1>=v1.length()?-2:v1.indexOf('.',p1),
                    e2 = p2>=v2.length()?-2:v2.indexOf('.', p2);
            if(e1==-1) e1 = v1.length();
            if(e2==-1) e2 = v2.length();

            int i1 = e1==-2?0:Integer.parseInt(v1.substring(p1,e1)),
                    i2 = e2==-2?0:Integer.parseInt(v2.substring(p2, e2));
            if(i1<i2) return -1;
            else if(i2<i1) return 1;
            p1 = e1<0?v1.length():e1+1;
            p2 = e2<0?v2.length():e2+1;
        }
        return 0;
    }

    public int compareVersion2(String v1, String v2){
        for(int i=0,j=0;i<v1.length()||j<v2.length();i++,j++){
            int num1 = 0, num2 = 0;
            // avoid using Integer.parseInt because we need to deal with special
            // input for it.
            // we just parse by ourselves
            while(i<v1.length()&&v1.charAt(i)!='.') num1 = num1*10+v1.charAt(i++)-'0';
            while(j<v2.length()&&v2.charAt(j)!='.') num2 = num2*10+v2.charAt(j++)-'0';
            if(num1<num2) return -1;
            if(num1>num2) return 1;
        }
        return 0;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.compareVersion("1.0.1","1"));
    }
}
