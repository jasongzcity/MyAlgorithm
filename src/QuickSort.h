/* Created on 2016-2-15 20:02:04 by LWZ. Template Function for quicksort */

#ifndef QUICKSORT_H
#define QUICKSORT_H

#include <ObjectFunctions.h>

using namespace std;

typedef int Rank;

enum partitionflag
{
	EQUAL,
	UNEQUAL
};
template<typename T>
Rank Partition1(T* buff,Rank lo,Rank hi)			//这是相等的版本,交换会更少,但是退化情况下O(n^2)
{
	Swap(buff[lo],buff[lo+rand()%(hi-lo+1)]);		//在区间随机取值做pivot
	T pivot = buff[lo];
	while(lo<hi)
	{
		while( (lo<hi) && (pivot<=buff[hi]) )
		{
			hi--;
		}
		buff[lo++] = buff[hi];
		while( (lo<hi) && (buff[lo]<=pivot) )
		{
			lo++;
		}
		buff[hi--] = buff[lo];
	}
	buff[lo] = pivot;
	return lo;
}

template<typename T>
Rank Partition2(T* buff,Rank lo,Rank hi)			//这是不相等版本,交换会更频繁但退化情况下O(nlogn)
{
	Swap(buff[lo],buff[lo+rand()%(hi-lo+1)]);		
	T pivot = buff[lo];
	while(lo<hi)
	{
		while(lo<hi)
		{
			if(pivot<buff[hi])
			{
				hi--;
			}
			else
			{
				buff[lo++] = buff[hi];
				break;
			}
		}
		while(lo<hi)
		{
			if(buff[lo]<pivot)
			{
				lo++;
			}
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

//排序区间[lo,hi)
//注意快速排序是不稳定的
template<typename T>
void QuickSort(T* buff,Rank lo,Rank hi,partitionflag flag = EQUAL)		
{
	Rank mi = 0;
	if(hi-lo<2)
	{
		return;
	}
	if(flag == EQUAL)
	{
		mi = Partition1(buff,lo,hi-1);			//在[lo,hi-1]区间取pivot
	}
	else
	{
		mi = Partition2(buff,lo,hi-1);
	}
	QuickSort(buff,lo,mi,flag);
	QuickSort(buff,mi+1,hi,flag);
}










#endif //QUICKSORT_H 