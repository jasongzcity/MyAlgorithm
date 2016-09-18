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

//��KV����Ϊ�����������ģ��,KΪ�ؼ���,VΪ��ֵ
template<typename K,typename V>
class SkipList:public List< QuadList<Entry<K,V>>* >,public Dictionary<K,V>	//�����а���������ָ��,�ڵ��а�����������
{
	//������List��ʵ��,SkipList�ṩ��������
protected:
	bool skipSearch(ListNode<QuadList<Entry<K,V>>*>* &qlist,QuadListNode<Entry<K,V>>* &p,K& k)
	{
		while(1)
		{
			while(p->succ && (p->Entry.key)<=k)	//�������һ��С�ڵ���k�Ĺؼ���ڵ�,�˻������ǵ�һ��key�ͱ�k����,��ôp��ʱΪheader
			{
				p = p->succ;					
			}
			p = p->pred;						//��ʱ����Ŀ��ڵ�
			if(p->pred && p->Entry.key == k)	//�ؼ������p->pred��������Ϊp�п���������quadlist��header
			{
				return true;					//��ʱp������Ҳ��ȡ����ȷ��ָ����ϲ�
			}
			qlist = qlist->succ;
			if(!qlist->succ)					//˵����ʱqlist��list�е�trailer
			{
				return false;					//����ʧ��,��������ʧ��,��ʱpҲָ����ײ��header,��p�ĺ�̲���ڵ�Ҳ����ȷ��!
			}
			p = (p->pred)?p->below:qlist->data->First();	//ָ��quadlist��һ�ڵ�
		}
	}
public:
	//ʵ���鷽��
	virtual bool Put(K k,V v)		//ע����Map�б�,����ؼ����ظ�,�ʱ�Ȼ�ɹ���1/2^n�ļ������½ڵ����ϳ�n��
	{								//(��,����ǰ��һ��Ӳ��,���벻����1/2����)
		Entry<K,V> E = Entry<K,V>(k,v);
		if(IsEmpty())		//�������
		{
			InsertAsFirst(new QuadList<Entry<K,V>>);				//�����һ����������,����������е����������ָ��
		}
		ListNode<QuadList<Entry<K,V>>*>* qlist = First();			//����߲㿪ʼ��������
		QuadListNode<Entry<K,V>>* qnode = qlist->data->First();
		if(skipSearch(qlist,qnode,k))				//�����ҳɹ�
		{
			while(qnode->below)
			{
				qnode = qnode->below;
			}
		}											
		qlist = Last();								//����,��qnodeָ����ײ�ĺ��ʲ���λ��
		QuadListNode<Entry<K,V>>* base = qlist->data->InsertAfterAbove(E,qnode,NULL);	//����ײ����,��׼������
		while(rand()%2)								//�ظ�����
		{
			while(!qnode->above && qlist->data->Valid(qnode))				//������һ�������ڵ�λ��
			{	
				qnode = qnode->pred;		//����qnode�п��ܻ���headerλ��
			}
			if(!qlist->data->Valid(qnode))		//qnode����header�Ļ�
			{
				if(qlist == First())			//��qlist���������һ��
				{
					InsertAsFirst(new QuadList<Entry<K,V>>());	
				}
				qnode = qlist->pred->data->First()->pred;					//��qnode�����ϲ�header
			}
			else
			{
				qnode = qnode->above;
			}
			qlist = qlist->pred;
			//��ʱqlist��Ӧ�����,����ڵ�Ӧ��qnode֮��,base֮��
			base = qlist->data->InsertAfterAbove(E,qnode,base);
		}
		return true;
	}
	virtual V* Get(K k)				//��ȡ
	{
		ListNode<QuadList<Entry<K,V>>*>* qlist = First();			//����߲㿪ʼ��������
		QuadListNode<Entry<K,V>>* qnode = qlist->data->First();
		return skipSearch(qlist,qnode,k)?&qnode->Entry.value:NULL;	//����������Ծ���ҹؼ���k,�ɹ��������ݵĵ�ַ,ʧ�ܷ��ؿյ�ַ
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
			return false;				//����ʧ��ֱ�ӷ���false
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