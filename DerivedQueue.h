/* Created on 2015-12-8 21:31:52 Author :LWZ*/
#ifndef DERIVEDQUEUE_H
#define DERIVEDQUEUE_H
#include "ADT_ListNode.h"	//考虑到队列的头尾操作,和栈不同,若用向量需大量移动元素.采用链表更为合适

template <typename T>
class SQueue :public List<T>
{
	//First()是队头,Last()是队尾
	//队尾插入队头删
	//Size()和判空List中已经包含
public:
	//构造函数
	SQueue()
	{
		init();		//开辟头尾哨兵
	}

	SQueue(SQueue<T> & q)		//整体复制
	{
		init();
		CopyNodes(q.First(),q.Size());
	}

	//析构函数,无操作,删除堆空间留给~List()
	virtual ~SQueue()
	{
	
	}

	int Enqueue(T const& e)
	{
		InsertAsLast(e);
		return Size();
	}

	T Dequeue()
	{
		return Remove(First());
	}

	T& Front()
	{
		return First()->data;
	}
};//class SQueue;

#endif //DERIVEDQUEUE_H