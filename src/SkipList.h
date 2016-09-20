/* Created on 2016-1-24 16:37:56 by LWZ. Template for SkipList */

#define SKIPLIST_H

#include "QuadList.h"
#include "Shared\Dictionary.h"
#ifndef ADT_LISTNODE_H
#include "ADT_ListNode.h"
#endif
#ifndef ENTRY_H
#include "Entry.h"
#endif
#include <cstdlib>

//以KV词条为基础搭建的跳表模板,K为关键码,V为数值
template<typename K,typename V>
class SkipList:public List< QuadList<Entry<K,V>>* >,public Dictionary<K,V>	//链表中包含四联表指针,节点中包含词条对象
{
	//数据在List中实现,SkipList提供搜索方法
protected:
	bool skipSearch(ListNode<QuadList<Entry<K,V>>*>* &qlist,QuadListNode<Entry<K,V>>* &p,K& k)
	{
		while(1)
		{
			while(p->succ && (p->Entry.key)<=k)	//查找最后一个小于等于k的关键码节点,退化条件是第一个key就比k大了,那么p此时为header
			{
				p = p->succ;					
			}
			p = p->pred;						//此时跳到目标节点
			if(p->pred && p->Entry.key == k)	//关键码相等p->pred条件是因为p有可能现在是quadlist的header
			{
				return true;					//此时p的引用也获取了正确的指针给上层
			}
			qlist = qlist->succ;
			if(!qlist->succ)					//说明此时qlist是list中的trailer
			{
				return false;					//搜索失败,即便搜索失败,此时p也指向最底层的header,在p的后继插入节点也是正确的!
			}
			p = (p->pred)?p->below:qlist->data->First();	//指向quadlist第一节点
		}
	}
public:
	//实现虚方法
	virtual bool Put(K k,V v)		//注意与Map有别,允许关键码重复,故必然成功有1/2^n的几率由新节点向上长n层
	{								//(即,长高前丢一次硬币,长与不长各1/2几率)
		Entry<K,V> E = Entry<K,V>(k,v);
		if(IsEmpty())		//若链表空
		{
			InsertAsFirst(new QuadList<Entry<K,V>>);				//插入第一个四联表链,留意链表持有的是四联表的指针
		}
		ListNode<QuadList<Entry<K,V>>*>* qlist = First();			//从最高层开始向下搜索
		QuadListNode<Entry<K,V>>* qnode = qlist->data->First();
		if(skipSearch(qlist,qnode,k))				//若查找成功
		{
			while(qnode->below)
			{
				qnode = qnode->below;
			}
		}											
		qlist = Last();								//至此,将qnode指向最底层的合适插入位置
		QuadListNode<Entry<K,V>>* base = qlist->data->InsertAfterAbove(E,qnode,NULL);	//在最底层插入,并准备建塔
		while(rand()%2)								//重复建塔
		{
			while(!qnode->above && qlist->data->Valid(qnode))				//搜索下一个有上邻的位置
			{	
				qnode = qnode->pred;		//留意qnode有可能会在header位置
			}
			if(!qlist->data->Valid(qnode))		//qnode等于header的话
			{
				if(qlist == First())			//若qlist又是链表第一个
				{
					InsertAsFirst(new QuadList<Entry<K,V>>());	
				}
				qnode = qlist->pred->data->First()->pred;					//令qnode等于上层header
			}
			else
			{
				qnode = qnode->above;
			}
			qlist = qlist->pred;
			//此时qlist对应插入层,插入节点应在qnode之后,base之上
			base = qlist->data->InsertAfterAbove(E,qnode,base);
		}
		return true;
	}
	virtual V* Get(K k)				//读取
	{
		ListNode<QuadList<Entry<K,V>>*>* qlist = First();			//从最高层开始向下搜索
		QuadListNode<Entry<K,V>>* qnode = qlist->data->First();
		return skipSearch(qlist,qnode,k)?&qnode->Entry.value:NULL;	//在跳表中跳跃查找关键码k,成功返回数据的地址,失败返回空地址
	}
	virtual int Size() const
	{
		return IsEmpty()?0:Last()->data->Size();
	}
	int Level() const
	{
		return List::Size();
	}
	virtual bool Remove(K k)
	{
		if(IsEmpty())
		{
			return false;
		}
		ListNode<QuadList<Entry<K,V>>*>* qlist = First();
		QuadListNode<Entry<K,V>>* qnode = qlist->data->First();
		if(!skipSearch(qlist,qnode,k))
		{
			return false;				//查找失败直接返回false
		}
		QuadListNode<Entry<K,V>>* below = NULL;
		while(qnode)
		{
			below = qnode->below;
			qlist->data->Remove(qnode);
			qlist = qlist->succ;
			qnode = below;
		}
		while(!(IsEmpty()) && (First()->data->IsEmpty()))
		{
			QuadList<Entry<K,V>>* QList = List::Remove(First());
			delete QList;
		}
		return true;
	}

	template<typename VST>
	void TraverseByLevel(VST& visit)
	{
		ListNode<QuadList<Entry<K,V>>*>* qlist = Last();
		int level = 1;
		cout<<endl;
		cout<<"Level:"<<level<<endl;
		while((qlist->pred) != NULL)
		{
			qlist->data->Traverse(visit);
			qlist = qlist->pred;
			cout<<endl;
			cout<<"Level:"<<++level<<endl;
		}
	}
};//SkipList