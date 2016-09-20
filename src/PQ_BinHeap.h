/* Created on 2016-2-4 09:42:45 BY LWZ. Template for complete Binary Heap */
#ifndef PQ_BINHEAP_H
#define PQ_BINHEAP_H

#include "PQInterface.h"
#include <ADT_Vector.h>
#include <ObjectFunctions.h>
#include <iostream>

/////////////////////////Shortcut////////////////////////
#define InHeap(i,n)		(((i)>(-1)) && ((i)<n))		//i是否在堆中
#define Parent(i)			(((i)-1)>>1)				//i的父节点的秩
#define LChild(i)			(((i)<<1)+1)				//i的左右孩子
#define RChild(i)			(LChild(i)+1)
#define RChildValid(i,n)	InHeap(RChild(i),n)			//i的左右孩子是否合法
#define LChildValid(i,n)	InHeap(LChild(i),n)	
#define ParentValid(i)		((i)>0)					//除非是根节点否则都合法
#define LastInternal(n)		(Parent(n-1))				//n表示堆得规模
#define Prior(buff,i,j)		((buff[i])<(buff[j])?j:i)		//这步前需要重载运算符来表示优先级.同时,该步返回优先级较高的元素的秩
												//若要优化,可将倾向于"不动"的元素摆在i的位置,那么当两者相等时返回的是i
////////////////////////ShortcutEnd//////////////////////

template <typename T>
class PQ_BinHeap : public PQ<T>							//构建一个堆默认优先高的更接近堆得根部
{														//若想修改则在快捷方式中修改Prior方法
private:
	int _size;		//规模
	SVector<T> Buff;//数据区已重载,可直接用[]访问
protected:
	Rank ProperParent(Rank i)		//找到i和它的两个孩子中优先级最高者的秩
	{
		Rank temp = i;
		if(LChildValid(i,_size))			//先判断左子树是否合法,若左子树不合法那么右子树也必不合法
		{
			temp = (Prior(Buff,i,LChild(i)) == i)?i:LChild(i);
			if(RChildValid(i,_size))
			{
				temp = (Prior(Buff,temp,RChild(i)) == temp)?temp:RChild(i);
			}
		}
		return temp;
	}
	Rank PercolateUp(Rank i)		//从秩为i的位置开始上渗透
	{								//此方法不需要调用Swap进行三次赋值,效率更高
		T temp = Buff[i];			//用temp暂存要上渗的元素					
		while(i)					//i=0也没必要上渗
		{
			if(temp>Buff[Parent(i)])	//i优先级较高,上渗
			{
				Buff[i] = Buff[Parent(i)];	//将元素换下来,而不必着急将temp赋给父亲,因为有可能还要上渗
				i = Parent(i);
			}
			else					//i位置优先级小或相等,不必继续上渗了
			{
				break;
			}
		}
		Buff[i] = temp;				//需补充最后一次赋值
		return i;
	}
	Rank PercolateDown(Rank i)		//从秩i的元素开始向下渗透
	{								//这种情况下能保证每层只赋值一次
		T temp = Buff[i];			//暂存元素		
		while((RChildValid(i,_size)&&(temp<Buff[RChild(i)]))||(LChildValid(i,_size)&&(temp<Buff[LChild(i)])))	//当有孩子存在且
		{																										//优先级比temp大时,就必须向下渗透
			if(RChildValid(i,_size) && (Prior(Buff,LChild(i),RChild(i)) == RChild(i)))	//仅当右孩子存在且右孩子优先级更高时
			{
				Buff[i] = Buff[RChild(i)];
				i = RChild(i);
			}
			else
			{
				Buff[i] = Buff[LChild(i)];
				i = LChild(i);
			}
		}
		Buff[i] = temp;				//补充一次赋值
		return i;
	}
	void Heapify()					//堆的快速构造
	{								//O(n)时间复杂度
		for(Rank i=LastInternal(_size);i>-1;i--)
		{
			PercolateDown(i);		//从下往上遍历节点,每个都向下渗透
		}
	}
public:
	PQ_BinHeap(){}						//默认构造方法(向量会自动构造)
	PQ_BinHeap(const T* Array,int size)		//读入一个数组(同时制定元素个数),并快速造堆
	{
		_size = size;
		Buff = SVector<T>(Array,size);		//通过构造匿名类完成了初始化...真是不易....
		Heapify();
	}
	PQ_BinHeap(const SVector<T>& vec)		//读取向量,并通过向量复制方法
	{
		_size = vec.Num();
		Buff = SVector<T>(vec);				//基于向量的复制构造方法
		Heapify();
	}
	virtual ~PQ_BinHeap(){}
	virtual void Insert(const T& e)				//插入元素T
	{
		Buff.Insert(e);
		_size++;
		//Buff[_size++] = e;
		PercolateUp(_size-1);
	}
	virtual int Size() const 
	{
		return _size;
	}
	virtual T DeleteMax()
	{
		T t = Buff[0];
		Buff[0] = Buff[--_size];
		Buff.Remove(_size);			//将不用的一位从向量中删除,且是最后一位,效率高
		PercolateDown(0);
		return t;
	}
	virtual T GetMax()
	{
		return Buff[0];
	}
};

#endif //PQ_BINHEAP_H