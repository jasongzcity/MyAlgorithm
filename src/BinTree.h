/* Created at 2015-12-9 22:32:23 Authot : LWZ */
#ifndef BINTREE_H
#define BINTREE_H

#include "ASonStack.h"
#include "DerivedQueue.h"
#include "ObjectFunctions.h"

#include <iostream>
using namespace std;

#define BinNodePosi(T) BinNode<T>*
typedef enum {RB_RED,RB_BLACK} RBColor;	//�ڵ���ɫ,������������

///////////////////////////////��ݷ�ʽ//////////////////////////////////
//ע��:���п�ݷ�ʽ�������������ʽ,���������ָ�����ʽ����(����Stature)
#define IsRoot(x) (!(x).Parent)
#define IsLChild(x) (!IsRoot(x) && ( &(x) == (x).Parent->LChild ))	//�ж�x�Ƿ�����
#define IsRChild(x) (!IsRoot(x) && ( &(x) == (x).Parent->RChild ))
#define HasParent(x) ((x).Parent)
#define HasLChild(x) ((x).LChild)
#define HasRChild(x) ((x).RChild)
#define HasChild(x) ((x).RChild || (x).LChild)
#define HasBothChild(x) ((x).LChild && (x).RChild)
#define IsLeaf(x) (!HasChild(x))
#define Stature(x) ((x)?(x)->Height:-1)
///////////���ݹ�ϵ�ڵ�//////////
#define Sibling(x) (IsLChild(x)? (x).Parent->RChild: (x).Parent->LChild)	//����x���ֵܽڵ��ָ��
#define Uncle(x) Sibling(((x).Parent))										//���������ָ��
#define FromParentTo(x) (IsLChild(x)? (x).Parent->LChild:(x).Parent->RChild)//���ظ��ڵ������

/////////////////////////////////////////////////////////////////////////
template <typename T>
class BinNode
{

	//���㿼��ȫ���趨Ϊ����
public:
	T Data;
	BinNodePosi(T) Parent;	//���ڵ�
	BinNodePosi(T) LChild;	//����
	BinNodePosi(T) RChild;	//�Һ���
	int Height;				//�ڵ�߶�,Ҳ�ǵ�ǰ�ڵ���Ϊ���ڵ�������ĸ߶�.Ҷ�ڵ�߶�0,�����߶�-1
	int Npl;				//��ʽ��
	RBColor Color;

	//���캯��
	BinNode()
		:Parent(NULL),LChild(NULL),RChild(NULL),Height(0),Npl(1),Color(RB_RED)
	{}
	BinNode(T e,BinNodePosi(T) p = NULL,BinNodePosi(T) l = NULL,BinNodePosi(T) r = NULL,int h = 0,int n = 1,RBColor c = RB_RED)
		:Data(e),Parent(p),LChild(l),RChild(r),Height(h),Npl(n),Color(c)		//������Ҫ���������=
	{}
	
	virtual ~BinNode()
	{}

	//�����ӿ�
	BinNodePosi(T) InsertAsLC(T const& e)		//��e��Ϊ��ǰ�ڵ�����Ӳ���(�������������ǰ�ڵ�������)
	{
		LChild = new BinNode(e,this);	//����������㲹��

		return LChild;
	}

	BinNodePosi(T) InsertAsRC(T const& e)		//��e��Ϊ�Һ��Ӳ���
	{
		RChild = new BinNode(e,this);
		return RChild;
	}

	int Size() const		//���ظýڵ������Ĺ�ģ
	{
		CountElement<T> C;
		PreTrav(C);
		return C.count;		//���ظ���
	}

	BinNodePosi(T) Succ()		//���ظýڵ��ֱ�Ӻ��(ͨ������ÿ���ڵ㶼�к��)������������ĺ��˳��
	{
		BinNodePosi(T) p = this;
		if(RChild)				//�����Һ���
		{
			p = RChild;
			while(HasLChild(*p))
			{
				p = p->LChild;
			}
		}
		else
		{
			while(IsRChild(*p))
			{
				p = p->Parent;
			}
			p = p->Parent;
		}
		return p;
	}

	//ϰ��5-14
	BinNodePosi(T) Pred() 
	{
		BinNodePosi(T) p = this;
		if(HasLChild(*p))
		{
			p = p->LChild;
			while(HasRChild(*p))
			{
				p = p->RChild;
			}		
			return p;
		
		}
		else if(IsRChild(*p))
		{
			return p->Parent;
		}
		else if(IsLChild(*p))
		{
			while(IsLChild(*p))
			{
				p = p->Parent;
			}
			if(IsRChild(*p))
			{
				return p->Parent;			//p��ʱ��Ϊ�������ĸ�
			}
			else
			{
				return NULL;				//ȫ�����������ͷ
			}
		}//if
	}



	//ͨ������������(ȫ��ʹ�õ�����)
	template <typename VST>
	void PreTrav(VST& visit)		//�������
	{
		SStack<BinNodePosi(T)> S;
		BinNodePosi(T) x = this;	//�ӵ�ǰ�ڵ㿪ʼ����
		while(1)
		{
			while(x)
			{
				visit(x->Data);
				S.Push(x->RChild);
				x = x->LChild;
			}
			if(S.IsEmpty())
			{
				break;
			}
			x = S.Pop();
		}
	}

	template <typename VST>
	void InTrav(VST& visit)			//�������
	{
		switch(rand()%2)
		{
		case 0:
			InTrav_V1(visit);
			break;
		case 1:
			InTrav_V2(visit);
			break;
		default:
			break;
		}
	}

	template <typename VST>
	void Intrav_V1(VST& visit)				//�汾1,���ø���ջ
	{
		SStack<BinNodePosi(T)> S;
		BinNodePosi(T) x = this;
		while(1)
		{
			while(x)
			{
				S.Push(x);
				x = x->LChild;
			}
			if(S.IsEmpty())
			{
				return;
			}
			x = S.Pop();
			visit(x->Data);
			x = x->RChild;
		}
	}

	template <typename VST>
	void Intrav_V2(VST& visit)		//��������汾2,��ʹ�ø���ջ,ʹ�û��ݱ�Ǻ�succ��ʵ�ֻ���
	{
		BinNodePosi(T) x = this;	
		bool backtracked = false;	//false����δ����,true�����ѻ���
		while(1)
		{
			if(!backtracked && HasLChild(*x))	//δ������������
			{
				x = x->LChild;					//����������
			}
			else								//�ѻ��ݻ���������(�л����������Ҷ)
			{
				visit(x->Data);
				if(HasRChild(*x))				//���Һ���
				{
					x = x->RChild;
					backtracked = false;		//δ����
				}
				else							//��θ������
				{
					x = x->Succ();
					if(!x)						//x�������ڵ�
					{
						return;
					}

					backtracked = true;
				}//else
			}//else
		}//while
	}


	template <typename VST>
	void PostTrav(VST& visit)		//�������
	{
		SStack<BinNodePosi(T)> S;
		BinNodePosi(T) p = this;
		S.Push(p);
		while(!S.IsEmpty())
		{
			if(S.Top() != p->Parent)		//��ôtop�����ֵ�,��ô�������ֵܵ������ҳ�
			{
				p = S.Top();
				while(p)
				{
					if(HasLChild(*p))
					{
						if(HasRChild(*p))
						{
							S.Push(p->RChild);
						}
						S.Push(p->LChild);
					}
					else
					{
						S.Push(p->RChild);
					}
					p = S.Top();
				}//while(p)
				S.Pop();					//����һ����
			}//if(S.Top() != p->Parent)		
			p = S.Pop();					//��������������Top�������ֵ�,���Ǹ�,��ֱ�ӵ���
			visit(p->Data);
		}//while(S�ǿ�)
	}

	template <typename VST>
	void LevelTrav(VST& visit)		//��α���ʹ�ø�������ʵ��
	{
		SQueue <BinNodePosi(T)> Q;
		BinNodePosi(T) p = this;
		Q.Enqueue(p);
		while(!Q.IsEmpty())			//���зǿ�ʱ
		{
			p = Q.Dequeue();		//����Dequeue�Ƿ��ظ��Ƶ�ָ��ֵ,һ�����Է��ʶ��������������
			visit(p->Data);
			if(HasLChild(*p))		//��������Ҷ������
			{
				Q.Enqueue(p->LChild);
			}
			if(HasRChild(*p))
			{
				Q.Enqueue(p->RChild);
			}
		}//while
	}

	//���������
	bool operator<(BinNodePosi(T) p)
	{
		return Data < p->Data;
	}

	bool operator==(BinNodePosi(T) p)
	{
		return (Data == p->Data);
	}

};//class BinNode

template <typename T> 
class BinTree
{
protected:
	BinNodePosi(T) _root;
	int _size;				//����ģ
	virtual int UpdateHeight(BinNodePosi(T) p)
	{
		using namespace std;	//for max
		p->Height = 1+max(Stature(p->LChild),Stature(p->RChild));
		return p->Height;
	}
	void UpdateHeightAbove(BinNodePosi(T) p)
	{
		UpdateHeight(p);
		p = p->Parent;
		while(p)
		{
			int temp = p->Height;
			UpdateHeight(p);
			if(temp == p->Height)			//���Ѿ��ޱ仯,��ֹͣ����
			{
				break;
			}
			p = p->Parent;
		}
	}

public:
	
	//Ĭ�Ϲ��캯��
	BinTree()
		:_root(NULL),_size(0)
	{}
	
	virtual ~BinTree()
	{
		if(IsEmpty())
		{
			return;
		}
		SStack<BinNodePosi(T)> S;
		S.Push(_root);
		while(!S.IsEmpty())
		{
			BinNodePosi(T) p = S.Pop();
			if(HasRChild(*p))
			{
				S.Push(p->RChild);
			}
			if(HasLChild(*p))
			{
				S.Push(p->LChild);
			}
			delete p;
		}
	}

	int Size() const
	{
		return _size;
	}

	bool IsEmpty() const
	{
		return !_size;		//size��0���ǿ�,����true
	}

	BinNodePosi(T) Root() const
	{
		return _root;
	}

	BinNodePosi(T) InsertAsRoot(T const& e) 
	{
		_size = 1;
		return _root = new BinNode<T>(e);
	}

	BinNodePosi(T) InsertAsLC(BinNodePosi(T) p,T const& e)		//����ڵ���Ϊp������
	{
		_size++;
		p->InsertAsLC(e);
		UpdateHeightAbove(p->LChild);
		return p->LChild;
	}

	BinNodePosi(T) InsertAsRC(BinNodePosi(T) p,T const& e)
	{
		_size++;
		p->InsertAsRC(e);
		UpdateHeightAbove(p->RChild);
		return p->RChild;
	}

	BinNodePosi(T) AttachAsLT(BinNodePosi(T) p,BinTree<T>& t)	//t��Ϊp������������
	{
		p->LChild = t._root;
		p->LChild->Parent = p;
		_size += t._size;
		UpdateHeightAbove(p);
		//����t�Ĺ���

		t._root = NULL;
		t._size = 0;
		return p;
	}

	//��������εı���,�����ڽڵ㼶������
	template<typename VST>
	void PreTrav_Iter(VST& visit)			//�������������
	{
		_root->PreTrav(visit);				//�ӽڵ㿪ʼ
	}

	template<typename VST>
	void InTravWithStack(VST& visit)		//�������������
	{
		_root->Intrav_V1(visit);
	}

	template<typename VST>
	void InTravWithNoStack(VST& visit)		//�������������
	{
		_root->Intrav_V2(visit);
	}

	template<typename VST>
	void PostTrav_Iter(VST& visit)			//������������
	{
		_root->PostTrav(visit);
	}

	template<typename VST>
	void LevelTrav_Iter(VST& visit)			//�������α���
	{
		_root->LevelTrav(visit);
	}

	BinNodePosi(T) AttachAsRT(BinNodePosi(T) p,BinTree<T>& t)	//t��Ϊ����������
	{
		p->RChild = t._root;
		p->RChild->Parent = p;
		_size += t._size;
		UpdateHeightAbove(p);

		t._root = NULL;
		t._size = 0;
		return p;
	}

	void ShowBelowHeight(BinNodePosi(T) p)
	{
		if(!p)
		{
			return;
		}
		cout<<p->Height<<endl;
		ShowBelowHeight(p->LChild);
		ShowBelowHeight(p->RChild);
	}

	/////////////////////ϰ��////////////////////////////
	// �κ�ϰ��5-24  (�����ú�������)
	bool TestSum()
	{
		SStack<int> sumtemp;
		sumtemp.Push(0);
		return TestNode(_root,sumtemp);
	}

	bool TestNode(BinNodePosi(T) p,SStack<int>& S)		//��ÿ���ڵ���м���,�����ʱ���������ȵĺ�����ջ
	{
		int temp = S.Top();
		if(temp>p->Data)				//����������
		{
			return false;
		}
		S.Push(temp + p->Data);
		if(HasLChild(*p))
		{
			if(TestNode(p->LChild,S))	//���µݹ�,������true(˵���Ѿ�����ѹ��ջ��,���ջ)
			{
				S.Pop();
			}
			else
			{
				return false;	
			}

		}
		if(HasRChild(*p))
		{
			if(TestNode(p->RChild,S))
			{
				S.Pop();
			}
			else
			{
				return false;
			}
		}
		return true;
	}

	//5-24��д�ɵ���ģʽ
	bool TestNode_Iter()
	{
		SStack<int> Sum;				//�ۼ�ջ
		SStack<BinNodePosi(T)> S;		//�ڵ�ָ��ջ
		Sum.Push(0);					//���������ں�
		S.Push(_root);
		BinNodePosi(T) p;
		while(!S.IsEmpty())				//ָ��ջ�ǿ�
		{
			p = S.Pop();
			int temp = Sum.Pop();
			if(temp > p->Data)
			{
				return false;
			}
			if(HasRChild(*p))
			{
				S.Push(p->RChild);
				Sum.Push(temp + p->Data);
			}
			if(HasLChild(*p))
			{
				S.Push(p->LChild);
				Sum.Push(temp+p->Data);
			}
		}//while						//while��������Ȼδfalse֤��ȫ������һ��
		return true;
	}
	///////////////////ϰ��////////////////////////
};//class BinTree
#endif //BINTREE_H