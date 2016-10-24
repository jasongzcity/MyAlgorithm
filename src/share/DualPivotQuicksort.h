/** 
* DualPivotQuicksort.h
* Use insertion sort and dual-pivot quicksort to provide more optimized sort for array.
* Created on 2016-10-16 
* Author: lwz
**/

#ifndef DUALPIVOTQUICKSORT_H
#define DUALPIVOTQUICKSORT_H

#define INSERTION_SORT_THRESHOLD 47

#include "./MyAlgorithm.h"
#include "./QuickSort.h"

ALGORITHM_BEGIN

/**
* Sort array a in the range of [lo, hi](Rank lo and hi inclusive). 
* Use leftmost to describe if the range to be sorted is the leftmost for insertion optimization.
* This method is inspired by Oracle JDK java.util.DualPivotQuicksort.
**/
template <typename T> void DualPivotQuicksort(T* a, Rank lo, Rank hi, bool leftmost)
{
    if(lo>=hi)
        return;
    int length = hi - lo + 1;
    if(length<INSERTION_SORT_THRESHOLD) //use insertion sort
    {
        if(leftmost)//need to check left boundary in every iteration
        {
            //tradition insertion sort.
            for(Rank i=lo+1, k=i; i<=hi ; k = ++i)
            {
                //use k in each search
                T temp = a[k];
                while(a[--k]>temp)
                {
                    a[k+1] = a[k];
                    if(k==lo)//no more searching or the k will out of bound!
                    {
                        --k;
                        break;
                    }
                }
                a[k+1] = temp;
            }
        }
        else    //No need to check left bound. Also, cound use pair insertion sort
        {
            //pair insertion sort. Use lo and k to represent the elements to be inserted.
            Rank k = lo;
            for(; ++lo <= hi ; k = ++lo)
            {
                T larger = a[lo], smaller = a[k];
                if(larger<smaller) 
                {
                    larger = smaller;
                    smaller = a[lo];
                }
                while(a[--k]>larger)
                {
                    a[k+2] = a[k];
                }
                a[k+2] = larger;
                ++k;                        //begin search rank
                while(a[--k]>smaller)
                {
                    a[k+1] = a[k];
                }
                a[k+1] = smaller;
            }
            if(lo == hi+1)//then the rightmost element has not been sorted
            {
                //then k = hi
                T temp = a[hi];
                while(a[--k]>temp)
                {
                    a[k+1] = a[k];
                }
                a[k+1] = temp;
            }
        }
        return;
    }

    //the below is dual pivot quicksort

    int seventh = (length>>3) + (length>>6) + 1; //get the length/7

    Rank e3 = (lo+hi)>>1;//middle point
    Rank e2 = e3 - seventh;
    Rank e1 = e2 - seventh;
    Rank e4 = e3 + seventh;
    Rank e5 = e4 + seventh;

    /* *
     *  Insertion sort these 5 elements, then use e2 and e4 as 2 pivots for dual pivot insertion sort.
     *  The method above to make sure a[e2] and a[e4] "as 1/3 as possible" so that the array can be split averagely 
     *  and therefore reduce the depth of recursion.
     * */
    T temp = a[e2];
    if(a[e1] > temp)
    {
        a[e2] = a[e1];
        a[e1] = temp;
    }
    if(a[e2] > a[e3])
    {
        temp = a[e3];
        a[e3] = a[e2];
        if(a[e1] > temp)
        {
            a[e2] = a[e1];
            a[e1] = temp;
        }
        else
            a[e2] = temp;
    }
    if(a[e3] > a[e4])
    {
        temp = a[e4];
        a[e4] = a[e3];
        if(a[e2] > temp)
        {
            a[e3] = a[e2];
            if(a[e1] > temp)
            {
                a[e2] = a[e1];
                a[e1] = temp;
            }
            else
                a[e2] = temp;
        }
        else
            a[e3] = temp;
    }
    if(a[e4] > a[e5])
    {
        temp = a[e5];
        a[e5] = a[e4];
        if(a[e3] > temp)
        {
            a[e4] = a[e3];
            if(a[e2] > temp)
            {
                a[e3] = a[e2];
                if(a[e1] > temp)
                {
                    a[e2] = a[e1];
                    a[e1] = temp;
                }
                else
                    a[e2] = temp; 
            }
            else
                a[e3] = temp;
        }
        else
            a[e4] = temp;
    }

    //std::cout<<"e1: "<<a[e1]<<" e2: "<<a[e2]<<" e3: "<<a[e3]<<" e4: "<<a[e4]<<" e5: "<<a[e5]<<std::endl;
    //SkipLines(1);

    //the comment copy from DualPivotQuicksort.java
    /*
    * Partitioning:
    *
    *   left part           center part                   right part
    * +--------------------------------------------------------------+
    * |  < pivot1  |  pivot1 <= && <= pivot2  |    ?    |  > pivot2  |
    * +--------------------------------------------------------------+
    *               ^                          ^       ^
    *               |                          |       |
    *              less                        k     great
    *
    * Invariants:
    *
    *              all in (left, less)   < pivot1
    *    pivot1 <= all in [less, k)     <= pivot2
    *              all in (great, right) > pivot2
    *
    * Pointer k is the first index of ?-part.
    */

    //use flag == 3 to deal with circumstance that elements are highly duplicated.
    if(a[e1] == a[e2] || a[e2] == a[e3] || a[e3] == a[e4] || a[e4] == a[e5])
    {
        MyAlgorithm::QuickSort(a, lo, hi, 3);
        return;
    }

    /* 
    Below is a simple implementation of dual pivot quicksort: 

    swap(a[e2],a[lo]);
    swap(a[e4],a[hi]);
    Rank less = lo+1;//skip the lo
    Rank great = hi-1;//skip the hi.
    Rank k = less;
    while(k<=great)
    {
        if(a[k] < a[lo])
            swap(a[k++],a[less++]);
        else if(a[k] > a[hi])
            swap(a[k],a[great--]);//check k again
        else
            k++;
    }
    swap(a[less-1],a[lo]);
    swap(a[great+1],a[hi]);
    DualPivotQuicksort(a,lo,less-2,leftmost);
    DualPivotQuicksort(a,less,great,false);
    DualPivotQuicksort(a,great+2,hi,false);
    */

    swap(a[e2],a[lo]); //use a[lo] as pivot1
    swap(a[e4],a[hi]); //use a[hi] as pivot2
    //pointers
    Rank less = lo+1;//skip the lo
    Rank great = hi-1;//skip the hi.

    while(a[less] < a[lo])//now a[less] >= a[lo]
        ++less;
    while(a[great] > a[hi])//now a[great] <= a[hi]
        --great;

outer:
    for(Rank k = less; k <= great ; k++)
    {
        if(a[k] < a[lo])//put in the left subarray.
        {
            temp = a[k];
            a[k] = a[less];
            a[less++] = temp;
        }
        else if(a[k] > a[hi])
        {
            temp = a[k];
            while(a[great] > a[hi])  //check & slide. Optimization.
            {
                --great;
                if(great == k)
                    goto outer;
            }
            if(a[great] < a[lo])
            {
                a[k] = a[less];
                a[less++] = a[great];
            }
            else
                a[k] = a[great];
            a[great] = temp;
            great--;
        }
        //else a[k] in [pivot1, pivot2], no need to move.
    }
    temp = a[less-1];   //swap(a[less-1],a[lo]);
    a[less-1] = a[lo];   
    a[lo] = temp;
    temp = a[great+1];//swap(a[great+1],a[hi]);
    a[great+1] = a[hi];
    a[hi] = temp;

    //recursive calls
    DualPivotQuicksort(a, lo, less-2, leftmost);
    DualPivotQuicksort(a, less, great, false);
    DualPivotQuicksort(a, great+2, hi, false);
}

ALGORITHM_END

#endif //DUALPIVOTQUICKSORT_H