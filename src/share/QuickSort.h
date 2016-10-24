/**
* QuickSort.h.
* Last modified on: 2016/10/15
* Author:lwz
**/

#ifndef QUICKSORT_H
#define QUICKSORT_H

#include <stdlib.h>//for rand()
#include "./MyAlgorithm.h"

ALGORITHM_BEGIN

/**
* Find the pivot element's rank in buff[lo,hi] - equal version.
* Less element swapping, worse degenerate situation.
* Return the Rank of the pivot element.
**/
template<typename T> Rank Partition1(T* buff,Rank lo,Rank hi)
{
    swap(buff[lo],buff[lo+rand()%(hi-lo+1)]);   //Randomize the pivot element
    T pivot = buff[lo];
    while(lo<hi)
    {
        while(lo<hi)
        {
            if(pivot<=buff[hi]) 
                hi--;
            else
            {
                buff[lo++] = buff[hi];
                break;
            }
        }
        while(lo<hi)
        {
            if(buff[lo]<=pivot)
                lo++;
            else
            {
                buff[hi--] = buff[lo];
                break;
            }
        }
    }
    buff[lo] = pivot;
    return lo;
}

/**
* Find the pivot element's rank in buff[lo,hi] - not equal version.
* More element swapping, better degenerate situation.
* Return the Rank of the pivot element.
**/
template<typename T> Rank Partition2(T* buff,Rank lo,Rank hi)
{
    swap(buff[lo],buff[lo+rand()%(hi-lo+1)]);//Randomize the pivot element
    T pivot = buff[lo];
    while(lo<hi)
    {
        while(lo<hi)
        {
            if(pivot<buff[hi])
                hi--;
            else
            {
                buff[lo++] = buff[hi];
                break;
            }
        }
        while(lo<hi)
        {
            if(buff[lo]<pivot)
                lo++;
            else
            {
                buff[hi--] = buff[lo];
                break;
            }
        }
    }
    buff[lo] = pivot;
    return lo;
}

/**
* TODO: NAMING.
* This method has same problem with Partition1, in degenerate situaltion,
* its time complexity degenerate to O(n^2).
* To avoid this, should discover a way to spread equal element averagely aside pivot element.
**/
template<typename T> Rank Partition3(T* buff,Rank lo,Rank hi)
{
    swap(buff[hi],buff[lo+rand()%(hi-lo+1)]);
    Rank i = lo - 1;
    for(Rank j=lo;j<hi;j++)
    {
        if(buff[j]<=buff[hi])//swap to i; use buff[hi] as pivot.
        {
            i++;
            swap(buff[j],buff[i]);
        }
    }
    i++;//move to pivot position.
    swap(buff[i], buff[hi]);
    return i;
}

/**
* TODO: NAMING.
**/
template<typename T> Rank Partition4(T* buff,Rank lo,Rank hi)
{
    swap(buff[hi],buff[lo+rand()%(hi-lo+1)]);
    bool toggle = false;
    Rank i = lo - 1;
    for(Rank j=lo;j<hi;j++)
    {
        if(buff[j]<buff[hi])
        {
            i++;
            swap(buff[i], buff[j]);
        }
        else if(buff[j] == buff[hi])//spread equal element averagely.
        {
            if(toggle)
            {
                i++;
                swap(buff[i], buff[j]);
                toggle = false;
            }
            else
                toggle = true;//skip this equal element and swap the next equal next time.
        }
    }
    i++;
    swap(buff[i], buff[hi]);
    return i;//so that the return Rank can stop in the middle of array
}

/**
* QuickSort entrance.
* Differs only in partition method.
* Sort array [lo, hi] (Rank lo and hi inclusive)
* use partitionflag to choose the partition method.
* Defaultly partitionflag = 0.
*
* PARTITION METHOD COMPARISON:
* Firstly, consider a degenerate situation that all elements in the array are equal.
*
* 1. If partitionflag==0, this method will use tradition(HOARE) partition and chech if equal in each compare.
* This will cause less element-moving(not moved if equals). However, it will degenerate to 
* time complexity of O(n^2) in the situation mentioned above.
*
* 2.If partitionflag == 1, this method will use tradition(HOARE) partition but not checking equal.
* So, it cause more element moving but maintain a time complexity of O(n logn).
*
* 3.If partitionflag == 2, this method use TODO:XXX partition.Analyze its degenerate situation later.
* 4.If partitionflag == 3,
**/
template<typename T> void QuickSort(T* buff, Rank lo, Rank hi, int partitionflag = 0)       
{
    PrepareRandom();
    Rank mi;
    if(!(lo<hi))
        return;
    if(partitionflag == 0)//choose partition
        mi = Partition1(buff,lo,hi);
    else if(partitionflag == 1)
        mi = Partition2(buff,lo,hi);
    else if(partitionflag == 2)
        mi = Partition3(buff,lo,hi);
    else
        mi = Partition4(buff,lo,hi);
    QuickSort(buff,lo,mi-1,partitionflag);
    QuickSort(buff,mi+1,hi,partitionflag);
}

ALGORITHM_END

#endif //QUICKSORT_H 