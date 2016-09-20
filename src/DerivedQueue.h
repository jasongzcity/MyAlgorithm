/* Created on 2015-12-8 21:31:52 Author :LWZ*/
#ifndef DERIVEDQUEUE_H
#define DERIVEDQUEUE_H
#include "ADT_ListNode.h"	//���ǵ����е�ͷβ����,��ջ��ͬ,��������������ƶ�Ԫ��.���������Ϊ����

template <typename T>
class SQueue :public List<T>
{
	//First()�Ƕ�ͷ,Last()�Ƕ�β
	//��β�����ͷɾ
	//Size()���п�List���Ѿ�����
public:
	//���캯��
	SQueue()
	{
		init();		//����ͷβ�ڱ�
	}

	SQueue(SQueue<T> & q)		//���帴��
	{
		init();
		CopyNodes(q.First(),q.Size());
	}

	//��������,�޲���,ɾ���ѿռ�����~List()
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