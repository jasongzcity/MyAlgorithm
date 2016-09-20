/* **********************************************************************
 * Created on 2016-1-7 10:24:24 by LWZ.
 * Header file for Binary Search Tree
 * contains template for BST
 * *********************************************************************/

#define BST_H

#ifndef ENTRY_H
#include "Entry.h"		//��������
#endif
#ifndef BINTREE_H
#include "BinTree.h"
#endif

template<typename T>
class BST : public BinTree<T>				//ֻ����������ӿ��������ڲ�����
{
protected:
	BinNodePosi(T) _hot;			//���нڵ�ĸ���,�����㷨�г���

	//"34"���������ڵ��ĸ�Ҷ�ڵ�
	BinNodePosi(T) connect34(BinNodePosi(T) a,BinNodePosi(T) b,BinNodePosi(T) c,BinNodePosi(T) T0,BinNodePosi(T) T1,
		BinNodePosi(T) T2,BinNodePosi(T) T3)							//����ϸ��,ֱ�ӹ���3����4����,���ٷ־����������
	{																	//ʱ�临�Ӷ��ǳ���!a.b.cһ������NULL!
		a->LChild = T0;
		if(T0)
		{
			T0->Parent = a;
		}
		a->RChild = T1;
		if(T1)
		{
			T1->Parent = a;
		}
		UpdateHeight(a);
		c->LChild = T2;
		if(T2)
		{
			T2->Parent = c;
		}
		c->RChild = T3;
		if(T3)
		{
			T3->Parent = c;
		}
		UpdateHeight(c);
		a->Parent = c->Parent = b;
		b->LChild = a;
		b->RChild = c;
		UpdateHeight(b);
		//UpdateHeightAbove(b);	//���Ӧ����������һ�������ʹ��
		return b;
	}
	
	//��ת
	//����connect34������ʵ����ת
	BinNodePosi(T) rotateAt(BinNodePosi(T) v)		//����:��ʱv�Ƿǿ��ﱲ���.������ת��� ����3+4���ӷ����ĸ��ڵ�
	{																			//�������λ��v.p.g��������� zig - ˳ʱ��.zag - ��ʱ��
		BinNodePosi(T) p = v->Parent;						//���conncet34�������̸��Ӷ���Ȼ�ǳ��������
		BinNodePosi(T) g = p->Parent;
		if(IsLChild(*p))	//p������
		{
			if(IsLChild(*v))		//p˳ʱ����ת
			{
				p->Parent = g->Parent;		//���Ӹ��ڵ�
				return connect34(v,p,g,v->LChild,v->RChild,p->RChild,g->RChild);
			}
			else					//p��ʱ����ת
			{
				v->Parent = g->Parent;
				return connect34(p,v,g,p->LChild,v->LChild,v->RChild,g->RChild);
			}
		}
		else						//p���Һ���
		{
			if(IsLChild(*v))		//p˳ʱ����ת
			{
				v->Parent = g->Parent;
				return connect34(g,v,p,g->LChild,v->LChild,v->RChild,p->RChild);
			}
			else					//p��ʱ����ת
			{
				p->Parent = g->Parent;
				return connect34(g,p,v,g->LChild,p->LChild,v->LChild,v->RChild);
			}
		}
	}


	BinNodePosi(T)& SearchIn(BinNodePosi(T)& x,T const& e,BinNodePosi(T)& hot)	//�ڲ�����,���ڵݹ�����Ԫ��e Ϊ�η�������??
	{																			//hot��¼�������нڵ�ĸ���
		if(!x || (x->Data == e))		//�ݹ��,���޸�Ԫ��e��Ӧ�ڵ��򷵻�NULL	//����,���ﷵ�ص�������,����ֱ�����ϲ��޸����Һ����൱��
		{
			return x;
		}
		//׼�����µݹ�
		hot = x;					//��¼����	
		BinNodePosi(T)& v = (x->Data > e)? x->LChild:x->RChild;		//��������������		
		return SearchIn(v,e,hot);
	}

	BinNodePosi(T) RemoveAt(BinNodePosi(T)& x,BinNodePosi(T)& hot)	//hot��¼ɾ���ڵ�ĸ���,x��¼ɾ���ڵ�ļ����ߵ�ַ
	{
		//��Swap()ģ���ֱ��ʹ��
		BinNodePosi(T) w = x;		//ɾ���ڵ�ĵ�ַ
		BinNodePosi(T) succ = NULL;	//��¼ɾ����Ľڵ�ļ�����
		if(!HasLChild(*x))	//�������ӻ��޺���
		{
			succ = x->RChild;	//ָ���Һ���
			if(IsRoot(*x))
			{
				x = _root = succ;
			}
			else
			{
				//�ƿ�ɾ���ڵ�
				if(IsRChild(*x))
				{
					x = x->Parent->RChild = succ;
				}
				else
				{
					x = x->Parent->LChild = succ;
				}
			}
		}
		else if(!HasRChild(*x))
		{
			succ = x = x->LChild;	//ָ������
			if(IsRoot(*x))
			{
				x = _root = succ;
			}
			else
			{
				//�ƿ�ɾ���ڵ�
				if(IsRChild(*x))
				{
					x = x->Parent->RChild = succ;
				}
				else
				{
					x = x->Parent->LChild = succ;
				}
			}
		}
		else						//���Һ��Ӷ���
		{
			w = w->Succ();
			Swap(w->Data,x->Data);
			hot = w->Parent;		//��¼����
			//(temp == x)? temp->RChild:temp->LChild = succ = w->RChild;
			succ = w->RChild;		//�п�����
			if(hot == x)
			{
				hot->RChild = succ;
			}
			else
			{
				hot->LChild = succ;
			}
		}
		if(succ)		//�������ߴ���
		{
			succ->Parent = hot;		//�ƿ�ɾ������
		}
		delete w;
		return succ;
	}

public:
	//�鷽��,���������д

	//ȫ������Ԫ��e
	virtual BinNodePosi(T)& Search(T const& e)
	{
		return SearchIn(_root,e,_hot = NULL);
	}
	//ȫ��ɾ���ؼ���e
	virtual bool Remove(T const& e)
	{
		BinNodePosi(T)& x = Search(e);
		if(!x)
		{
			return false;	//��x�ǿ��򷵻�false,ɾ��ʧ��
		}
		RemoveAt(x,_hot);
		_size--;
		UpdateHeightAbove(_hot);
		return true;
	}
	//�ں��ʵ�λ�ò���ؼ���e
	virtual BinNodePosi(T) Insert(T const& e)
	{
		BinNodePosi(T)& x = Search(e);		//�ҵ������
		if(x)			//����λ�����нڵ�
		{
			return x;	//��ֱ�ӷ���
		}
		//x = new BinNode<T>(e,_hot);		//���п�λ����붥�㲢���ø��ڵ�Ϊ_hot(���������Ѿ���¼����)
		//(e<_hot->Data)? _hot->LChild:_hot->RChild  = x;			//�Ӹ��׽ڵ㴦

		//�����������BinTree���Ѿ�����õ�!
		if(_hot == NULL)		//��ʱ�����
		{
			return InsertAsRoot(e);
		}

		if(e<_hot->Data)
		{
			_hot->InsertAsLC(e);
		}
		else
		{
			_hot->InsertAsRC(e);
		}
		//_size++;					//�����������!�ڻ���BinTree���Ѿ��и��¹�ģ�͸߶ȵĲ���
		//UpdateHeightAbove(_hot);
		return x;
	}

	BinNodePosi(T) TallerChild(BinNodePosi(T) x)		//���ÿ�ݷ�ʽ,�ò�ѯ�����ķ�ʽʵ��
	{													//���ظ��ߵĺ��ӽڵ��ָ��,���ȸ�,�򷵻���xͬ�ߵĽڵ�
		if(Stature(x->LChild) > Stature(x->RChild))
		{
			return x->LChild;
		}
		else
		{
			if(Stature(x->RChild) > Stature(x->LChild))
			{
				return x->RChild;
			}
			else										//���ߵȸ�
			{
				if(IsLChild(*x))
				{
					return x->LChild;
				}
				else
				{
					return x->RChild;
				}
			}//else(���ȸ�)
		}//else(�����߲�С������)
	}//����β

};//class BST