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

#define BTNodePosi(T) BTreeNode<T>* //B-树节点指针

//节点模板,关键码类型为T
template<typename T>
class BTreeNode
{
public:
	BTNodePosi(T) parent;			//父节点
	SVector<T> key;					//关键码向量
	
	SVector<BTNodePosi(T)> child;	//子节点地址组成向量	特别留意!更新子节点向量的Size的问题,即便其中有空!
public:
	//构造方法
	BTreeNode()
	{
		parent = NULL;
		child.Insert(NULL,0);
		//向量的默认构造函数中将元素都归0
	}
	BTreeNode(T e,BTNodePosi(T) lc = NULL,BTNodePosi(T) rc = NULL)
	{
		parent = NULL;	
		key.Insert(e,0);	//插入首元素
		child.Insert(lc,0);
		child.Insert(rc,1);
		if(lc)					
		{
			lc->parent = this;		//私有类型成员,允许操作???
		}
		if(rc)
		{
			rc->parent = this;
		}
	}

	virtual ~BTreeNode()	//析构函数
	{
		//for(int i=0;i<child.capacity();i++)		//此处会引发递归析构
		//{
		//	delete child[i];		//向下递归删除子节点
		//}
		//向下递归删除应当留给树根的析构,节点析构无操作
		//cout<<"clean node"<<endl;
	}
};//class BTreeNode


//B树模板,T为其中关键码类型
template<typename T>
class BTree
{
private:
	int _order;				//B树阶数-创建时确定,一般不容修改
protected:
	int _size;				//关键码个数
	BTNodePosi(T) _root;	//树根
	BTNodePosi(T) _hot;		//命中节点的父亲

	void OverFlow(BTNodePosi(T) v)	//上溢操作
	{
		if(v->key.Num()<_order)		//关键码个数应该小于阶数,则未上溢
		{
			return;
		}
		Rank r = _order/2;			//分割点的秩

		//这里右侧共有_order-1-r个关键码和_order-r个孩子
		BTNodePosi(T) right = new BTreeNode<T>();		//建立右边节点,此时已经有一个孩子节点了
		for(int i=0;i<_order-r-1;i++)					//右边应该有_order-1-r个关键码
		{
			right->key.Insert(v->key.Remove(r+1),i);	//删除v中原有r右侧的关键码,添加到right节点中
			right->child.Insert(v->child.Remove(r+1),i);//删除原有孩子,迁移到right节点中
		}
		right->child[_order-r-1] = v->child.Remove(r+1);//孩子比关键码多1,最后一个迁移过来,覆盖原来初始创建的空海子
		
		if(right->child[0])				//非空,则不是叶子节点,有孩子,需指向新的父亲
		{								//b树特点,有孩子则必定是一群
			for(int i=0;i<_order-r;i++)	//_order-r个孩子
			{
				right->child[i]->parent = right;
			}
		}
		BTNodePosi(T) p = v->parent;
		if(!p)				//p为空则v为根
		{
			p = _root = new BTreeNode<T>();	//创建新的根
			v->parent = p;
			p->child[0] = v;				//配置父子关系
		}
		Rank ins = 1 + p->key.BinSearch(v->key[0]);		//找到p父节点找到合适的r的插入点,因为ins位置是当前指向v的,不用变更
														//故,在ins位置插入v中秩为r的关键码是符合搜索树的标准的
		p->key.Insert(v->key.Remove(s),ins);			//删除轴点,插入到父节点中
		p->child.Insert(right,ins+1);					//插入之前建好的右节点
		right->parent = p;
		OverFlow(p);									//向上递归
	}

	void UnderFlow(BTNodePosi(T) v)	//下溢操作
	{
		if( v->child.Num() >= (_order+1)/2 )			//n/2向上取整用(n+1)/2,向下取整直接用n/2
		{
			return;
		}
		BTNodePosi(T) p = v->parent;					//找到父节点
		if(!p)						//v是根节点,且删除必定删除的是关键码的右子树
		{
			if((v->key.Num()<1) && v->child[0])		//根节点已无关键码,则必然只剩一个子节点(子节点也有可能是空的,若空则无需操作
			{										//相当于空树)
				_root = v->child[0];				//绕开v节点
				_root->parent = NULL;
				delete v;
			}
			return;
		}
		//Rank r = p->key.BinSearch(v->key[0]);		//确定p中v的位置..注意:不能查找,有可能不含关键码
		//此处开始下溢操作
		Rank r = 0;
		while(p->child[r] != v)
		{
			r++;
		}
		if(r>0 && (p->child[r-1]->key.Num() > (_order+1)/2))	//左孩子可用
		{
			BTNodePosi(T) lb = p->child[r-1];	//记录左兄弟
			v->key.Insert(lb->key.Remove(lb->key.Num()-1),0);	//将左兄弟最右关键码删除并插入到v的最左侧
			Swap(p->key[r],v->key[0]);			//再将两者交换(可由外部方法操作??,不行就添加友函数)
			v->child.Insert(lb->child.Remove(lb->child.Num()-1),0);	//孩子过继
			if(v->child[0])	//孩子非空
			{
				v->child[0]->parent = v;	//指向继父
			}
			//此时父节点及左兄弟都不会下溢,无需递归检验
		}
		else if( (r<p->child.Num()-1) && (p->child[r+1]->key.Num()>(_order+1)/2) )		//右兄弟存在,且关键码够借
		{
			BTNodePosi(T) rb = p->child[r+1];	//右兄弟
			v->key.Insert(rb->key.Remove(0),v->key.Num());	//删除右兄弟最左侧关键码,插入到v中的最右侧
			Swap(p->key[r+1],v->key[v->key.Num()-1]);		//交换位置
			v->child.Insert(rb->child.Remove(0),v->child.Num());	//将孩子过继为最右孩子;
			if(v->child[v->child.Num()-1])		//非空的话
			{
				v->child[v->child.Num()-1]->parent = v;
			}
			//此时父节点及右兄弟都不会下溢,无需递归检验
		}
		else
		{
			if(r>0)		//左兄弟存在
			{
				BTNodePosi(T) lb = p->child[r-1];
				lb->key.Insert(p->key.Remove(r-1));		//末尾插入父节点中元素
				p->child.Remove(r);						//删除p中指向v的指针
				while(!(v->key.IsEmpty()))				//当其中关键码非空时持续将关键码及孩子搬往左兄弟
				{
					lb->key.Insert(v->key.Remove(0));
					lb->child.Insert(v->child.Remove(0));
				}
				lb->child.Insert(v->child.Remove(0));	//至此v中关键码和孩子全部转移完毕
				if(lb->child[0])	//若有孩子
				{
					Rank i=0;
					while(i<lb->child.Num())
					{
						lb->child[i]->parent = lb;
						i++;
					}
				}
				delete v;			//已经将v的孩子及父亲接起可以删除v了
			}//左兄弟情况处理完毕
			else					//若无左兄弟,必有右兄弟child[r+1],与右兄弟合并
			{
				BTNodePosi(T) rb = p->child[r+1];
				v->key.Insert(p->key.Remove(r));
				p->child.Remove(r+1);			//从父亲处删除指向右兄弟的指针
				while(!(rb->key.IsEmpty()))		//当右兄弟非空时,持续搬运
				{
					v->key.Insert(rb->key.Remove(0));
					v->child.Insert(rb->child.Remove(0));
				}
				v->child.Insert(rb->child.Remove(0));
				if(v->child[0])		//孩子非空
				{
					Rank i=0;
					while(i<v->child.Num())
					{
						v->child[i++]->parent = v;	//孩子过继
					}
				}
				delete rb;
			}//右兄弟合并处理完毕
			UnderFlow(p);				//该情况下从父亲处"吸"下一关键码,需检查父亲节点的节点数,递归
		}//左右兄弟关键码都不够借情况处理完毕
	}

public:
	BTree()	//默认构造函数
	{
		_root = new BTreeNode<T>();
	}
	virtual ~BTree()
	{
		//delete _root;		//引发向下递归析构
		//递归析构要重写
		BTreeDestructor(_root);
	}
	void BTreeDestructor(BTNodePosi(T) p)
	{											//B树递归析构,递归深度O(Height(T))
		Rank i = 0;
		while(i<p->child.Num())
		{
			if(p->child[i])		//非空
			{
				delete p->child[i];
			}
			i++;
		}
		delete p;		//将子树删除完毕后,在删除节点自身
	}
	//查询操作
	int Order() const		//阶数查询
	{
		return _order;
	}
	int Size() const		//关键码个数查询
	{
		return _size;
	}
	BTNodePosi(T)& Root()	//返回树根的引用!即可以修改树根
	{
		return _root;
	}
	bool IsEmpty() const
	{
		return !_root;
	}
	BTNodePosi(T) Search(T const& e)	//查找关键码e
	{
		BTNodePosi(T) v = _root;
		_hot = NULL;
		while(v)
		{
			int r = v->key.BinSearch(e);	//这里返回最后一个小于等于e的秩(有可能是-1)
			if((r>-1) && (e==key[r]))		//若搜索成功
			{
				return v;					//返回节点地址
			}
			//else
			_hot = v;
			v = v->child[r+1];
		}
		return NULL;						//搜索失败返回NULL 同时,也是插入元素e的合理位置.用_hot记录的插入位置的父节点
	}

	bool Insert(T const& e)				//插入关键码e
	{
		BTNodePosi(T) v = Search(e);
		if(v)							//若已存在,插入失败
		{
			return false;
		}
		//else
		int r = _hot->key.BinSearch(e);	//向量中插入位置
		_hot->key.Insert(e,r+1);
		_hot->child.Insert(NULL,r+2);	//子节点向量在初始化时已经插入了一个空,故这个操作一直保持子节点与当前节点元素的"对齐"
		_size++;
		OverFlow(_hot);					//检查上溢
		return true;
	}

	bool Remove(T const& e)				//删除关键码e 注意,这里删除的必定是叶子
	{
		BTNodePosi(T) p = Search(e);
		if(!p)							//若不存在
		{
			return false;
		}
		Rank r = p->key.BinSearch(e);
		if(p->child[0])					//若存在,则非叶子,需找后继
		{
			BTNodePosi(T) next = p->child[r+1];	//向右子树找后继
			while(next->child[0])				//0是因为后继必然是右子树中最小的
			{
				next = next->child[0];			//向下找
			}
			p->key[r] = next->key[0];			//交换位置
			p = next;
			r = 0;
		}
		//经过处理后,节点p的r位置就是要删除的关键码且必为叶节点(子节点都是空,随便删)
		p->key.Remove(r);
		p->child.Remove(r+1);	//删前一位
		_size--;
		UnderFlow(p);			//p节点有可能下溢
	}
};//class BTree

