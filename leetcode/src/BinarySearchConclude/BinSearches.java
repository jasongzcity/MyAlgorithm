package BinarySearchConclude;

/**
 * Some conclusions upon Binary Search.
 * Keep adding up cases!
 */
public class BinSearches {
    // Classic binary search. Given a sorted array, find the target's position
    public static int classicBinSearch(int[] a,int target){
        int lo = 0,hi = a.length-1;
        while(lo<=hi){
            int mid = lo+((hi-lo)>>1); // avoid overflow
            if(a[mid]==target) return mid;
            if(a[mid]>target) hi = mid-1;
            else lo = mid+1;
        }
//        return hi; // largest element in sets which are elements smaller than target
        return lo; // smallest element in sets which are elements larger than target
    }

    public static int classicBinSearch2(int[] a,int target){
        int lo = 0,hi = a.length;
        while(lo<hi){
            int mid = lo+((hi-lo)>>1);
            if(a[mid]>target) hi = mid;
            else lo = mid+1;
        }
        return --lo;
    }

    public static int classicBinSearch3(int[] a,int target){
        int lo = 0,hi = a.length;
        while(lo<hi){
            int mid = lo+((hi-lo)>>1);
            if(a[mid]<target) lo = mid+1;
            else hi = mid;
        }
        return lo;
    }

    public static void main(String[] args){
        //testNoDuplicate();
        testDuplicate();
    }

    private static void testNoDuplicate(){
        int[] a = new int[]{1,2,3,4,6,8,99,111,134,1898,8888,100000,100000000};
        System.out.println(a[classicBinSearch2(a,7)]);
        System.out.println(a[classicBinSearch2(a,1897)]);
        System.out.println(a[classicBinSearch2(a,1899)]);
        System.out.println(a[classicBinSearch2(a,111)]);
        System.out.println(a[classicBinSearch2(a,1)]);
        System.out.println(a[classicBinSearch2(a,100000000)]);

        System.out.println();

        System.out.println(a[classicBinSearch3(a,7)]);
        System.out.println(a[classicBinSearch3(a,1897)]);
        System.out.println(a[classicBinSearch3(a,1899)]);
        System.out.println(a[classicBinSearch3(a,111)]);
        System.out.println(a[classicBinSearch3(a,1)]);
        System.out.println(a[classicBinSearch3(a,100000000)]);
    }

    private static void testDuplicate(){
        int[] a = new int[]{1,2,2,2,2,2,2,2,3};
        System.out.println(classicBinSearch2(a,2));

        System.out.println(classicBinSearch3(a,2));
    }
}
