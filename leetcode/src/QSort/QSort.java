package QSort;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

/**
 * Review of quicksort
 */
public class QSort
{
    private static Random rand = new Random();

    private static void swap(int[] a,int i,int j){
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void hoareQSort(int[] a,int lo,int hi){
        if(lo>=hi) return;
        int mid = hoarePartition(a,lo,hi);
        hoareQSort(a,lo,mid-1);
        hoareQSort(a,mid+1,hi);
    }

    // Notice hi inclusive
    private static int hoarePartition(int[] a, int lo, int hi){
        swap(a,lo,lo+(rand.nextInt()%(hi-lo+1))); // randomize
        int pivot = a[lo];
        while(lo<hi){
            while(lo<hi) {
                if (a[hi] >= pivot) --hi;
                else {
                    a[lo++] = a[hi];
                    break;
                }
            }
            while(lo<hi){
                if(a[lo]<=pivot) ++lo;
                else {
                    a[hi--] = a[lo];
                    break;
                }
            }
        }
        a[lo] = pivot;
        return lo;
    }

    // Notice hi inclusive
    public static void lomutoQSort(int[] a,int lo,int hi){
        if(lo>=hi) return;
        int mid = lomutoPartition(a,lo,hi);
        lomutoQSort(a,lo,mid-1);
        lomutoQSort(a,mid+1,hi);
    }

    // This method keeps the elements that smaller than
    // pivot at range [lo,slow)
    // and elements that larger than pivot at range [slow,fast)
    private static int lomutoPartition(int[] a,int lo,int hi){
        swap(a,lo+rand.nextInt()%(hi-lo+1),hi); // randomize pivot
        int fast = lo,slow = lo;
        while(fast<hi){
            if(a[fast]<a[hi]){
                swap(a,slow,fast);
                slow++;
            }
            fast++;
        }
        swap(a,slow,hi);
        return slow;
    }

    public static void main(String[] args) {
        int[] a = new int[25];
//        for (int i = 0; i < 25; i++) {
//            a[i] = rand.nextInt(10000);
//        }
//        hoareQSort(a,0,a.length-1);
//        System.out.println(Arrays.toString(a));
//        for (int i = 0; i < 25; i++) {
//            a[i] = rand.nextInt(10000);
//        }
//        lomutoQSort(a,0,a.length-1);
//        System.out.println(Arrays.toString(a));

        // test extreme input
        for (int i = 0; i < 24; i++) {
            a[i] = 1000;
        }
        a[24] = 1;
        lomutoQSort(a,0,a.length-1);
        System.out.println(Arrays.toString(a));
        a[0] = 1000;
        lomutoQSort(a,0,a.length-1);
        System.out.println(Arrays.toString(a));

//        for (int i = 0; i < 24; i++) {
//            a[i] = 1000;
//        }
//        a[24] = 1;
//        hoareQSort(a,0,a.length-1);
//        System.out.println(Arrays.toString(a));
//        a[0] = 1000;
//        hoareQSort(a,0,a.length-1);
//        System.out.println(Arrays.toString(a));
    }
}
