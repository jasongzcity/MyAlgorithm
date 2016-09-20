/* Created at 2015-12-9 22:33:21 by LWZ */
#ifndef ASONSTACK_H
#define ASONSTACK_H

#include "ADT_Vector.h"

//继承过来后DataBuff做栈底,Size继续用形容个数,Capacity形容规模
//级别为protected
//DEFAULT_CAPACITY,Rank,还可继续使用,回去VC的析构形式
//不同在于栈是仅对栈顶进行操作(除遍历),故只需添加几个对栈顶的操作方法,以及自身独有的构造和析构方法
template<typename T>
class SStack :public SVector<T>
{
//有了向量做父类,就没必要添加栈顶指针了
private:
//	T* StackBase;       //需要有栈底.类似于两个类共享了公共数据???
public:

	//构造函数
	SStack(int c = DEFAULT_CAPACITY,int s = 0,T e = 0)
		:SVector(c,s,e)		//默认构造方法
	{
//		Capacity = c;
//		DataBuff = new T[c];		//拓展起始空间
//		for(Size=0;Size<s;Size++)	
//		{
//			DataBuff[Size] = e;		//设置数据默认值	留意:这里默认了类型T重载了运算符[]
//		}
		//注意!不可直接调用SVector.查阅得知直接创建子类对象时会顺便创建父类对象???待查阅!
//		cout<<"stack"<<endl;
	}

	SStack(SStack<T> const& S,Rank lo,Rank hi)
	{
		CopyFrom(S.DataBuff,lo,hi);
	}

	SStack(T const* A,Rank lo,Rank hi)
	{
		CopyFrom(A,lo,hi);
	}

	//析构函数

	~SStack()
	{
//		cout<<"delete S"<<endl;
	}

	//可写函数
	int Push(T const& e)	//入栈,返回当前栈内元素个数
	{
		Insert(e,Size);		//相当于末尾插入
		return Size;
	}

	T& Top()				//返回栈顶的元素
	{
		return DataBuff[Size-1];
	}

	T Pop()					//直接删除栈顶元素并返回元素
	{
		return Remove(Size-1);
	}

};//SStack

#endif //ASONSTACK_H

