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
Rank Partition1(T* buff,Rank lo,Rank hi)			//������ȵİ汾,���������,�����˻������O(n^2)
{
	Swap(buff[lo],buff[lo+rand()%(hi-lo+1)]);		//���������ȡֵ��pivot
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
Rank Partition2(T* buff,Rank lo,Rank hi)			//���ǲ���Ȱ汾,�������Ƶ�����˻������O(nlogn)
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

//��������[lo,hi)
//ע����������ǲ��ȶ���
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
		mi = Partition1(buff,lo,hi-1);			//��[lo,hi-1]����ȡpivot
	}
	else
	{
		mi = Partition2(buff,lo,hi-1);
	}
	QuickSort(buff,lo,mi,flag);
	QuickSort(buff,mi+1,hi,flag);
}










#endif //QUICKSORT_H 