/* Created on 2016-2-14 16:33:29 by LWZ. Template for LeftHeap */
#ifndef PQ_LEFTHEAP_H
#define PQ_LEFTHEAP_H

#include "PQInterface.h"
#include <BinTree.h>

template <typename T>
class PQ_LeftHeap:public PQ<T>,protected BinTree<T>				//不暴露二叉树的接口
{
protected:
	BinNodePosi(T) merge(BinNodePosi(T) a,BinNodePosi(T) b)		//归并以a和b为根的两个堆-递归版
	{
		if(!a)
		{
			return b;
		}
		if(!b)								//极特殊情况下才会有b=null,递归过程中b!=null(只有插入空节点时才会有b=null)
		{
			return a;
		}
		if(a->Data<b->Data)
		{
			Swap(a,b);						//交换a\b指针指向,确保a指向优先级高的节点
		}
		a->RChild = merge(a->RChild,b);		//返回后应该先对接父节点
		a->RChild->Parent = a;
		if(!a->LChild || a->LChild->Npl<a->RChild->Npl)	//后比较a左右孩子的npl
		{
			Swap(a->RChild,a->LChild);
		}
		a->Npl = a->RChild?a->RChild->Npl+1:1;	//最后更新a自身npl(递归基npl就等于默认值1无需更改)
		return a;
	}
	BinNodePosi(T) merge_Iter(BinNodePosi(T) a,BinNodePosi(T) b)			//归并以a和b为根的两个堆-迭代版
	{
		SStack<BinNodePosi(T)> stack;			//临时存放栈
		while(a && b)
		{										//只要进入循环了,b!=NULL
			if(a->Data<b->Data)					
			{
				Swap(a,b);						//确保a大
			}
			stack.Push(a);
			a = a->RChild;
		}										//此时,栈弹出的顺序就是合并后左式堆的右NPL的逆序
												//除了一个"悬空"的b							
		while(!stack.IsEmpty())					//栈非空时
		{
			a = stack.Top();
			a->RChild = b;
			b->Parent = a;
			if(!a->LChild || a->LChild->Npl<a->RChild->Npl)	
			{
				Swap(a->RChild,a->LChild);
			}
			a->Npl = a->RChild?a->RChild->Npl+1:1;	//最后更新a自身npl(递归基npl就等于默认值1无需更改)
			b = stack.Pop();
		}											//至循环结束,结连完毕.此时a/b同时指向合并结束的堆顶
		//检查边界条件,a或b不存在的情况下
		return a?a:b;
	}
public:
	PQ_LeftHeap(){}					//默认构造与二叉树默认构造相同
	PQ_LeftHeap(T* A,int size)		//从数组复制构造,用Floyd方法建堆
	{								//复杂度从O(nlogn)降到O(n)
		for(int i=0;i<size;i++)
		{
			Insert(A[i]);
		}
	}
	virtual void Insert(const T& e)
	{
		BinNode<T>* node = new BinNode<T>(e);	//构建新节点
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