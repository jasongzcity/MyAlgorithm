/* **********************************************************************
 * Created on 2016-1-7 10:24:24 by LWZ.
 * Header file for Binary Search Tree
 * contains template for BST
 * *********************************************************************/

#define BST_H

#ifndef ENTRY_H
#include "Entry.h"		//包含词条
#endif
#ifndef BINTREE_H
#include "BinTree.h"
#endif

template<typename T>
class BST : public BinTree<T>				//只留少量对外接口其他是内部方法
{
protected:
	BinNodePosi(T) _hot;			//命中节点的父亲,后续算法中常用

	//"34"联接三个节点四个叶节点
	BinNodePosi(T) connect34(BinNodePosi(T) a,BinNodePosi(T) b,BinNodePosi(T) c,BinNodePosi(T) T0,BinNodePosi(T) T1,
		BinNodePosi(T) T2,BinNodePosi(T) T3)							//忽略细节,直接构造3顶点4子树,不再分具体情况讨论
	{																	//时间复杂度是常数!a.b.c一定不是NULL!
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
		//UpdateHeightAbove(b);	//这个应该留给更上一层调用者使用
		return b;
	}
	
	//旋转
	//利用connect34方法来实现旋转
	BinNodePosi(T) rotateAt(BinNodePosi(T) v)		//留意:此时v是非空孙辈结点.单次旋转情况 返回3+4连接方法的根节点
	{																			//根据相对位置v.p.g分四种情况 zig - 顺时针.zag - 逆时针
		BinNodePosi(T) p = v->Parent;						//结合conncet34整个过程复杂度依然是常数级别的
		BinNodePosi(T) g = p->Parent;
		if(IsLChild(*p))	//p是左孩子
		{
			if(IsLChild(*v))		//p顺时针旋转
			{
				p->Parent = g->Parent;		//连接父节点
				return connect34(v,p,g,v->LChild,v->RChild,p->RChild,g->RChild);
			}
			else					//p逆时针旋转
			{
				v->Parent = g->Parent;
				return connect34(p,v,g,p->LChild,v->LChild,v->RChild,g->RChild);
			}
		}
		else						//p是右孩子
		{
			if(IsLChild(*v))		//p顺时针旋转
			{
				v->Parent = g->Parent;
				return connect34(g,v,p,g->LChild,v->LChild,v->RChild,p->RChild);
			}
			else					//p逆时针旋转
			{
				p->Parent = g->Parent;
				return connect34(g,p,v,g->LChild,p->LChild,v->LChild,v->RChild);
			}
		}
	}


	BinNodePosi(T)& SearchIn(BinNodePosi(T)& x,T const& e,BinNodePosi(T)& hot)	//内部方法,用于递归搜索元素e 为何返回引用??
	{																			//hot记录的是命中节点的父亲
		if(!x || (x->Data == e))		//递归基,若无该元素e对应节点则返回NULL	//留意,这里返回的是引用,可以直接在上层修改左右孩子相当于
		{
			return x;
		}
		//准备向下递归
		hot = x;					//记录父亲	
		BinNodePosi(T)& v = (x->Data > e)? x->LChild:x->RChild;		//左子树或右子树		
		return SearchIn(v,e,hot);
	}

	BinNodePosi(T) RemoveAt(BinNodePosi(T)& x,BinNodePosi(T)& hot)	//hot记录删除节点的父亲,x记录删除节点的继任者地址
	{
		//有Swap()模板可直接使用
		BinNodePosi(T) w = x;		//删除节点的地址
		BinNodePosi(T) succ = NULL;	//记录删除后的节点的继任者
		if(!HasLChild(*x))	//若无左孩子或无孩子
		{
			succ = x->RChild;	//指向右孩子
			if(IsRoot(*x))
			{
				x = _root = succ;
			}
			else
			{
				//绕开删除节点
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
			succ = x = x->LChild;	//指向左孩子
			if(IsRoot(*x))
			{
				x = _root = succ;
			}
			else
			{
				//绕开删除节点
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
		else						//左右孩子都有
		{
			w = w->Succ();
			Swap(w->Data,x->Data);
			hot = w->Parent;		//记录父亲
			//(temp == x)? temp->RChild:temp->LChild = succ = w->RChild;
			succ = w->RChild;		//有可能无
			if(hot == x)
			{
				hot->RChild = succ;
			}
			else
			{
				hot->LChild = succ;
			}
		}
		if(succ)		//若接替者存在
		{
			succ->Parent = hot;		//绕开删除顶点
		}
		delete w;
		return succ;
	}

public:
	//虚方法,留待子类改写

	//全树搜索元素e
	virtual BinNodePosi(T)& Search(T const& e)
	{
		return SearchIn(_root,e,_hot = NULL);
	}
	//全树删除关键码e
	virtual bool Remove(T const& e)
	{
		BinNodePosi(T)& x = Search(e);
		if(!x)
		{
			return false;	//若x是空则返回false,删除失败
		}
		RemoveAt(x,_hot);
		_size--;
		UpdateHeightAbove(_hot);
		return true;
	}
	//在合适的位置插入关键码e
	virtual BinNodePosi(T) Insert(T const& e)
	{
		BinNodePosi(T)& x = Search(e);		//找到插入点
		if(x)			//若该位置已有节点
		{
			return x;	//则直接返回
		}
		//x = new BinNode<T>(e,_hot);		//若有空位则插入顶点并设置父节点为_hot(在搜索中已经记录好了)
		//(e<_hot->Data)? _hot->LChild:_hot->RChild  = x;			//从父亲节点处

		//插入操作采用BinTree中已经定义好的!
		if(_hot == NULL)		//这时插入根
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
		//_size++;					//这里无序更新!在基类BinTree中已经有更新规模和高度的操作
		//UpdateHeightAbove(_hot);
		return x;
	}

	BinNodePosi(T) TallerChild(BinNodePosi(T) x)		//不用快捷方式,用查询方法的方式实现
	{													//返回更高的孩子节点的指针,若等高,则返回于x同边的节点
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
			else										//两边等高
			{
				if(IsLChild(*x))
				{
					return x->LChild;
				}
				else
				{
					return x->RChild;
				}
			}//else(树等高)
		}//else(右树高不小于左树)
	}//函数尾

};//class BST