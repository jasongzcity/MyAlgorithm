package StrobogrammaticNumber_246;

/**
 * A strobogrammatic number is a number that
 * looks the same when rotated 180 degrees (looked at upside down).
 *
 * Write a function to determine if a number is strobogrammatic.
 * The number is represented as a string.
 *
 * For example, the numbers "69", "88", and "818" are all strobogrammatic.
 */
public class Solution {
    public boolean isStrobogrammatic(String num) {
        int len = num.length();
        if(len==0) return true;
        char[] a = num.toCharArray();
        int mid = len>>1,right,left;
        if((len&1)==1){
            if(a[mid]!='0'&&a[mid]!='8'&&a[mid]!='1') return false;
            right = mid+1;
            left = mid-1;
        }else{
            right = mid;
            left = mid-1;
        }
        while(left>-1){
            if(a[left]==a[right]){
                if(a[left]!='0'&&a[left]!='8'&&a[left]!='1')
                    return false;
            }else{
                if(!((a[left]=='6'&&a[right]=='9')||(a[right]=='6'&&a[left]=='9')))
                    return false;
            }
            left--;
            right++;
        }
        return true;
    }
}
