package NumberOfSegmentInAStr_434;

/**
 * Count the number of segments in a string,
 * where a segment is defined to be a contiguous sequence of non-space characters.
 *
 * Please note that the string does not contain any non-printable characters.
 *
 * Example:
 * Input: "Hello, my name is John"
 * Output: 5
 */
public class Solution {
    public int countSegments(String s) {
        int count = 0;
        boolean flag = false;
        char[] a = s.toCharArray();
        for(int i=0;i<a.length;i++){
            if(a[i]==' '){
                if(flag) ++count;
                flag = false;
            }else{
                flag = true;
            }
        }
        return flag?count+1:count;
    }
}
