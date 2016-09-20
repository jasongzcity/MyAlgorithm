/* Created at 2015-12-9 22:32:23 Authot : LWZ */
#ifndef BINTREE_H
#define BINTREE_H

#include "ASonStack.h"
#include "DerivedQueue.h"
#include "ObjectFunctions.h"

#include <iostream>
using namespace std;

#define BinNodePosi(T) BinNode<T>*
typedef enum {RB_RED,RB_BLACK} RBColor;	//节点颜色,往后红黑树有用

///////////////////////////////快捷方式//////////////////////////////////
//注意:所有快捷方式都是以类对象形式,而非类对象指针的形式呈现(除了Stature)
#define IsRoot(x) (!(x).Parent)
#define IsLChild(x) (!IsRoot(x) && ( &(x) == (x).Parent->LChild ))	//判断x是否左孩子
#define IsRChild(x) (!IsRoot(x) && ( &(x) == (x).Parent->RChild ))
#define HasParent(x) ((x).Parent)
#define HasLChild(x) ((x).LChild)
#define HasRChild(x) ((x).RChild)
#define HasChild(x) ((x).RChild || (x).LChild)
#define HasBothChild(x) ((x).LChild && (x).RChild)
#define IsLeaf(x) (!HasChild(x))
#define Stature(x) ((x)?(x)->Height:-1)
///////////亲戚关系节点//////////
#define Sibling(x) (IsLChild(x)? (x).Parent->RChild: (x).Parent->LChild)	//返回x的兄弟节点的指针
#define Uncle(x) Sibling(((x).Parent))										//返回叔叔的指针
#define FromParentTo(x) (IsLChild(x)? (x).Parent->LChild:(x).Parent->RChild)//返回父节点的引用

/////////////////////////////////////////////////////////////////////////
template <typename T>
class BinNode
{

	//方便考虑全部设定为公有
public:
	T Data;
	BinNodePosi(T) Parent;	//父节点
	BinNodePosi(T) LChild;	//左孩子
	BinNodePosi(T) RChild;	//右孩子
	int Height;				//节点高度,也是当前节点作为根节点的子树的高度.叶节点高度0,空树高度-1
	int Npl;				//左式堆
	RBColor Color;

	//构造函数
	BinNode()
		:Parent(NULL),LChild(NULL),RChild(NULL),Height(0),Npl(1),Color(RB_RED)
	{}
	BinNode(T e,BinNodePosi(T) p = NULL,BinNodePosi(T) l = NULL,BinNodePosi(T) r = NULL,int h = 0,int n = 1,RBColor c = RB_RED)
		:Data(e),Parent(p),LChild(l),RChild(r),Height(h),Npl(n),Color(c)		//可能需要重载运算符=
	{}
	
	virtual ~BinNode()
	{}

	//操作接口
	BinNodePosi(T) InsertAsLC(T const& e)		//将e作为左当前节点的左孩子插入(调用者需清楚当前节点无左孩子)
	{
		LChild = new BinNode(e,this);	//其他性质晚点补充

		return LChild;
	}

	BinNodePosi(T) InsertAsRC(T const& e)		//将e作为右孩子插入
	{
		RChild = new BinNode(e,this);
		return RChild;
	}

	int Size() const		//返回该节点子树的规模
	{
		CountElement<T> C;
		PreTrav(C);
		return C.count;		//返回个数
	}

	BinNodePosi(T) Succ()		//返回该节点的直接后继(通过遍历每个节点都有后继)这是中序遍历的后继顺序
	{
		BinNodePosi(T) p = this;
		if(RChild)				//若有右孩子
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

	//习题5-14
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
				return p->Parent;			//p此时作为右子树的根
			}
			else
			{
				return NULL;				//全树中序遍历的头
			}
		}//if
	}



	//通过操作器遍历(全部使用迭代版)
	template <typename VST>
	void PreTrav(VST& visit)		//先序遍历
	{
		SStack<BinNodePosi(T)> S;
		BinNodePosi(T) x = this;	//从当前节点开始遍历
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
	void InTrav(VST& visit)			//中序遍历
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
	void Intrav_V1(VST& visit)				//版本1,运用辅助栈
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
	void Intrav_V2(VST& visit)		//中序遍历版本2,不使用辅助栈,使用回溯标记和succ来实现回溯
	{
		BinNodePosi(T) x = this;	
		bool backtracked = false;	//false代表未回溯,true代表已回溯
		while(1)
		{
			if(!backtracked && HasLChild(*x))	//未回溯且有左孩子
			{
				x = x->LChild;					//深入左子树
			}
			else								//已回溯或者无左孩子(中或左树左侧树叶)
			{
				visit(x->Data);
				if(HasRChild(*x))				//有右孩子
				{
					x = x->RChild;
					backtracked = false;		//未回溯
				}
				else							//这段负责回溯
				{
					x = x->Succ();
					if(!x)						//x处于最后节点
					{
						return;
					}

					backtracked = true;
				}//else
			}//else
		}//while
	}


	template <typename VST>
	void PostTrav(VST& visit)		//后序遍历
	{
		SStack<BinNodePosi(T)> S;
		BinNodePosi(T) p = this;
		S.Push(p);
		while(!S.IsEmpty())
		{
			if(S.Top() != p->Parent)		//那么top是右兄弟,那么将该右兄弟的最深找出
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
				S.Pop();					//弹出一个空
			}//if(S.Top() != p->Parent)		
			p = S.Pop();					//这里隐含义是若Top不是右兄弟,则是父,则直接弹出
			visit(p->Data);
		}//while(S非空)
	}

	template <typename VST>
	void LevelTrav(VST& visit)		//层次遍历使用辅助队列实现
	{
		SQueue <BinNodePosi(T)> Q;
		BinNodePosi(T) p = this;
		Q.Enqueue(p);
		while(!Q.IsEmpty())			//队列非空时
		{
			p = Q.Dequeue();		//这里Dequeue是返回复制的指针值,一样可以访问二叉树本身的数据
			visit(p->Data);
			if(HasLChild(*p))		//按先左后右队列入队
			{
				Q.Enqueue(p->LChild);
			}
			if(HasRChild(*p))
			{
				Q.Enqueue(p->RChild);
			}
		}//while
	}

	//运算符重载
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
	int _size;				//树规模
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
			if(temp == p->Height)			//若已经无变化,则停止更新
			{
				break;
			}
			p = p->Parent;
		}
	}

public:
	
	//默认构造函数
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
		return !_size;		//size是0则是空,返回true
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

	BinNodePosi(T) InsertAsLC(BinNodePosi(T) p,T const& e)		//插入节点作为p的左孩子
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

	BinNodePosi(T) AttachAsLT(BinNodePosi(T) p,BinTree<T>& t)	//t作为p的左子树插入
	{
		p->LChild = t._root;
		p->LChild->Parent = p;
		_size += t._size;
		UpdateHeightAbove(p);
		//废弃t的过程

		t._root = NULL;
		t._size = 0;
		return p;
	}

	//二叉树层次的遍历,建立在节点级遍历上
	template<typename VST>
	void PreTrav_Iter(VST& visit)			//迭代版先序遍历
	{
		_root->PreTrav(visit);				//从节点开始
	}

	template<typename VST>
	void InTravWithStack(VST& visit)		//迭代版中序遍历
	{
		_root->Intrav_V1(visit);
	}

	template<typename VST>
	void InTravWithNoStack(VST& visit)		//迭代版先序遍历
	{
		_root->Intrav_V2(visit);
	}

	template<typename VST>
	void PostTrav_Iter(VST& visit)			//迭代版后序遍历
	{
		_root->PostTrav(visit);
	}

	template<typename VST>
	void LevelTrav_Iter(VST& visit)			//迭代版层次遍历
	{
		_root->LevelTrav(visit);
	}

	BinNodePosi(T) AttachAsRT(BinNodePosi(T) p,BinTree<T>& t)	//t作为右子树插入
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

	/////////////////////习题////////////////////////////
	// 课后习题5-24  (可能用后序会更好)
	bool TestSum()
	{
		SStack<int> sumtemp;
		sumtemp.Push(0);
		return TestNode(_root,sumtemp);
	}

	bool TestNode(BinNodePosi(T) p,SStack<int>& S)		//对每个节点进行检验,假设此时所有真祖先的和已入栈
	{
		int temp = S.Top();
		if(temp>p->Data)				//不符合条件
		{
			return false;
		}
		S.Push(temp + p->Data);
		if(HasLChild(*p))
		{
			if(TestNode(p->LChild,S))	//向下递归,若返回true(说明已经将和压入栈中,需出栈)
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

	//5-24改写成迭代模式
	bool TestNode_Iter()
	{
		SStack<int> Sum;				//累加栈
		SStack<BinNodePosi(T)> S;		//节点指针栈
		Sum.Push(0);					//根的真祖宗和
		S.Push(_root);
		BinNodePosi(T) p;
		while(!S.IsEmpty())				//指针栈非空
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
		}//while						//while结束后仍然未false证明全部检查过一遍
		return true;
	}
	///////////////////习题////////////////////////
};//class BinTree
#endif //BINTREE_H