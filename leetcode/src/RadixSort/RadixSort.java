package RadixSort;

import java.util.Arrays;

/**
 * RadixSort practice
 */
public class RadixSort {
    public static void radixSort(int[] a){
        int len = a.length;
        if(len<2) return;
        int max = Integer.MIN_VALUE;
        for(int i:a)
            if(i>max)
                max=i;
        int mask = 1;
        int[] count = new int[10],b = new int[len],temp;
        while(max/mask!=0){
            for(int i:a) ++count[i/mask%10];
            for(int i=1;i<count.length;i++) count[i]+=count[i-1];
            for(int i=a.length-1;i>-1;i--) b[--count[a[i]/mask%10]] = a[i];
            temp = a;
            a = b;
            b = temp;
            Arrays.fill(count,0);
            mask*=10;
        }
    }

    public static void main(String[] args) {
        int[] a = {7,19,233,23,1,234,54,5,324,2314,34,6543,345,7,8,2,1,34,3254,234};
        radixSort(a);
        System.out.println(Arrays.toString(a));
    }
}
