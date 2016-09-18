/* Created on 2016-2-14 16:33:29 by LWZ. Template for LeftHeap */
#ifndef PQ_LEFTHEAP_H
#define PQ_LEFTHEAP_H

#include "PQInterface.h"
#include <BinTree.h>

template <typename T>
class PQ_LeftHeap:public PQ<T>,protected BinTree<T>				//����¶�������Ľӿ�
{
protected:
	BinNodePosi(T) merge(BinNodePosi(T) a,BinNodePosi(T) b)		//�鲢��a��bΪ����������-�ݹ��
	{
		if(!a)
		{
			return b;
		}
		if(!b)								//����������²Ż���b=null,�ݹ������b!=null(ֻ�в���սڵ�ʱ�Ż���b=null)
		{
			return a;
		}
		if(a->Data<b->Data)
		{
			Swap(a,b);						//����a\bָ��ָ��,ȷ��aָ�����ȼ��ߵĽڵ�
		}
		a->RChild = merge(a->RChild,b);		//���غ�Ӧ���ȶԽӸ��ڵ�
		a->RChild->Parent = a;
		if(!a->LChild || a->LChild->Npl<a->RChild->Npl)	//��Ƚ�a���Һ��ӵ�npl
		{
			Swap(a->RChild,a->LChild);
		}
		a->Npl = a->RChild?a->RChild->Npl+1:1;	//������a����npl(�ݹ��npl�͵���Ĭ��ֵ1�������)
		return a;
	}
	BinNodePosi(T) merge_Iter(BinNodePosi(T) a,BinNodePosi(T) b)			//�鲢��a��bΪ����������-������
	{
		SStack<BinNodePosi(T)> stack;			//��ʱ���ջ
		while(a && b)
		{										//ֻҪ����ѭ����,b!=NULL
			if(a->Data<b->Data)					
			{
				Swap(a,b);						//ȷ��a��
			}
			stack.Push(a);
			a = a->RChild;
		}										//��ʱ,ջ������˳����Ǻϲ�����ʽ�ѵ���NPL������
												//����һ��"����"��b							
		while(!stack.IsEmpty())					//ջ�ǿ�ʱ
		{
			a = stack.Top();
			a->RChild = b;
			b->Parent = a;
			if(!a->LChild || a->LChild->Npl<a->RChild->Npl)	
			{
				Swap(a->RChild,a->LChild);
			}
			a->Npl = a->RChild?a->RChild->Npl+1:1;	//������a����npl(�ݹ��npl�͵���Ĭ��ֵ1�������)
			b = stack.Pop();
		}											//��ѭ������,�������.��ʱa/bͬʱָ��ϲ������ĶѶ�
		//���߽�����,a��b�����ڵ������
		return a?a:b;
	}
public:
	PQ_LeftHeap(){}					//Ĭ�Ϲ����������Ĭ�Ϲ�����ͬ
	PQ_LeftHeap(T* A,int size)		//�����鸴�ƹ���,��Floyd��������
	{								//���Ӷȴ�O(nlogn)����O(n)
		for(int i=0;i<size;i++)
		{
			Insert(A[i]);
		}
	}
	virtual void Insert(const T& e)
	{
		BinNode<T>* node = new BinNode<T>(e);	//�����½ڵ�
		_root = merge(_root,node);
		_root->Parent = NULL;
		_size++;
	}
	virtual int Size() const
	{
		return _size;
	}
	virtual T DeleteMax()
	{
		BinNodePosi(T) lc = _root->LChild;
		BinNodePosi(T) rc = _root->RChild;
		T data = _root->Data;
		delete _root;
		_size--;
		_root = merge(lc,rc);
		if(_root)
		{
			_root->Parent = NULL;
		}
		return data;
	}
	virtual T GetMax()
	{
		return _root->Data;
	}
};//class LeftHeap

#endif //PQ_LEFTHEAP_H