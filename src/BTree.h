/* Created on 2016-1-19 22:09:42 by LWZ. Template for BTree & BTreeNode */

#define BTREE_H

#ifndef ADT_VECTOR_H
#include "ADT_Vector.h"
#endif
#ifndef OBJECTFUNCIONS_H
#include "ObjectFunctions.h"
#endif
#ifndef ASONSTACK_H
#include "ASonStack.h"
#endif

#define BTNodePosi(T) BTreeNode<T>* //B-���ڵ�ָ��

//�ڵ�ģ��,�ؼ�������ΪT
template<typename T>
class BTreeNode
{
public:
	BTNodePosi(T) parent;			//���ڵ�
	SVector<T> key;					//�ؼ�������
	
	SVector<BTNodePosi(T)> child;	//�ӽڵ��ַ�������	�ر�����!�����ӽڵ�������Size������,���������п�!
public:
	//���췽��
	BTreeNode()
	{
		parent = NULL;
		child.Insert(NULL,0);
		//������Ĭ�Ϲ��캯���н�Ԫ�ض���0
	}
	BTreeNode(T e,BTNodePosi(T) lc = NULL,BTNodePosi(T) rc = NULL)
	{
		parent = NULL;	
		key.Insert(e,0);	//������Ԫ��
		child.Insert(lc,0);
		child.Insert(rc,1);
		if(lc)					
		{
			lc->parent = this;		//˽�����ͳ�Ա,�������???
		}
		if(rc)
		{
			rc->parent = this;
		}
	}

	virtual ~BTreeNode()	//��������
	{
		//for(int i=0;i<child.capacity();i++)		//�˴��������ݹ�����
		//{
		//	delete child[i];		//���µݹ�ɾ���ӽڵ�
		//}
		//���µݹ�ɾ��Ӧ����������������,�ڵ������޲���
		//cout<<"clean node"<<endl;
	}
};//class BTreeNode


//B��ģ��,TΪ���йؼ�������
template<typename T>
class BTree
{
private:
	int _order;				//B������-����ʱȷ��,һ�㲻���޸�
protected:
	int _size;				//�ؼ������
	BTNodePosi(T) _root;	//����
	BTNodePosi(T) _hot;		//���нڵ�ĸ���

	void OverFlow(BTNodePosi(T) v)	//�������
	{
		if(v->key.Num()<_order)		//�ؼ������Ӧ��С�ڽ���,��δ����
		{
			return;
		}
		Rank r = _order/2;			//�ָ�����

		//�����Ҳ๲��_order-1-r���ؼ����_order-r������
		BTNodePosi(T) right = new BTreeNode<T>();		//�����ұ߽ڵ�,��ʱ�Ѿ���һ�����ӽڵ���
		for(int i=0;i<_order-r-1;i++)					//�ұ�Ӧ����_order-1-r���ؼ���
		{
			right->key.Insert(v->key.Remove(r+1),i);	//ɾ��v��ԭ��r�Ҳ�Ĺؼ���,��ӵ�right�ڵ���
			right->child.Insert(v->child.Remove(r+1),i);//ɾ��ԭ�к���,Ǩ�Ƶ�right�ڵ���
		}
		right->child[_order-r-1] = v->child.Remove(r+1);//���ӱȹؼ����1,���һ��Ǩ�ƹ���,����ԭ����ʼ�����Ŀպ���
		
		if(right->child[0])				//�ǿ�,����Ҷ�ӽڵ�,�к���,��ָ���µĸ���
		{								//b���ص�,�к�����ض���һȺ
			for(int i=0;i<_order-r;i++)	//_order-r������
			{
				right->child[i]->parent = right;
			}
		}
		BTNodePosi(T) p = v->parent;
		if(!p)				//pΪ����vΪ��
		{
			p = _root = new BTreeNode<T>();	//�����µĸ�
			v->parent = p;
			p->child[0] = v;				//���ø��ӹ�ϵ
		}
		Rank ins = 1 + p->key.BinSearch(v->key[0]);		//�ҵ�p���ڵ��ҵ����ʵ�r�Ĳ����,��Ϊinsλ���ǵ�ǰָ��v��,���ñ��
														//��,��insλ�ò���v����Ϊr�Ĺؼ����Ƿ����������ı�׼��
		p->key.Insert(v->key.Remove(s),ins);			//ɾ�����,���뵽���ڵ���
		p->child.Insert(right,ins+1);					//����֮ǰ���õ��ҽڵ�
		right->parent = p;
		OverFlow(p);									//���ϵݹ�
	}

	void UnderFlow(BTNodePosi(T) v)	//�������
	{
		if( v->child.Num() >= (_order+1)/2 )			//n/2����ȡ����(n+1)/2,����ȡ��ֱ����n/2
		{
			return;
		}
		BTNodePosi(T) p = v->parent;					//�ҵ����ڵ�
		if(!p)						//v�Ǹ��ڵ�,��ɾ���ض�ɾ�����ǹؼ����������
		{
			if((v->key.Num()<1) && v->child[0])		//���ڵ����޹ؼ���,���Ȼֻʣһ���ӽڵ�(�ӽڵ�Ҳ�п����ǿյ�,�������������
			{										//�൱�ڿ���)
				_root = v->child[0];				//�ƿ�v�ڵ�
				_root->parent = NULL;
				delete v;
			}
			return;
		}
		//Rank r = p->key.BinSearch(v->key[0]);		//ȷ��p��v��λ��..ע��:���ܲ���,�п��ܲ����ؼ���
		//�˴���ʼ�������
		Rank r = 0;
		while(p->child[r] != v)
		{
			r++;
		}
		if(r>0 && (p->child[r-1]->key.Num() > (_order+1)/2))	//���ӿ���
		{
			BTNodePosi(T) lb = p->child[r-1];	//��¼���ֵ�
			v->key.Insert(lb->key.Remove(lb->key.Num()-1),0);	//�����ֵ����ҹؼ���ɾ�������뵽v�������
			Swap(p->key[r],v->key[0]);			//�ٽ����߽���(�����ⲿ��������??,���о�����Ѻ���)
			v->child.Insert(lb->child.Remove(lb->child.Num()-1),0);	//���ӹ���
			if(v->child[0])	//���ӷǿ�
			{
				v->child[0]->parent = v;	//ָ��̸�
			}
			//��ʱ���ڵ㼰���ֵܶ���������,����ݹ����
		}
		else if( (r<p->child.Num()-1) && (p->child[r+1]->key.Num()>(_order+1)/2) )		//���ֵܴ���,�ҹؼ��빻��
		{
			BTNodePosi(T) rb = p->child[r+1];	//���ֵ�
			v->key.Insert(rb->key.Remove(0),v->key.Num());	//ɾ�����ֵ������ؼ���,���뵽v�е����Ҳ�
			Swap(p->key[r+1],v->key[v->key.Num()-1]);		//����λ��
			v->child.Insert(rb->child.Remove(0),v->child.Num());	//�����ӹ���Ϊ���Һ���;
			if(v->child[v->child.Num()-1])		//�ǿյĻ�
			{
				v->child[v->child.Num()-1]->parent = v;
			}
			//��ʱ���ڵ㼰���ֵܶ���������,����ݹ����
		}
		else
		{
			if(r>0)		//���ֵܴ���
			{
				BTNodePosi(T) lb = p->child[r-1];
				lb->key.Insert(p->key.Remove(r-1));		//ĩβ���븸�ڵ���Ԫ��
				p->child.Remove(r);						//ɾ��p��ָ��v��ָ��
				while(!(v->key.IsEmpty()))				//�����йؼ���ǿ�ʱ�������ؼ��뼰���Ӱ������ֵ�
				{
					lb->key.Insert(v->key.Remove(0));
					lb->child.Insert(v->child.Remove(0));
				}
				lb->child.Insert(v->child.Remove(0));	//����v�йؼ���ͺ���ȫ��ת�����
				if(lb->child[0])	//���к���
				{
					Rank i=0;
					while(i<lb->child.Num())
					{
						lb->child[i]->parent = lb;
						i++;
					}
				}
				delete v;			//�Ѿ���v�ĺ��Ӽ����׽������ɾ��v��
			}//���ֵ�����������
			else					//�������ֵ�,�������ֵ�child[r+1],�����ֵܺϲ�
			{
				BTNodePosi(T) rb = p->child[r+1];
				v->key.Insert(p->key.Remove(r));
				p->child.Remove(r+1);			//�Ӹ��״�ɾ��ָ�����ֵܵ�ָ��
				while(!(rb->key.IsEmpty()))		//�����ֵܷǿ�ʱ,��������
				{
					v->key.Insert(rb->key.Remove(0));
					v->child.Insert(rb->child.Remove(0));
				}
				v->child.Insert(rb->child.Remove(0));
				if(v->child[0])		//���ӷǿ�
				{
					Rank i=0;
					while(i<v->child.Num())
					{
						v->child[i++]->parent = v;	//���ӹ���
					}
				}
				delete rb;
			}//���ֵܺϲ��������
			UnderFlow(p);				//������´Ӹ��״�"��"��һ�ؼ���,���鸸�׽ڵ�Ľڵ���,�ݹ�
		}//�����ֵܹؼ��붼����������������
	}

public:
	BTree()	//Ĭ�Ϲ��캯��
	{
		_root = new BTreeNode<T>();
	}
	virtual ~BTree()
	{
		//delete _root;		//�������µݹ�����
		//�ݹ�����Ҫ��д
		BTreeDestructor(_root);
	}
	void BTreeDestructor(BTNodePosi(T) p)
	{											//B���ݹ�����,�ݹ����O(Height(T))
		Rank i = 0;
		while(i<p->child.Num())
		{
			if(p->child[i])		//�ǿ�
			{
				delete p->child[i];
			}
			i++;
		}
		delete p;		//������ɾ����Ϻ�,��ɾ���ڵ�����
	}
	//��ѯ����
	int Order() const		//������ѯ
	{
		return _order;
	}
	int Size() const		//�ؼ��������ѯ
	{
		return _size;
	}
	BTNodePosi(T)& Root()	//��������������!�������޸�����
	{
		return _root;
	}
	bool IsEmpty() const
	{
		return !_root;
	}
	BTNodePosi(T) Search(T const& e)	//���ҹؼ���e
	{
		BTNodePosi(T) v = _root;
		_hot = NULL;
		while(v)
		{
			int r = v->key.BinSearch(e);	//���ﷵ�����һ��С�ڵ���e����(�п�����-1)
			if((r>-1) && (e==key[r]))		//�������ɹ�
			{
				return v;					//���ؽڵ��ַ
			}
			//else
			_hot = v;
			v = v->child[r+1];
		}
		return NULL;						//����ʧ�ܷ���NULL ͬʱ,Ҳ�ǲ���Ԫ��e�ĺ���λ��.��_hot��¼�Ĳ���λ�õĸ��ڵ�
	}

	bool Insert(T const& e)				//����ؼ���e
	{
		BTNodePosi(T) v = Search(e);
		if(v)							//���Ѵ���,����ʧ��
		{
			return false;
		}
		//else
		int r = _hot->key.BinSearch(e);	//�����в���λ��
		_hot->key.Insert(e,r+1);
		_hot->child.Insert(NULL,r+2);	//�ӽڵ������ڳ�ʼ��ʱ�Ѿ�������һ����,���������һֱ�����ӽڵ��뵱ǰ�ڵ�Ԫ�ص�"����"
		_size++;
		OverFlow(_hot);					//�������
		return true;
	}

	bool Remove(T const& e)				//ɾ���ؼ���e ע��,����ɾ���ıض���Ҷ��
	{
		BTNodePosi(T) p = Search(e);
		if(!p)							//��������
		{
			return false;
		}
		Rank r = p->key.BinSearch(e);
		if(p->child[0])					//������,���Ҷ��,���Һ��
		{
			BTNodePosi(T) next = p->child[r+1];	//���������Һ��
			while(next->child[0])				//0����Ϊ��̱�Ȼ������������С��
			{
				next = next->child[0];			//������
			}
			p->key[r] = next->key[0];			//����λ��
			p = next;
			r = 0;
		}
		//���������,�ڵ�p��rλ�þ���Ҫɾ���Ĺؼ����ұ�ΪҶ�ڵ�(�ӽڵ㶼�ǿ�,���ɾ)
		p->key.Remove(r);
		p->child.Remove(r+1);	//ɾǰһλ
		_size--;
		UnderFlow(p);			//p�ڵ��п�������
	}
};//class BTree

