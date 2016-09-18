/* Created on 2016-1-12 16:26:02 by LWZ. Templates for SplayTree,Derived from BST*/

#define SPLAYTREE_H

#ifndef BST_H
#include "BST.h"
#endif

///////////////////////内联函数////////////////////////////
//使用节点指针作为模板
template<typename TreeNode> inline	
void AttachAsLChild(TreeNode p,TreeNode lc)
{
	p->LChild = lc;
	if(lc)			//左孩子存在
	{
		lc->Parent = p;
	}
}

template<typename TreeNode> inline	
void AttachAsRChild(TreeNode p,TreeNode rc)
{
	p->RChild = rc;
	if(rc)			//左孩子存在
	{
		rc->Parent = p;
	}
}
////////////////////////结束////////////////////////////



template<typename T>							//伸展树(SplayTree 模板)
class SplayTree : public BST<T>
{
protected:
	BinNodePosi(T) splay(BinNodePosi(T) v)		//保护型方法,将目标v伸展到树根位置,返回树根位置
	{
		//v是已经搜索完毕的指针
		if(!v)				//搜索到为空,则无需伸展直接返回
		{
			return v;
		}
		BinNodePosi(T) p;
		BinNodePosi(T) g;
		while((p= v->Parent)&&(g=p->Parent))	//向上溯源且保证父及祖父节点不为空
		{
			BinNodePosi(T) gg = g->Parent;		//先记录曾祖父(可能为空,即g为根),稍后要接上
			//以下分四种情况进行伸展
			if(IsLChild(*p))
			{
				if(IsLChild(*v))
				{
					//左-左 zig-zig
					//第一次顺时针旋转
					AttachAsLChild(g,p->RChild);
					AttachAsLChild(p,v->RChild);
					//第二次顺时针旋转
					AttachAsRChild(p,g);
					AttachAsRChild(v,p);
				}
				else	
				{
					//左-右 zag-zig  
					//zag
					AttachAsRChild(p,v->LChild);
					AttachAsLChild(v,p);
					//zig
					AttachAsLChild(g,v->RChild);
					AttachAsRChild(v,g);
				}
			}
			else
			{
				if(IsRChild(*v))
				{
					//右-右 zag-zag
					//zag1
					AttachAsRChild(g,p->LChild);
					AttachAsLChild(p,g);
					//zag2
					AttachAsRChild(p,v->LChild);
					AttachAsLChild(v,p);
				}
				else
				{
					//右-左 zig-zag
					//zig
					AttachAsLChild(p,v->RChild);
					AttachAsRChild(v,p);
					//zag
					AttachAsRChild(g,v->LChild);
					AttachAsLChild(v,g);
				}
			}

			//连接曾祖父
			if(!gg)		//那么此时v为根
			{
				v->Parent = NULL;
			}
			else
			{
				//(g==gg->LChild)? gg->LChild:gg->RChild = v;		//这样写是错误的,会导致gg的孩子还是g
				(g==gg->LChild)?AttachAsLChild(gg,v):AttachAsRChild(gg,v);
				v->Parent = gg;
			}
			UpdateHeight(g);		//每次旋转完都要更新高度
			UpdateHeight(p);
			UpdateHeight(v);
			
		}//while((p=v->Parent) && (g=p->Parent))

		if(p = v->Parent)			//有可能还有一个父节点,祖父已经是空
		{
			if(IsLChild(*v))		//顺时针作一次单旋
			{
				AttachAsLChild(p,v->RChild);
				AttachAsRChild(v,p);
			}
			else
			{
				AttachAsRChild(p,v->LChild);
				AttachAsLChild(v,p);
			}
			UpdateHeight(p);
			UpdateHeight(v);
		}

		v->Parent = NULL;//根无祖先
		return v;
	}

public:
	//重写插入,删除和搜索
	virtual BinNodePosi(T)& Search(const T& e)		//搜索后将节点翻上树根(搜索失败则将搜索位置的父节点翻向树根)
	{
		BinNodePosi(T) p = SearchIn(_root,e,_hot = NULL);
		_root = splay( p?p:_hot );
		return _root;			//留意,此时上层对返回值得修改即修改了_root,因返回引用
	}

	virtual BinNodePosi(T) Insert(const T& e)
	{
		if(!_root)		//空树退化情况
		{
			_size++;
			return _root = new BinNode<T>(e);
		}
		if(e == Search(e)->Data)		//若已有该元素
		{
			return _root;
		}
		_size++;
		BinNodePosi(T) p = _root;	//记录树根地址
		if(p->Data > e)
		{
			p->Parent = _root = new BinNode<T>(e,NULL,p->LChild,p);	//构造新节点作为树根
			if(p->LChild)			//p的左子树存在
			{
				p->LChild->Parent = _root;
				p->LChild = NULL;
			}
		}
		else
		{
			p->Parent = _root = new BinNode<T>(e,NULL,p,p->RChild);
			if(p->RChild)
			{
				p->RChild->Parent = _root;
				p->RChild = NULL;
			}
		}
		UpdateHeightAbove(p);
		return _root;		//那么无论原来树中是否有e,树根必然都为e且返回树根
	}

	virtual bool Remove(const T& e)	//删除元素为e的节点,并将其父亲节点升至树根-以体现数据的局部性
	{
		BinNodePosi(T) p = Search(e);	//搜索元素(若非空则到树根,空则父亲节点到树根)
		if(p->Data != e)				//若搜索失败
		{
			return false;	//则删除失败
		}
		if(!HasLChild(*p))	//若无左子树
		{
			_root = p->RChild;
			if(_root)		//若有右子树
			{	
				_root->Parent = NULL;
			}
		}
		else if(!HasRChild(*p))	//又或者无右孩子
		{
			_root = p->LChild;
			if(_root)
			{
				_root->Parent = NULL;
			}
		}
		else					//左右孩子都在
		{	
			BinNodePosi(T) lefttree = p->LChild;	//记录左子树
			p->LChild = NULL;
			lefttree->Parent = NULL;				//左子树的分离

			_root = p->RChild;						//右子树的分离
			_root->Parent = NULL;
			Search(p->Data);						//右子树中再搜索一次元素e(必失败,且升上来的节点必然无左子树)
													//因为e小于右子树中任意元素,根据SearchIn方法,只会一直"左转"直至空
			_root->LChild = lefttree;				//将左子树重新接上
			lefttree->Parent = _root;		
			p->~BinNode();//删除节点p
			delete p;
			_size--;
		}
		if(_root)	//若存在
		{
			UpdateHeight(_root);		//有可能左子树较高,右子树全树已经在Splay()中更新过高度
		}
		return true;
	}
};//class SplayTree