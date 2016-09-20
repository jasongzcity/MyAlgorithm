/* Created on 2016-1-24 14:52:59 by LWZ. Template for QuadListNode & QuadList */
#include <iostream>

#define QUADLIST_H

#define QListNodePosi(T) QuadListNode<T>*

template <typename T>	//以T为元素的四联节点
class QuadListNode
{
public:					
	T Entry;
	QListNodePosi(T) succ;
	QListNodePosi(T) pred;
	QListNodePosi(T) above;
	QListNodePosi(T) below;
	//构造
	QuadListNode(T t = T(),QListNodePosi(T) s = NULL,QListNodePosi(T) p = NULL,
		QListNodePosi(T) a = NULL,QListNodePosi(T) b = NULL)
		:Entry(t),succ(s),pred(p),above(a),below(b){}

	QListNodePosi(T) InsertAsSuccAbove(T const& e,QListNodePosi(T) below = NULL)	//将词条e插入当前节点的后继,并确定下邻b
	{
		QListNodePosi(T) x = new QuadListNode<T>(e,succ,this,NULL,below);
		succ->pred = x;
		succ = x;
		if(below)		//下邻若存在
		{
			below->above = x;
		}
		return x;
	}
};//QuadListNode

//以元素T为节点中元素的四联链表
template<typename T>
class QuadList
{
private:
	int _size;
	QListNodePosi(T) header;
	QListNodePosi(T) trailer;	//头尾哨兵

protected:
	void init()						//创建带头尾哨兵的空链
	{
		header = new QuadListNode<T>();
		trailer = new QuadListNode<T>();
		header->succ = trailer;
		trailer->pred = header;
		_size = 0;
	}
	int clear()						//清空链表(除头尾哨兵),并返回删除节点个数
	{
		int count = _size;			//备份
		QListNodePosi(T) del = header->succ;
		QListNodePosi(T) tmp = del;
		while(del != trailer)
		{
			tmp = del->succ;
			delete del;
			del = tmp;
		}
		_size = 0;
		header->succ = trailer;
		trailer->pred = header;
		return count;
	}
public:
	QuadList()
	{
		init();
	}
	virtual ~QuadList()
	{
		clear();
		delete header;
		delete trailer;
	}

	//访问类接口
	int Size() const
	{
		return _size;
	}
	QListNodePosi(T) First() const
	{
		return header->succ;
	}
	QListNodePosi(T) Last() const
	{
		trailer->pred;
	}
	bool IsEmpty() const
	{
		return !_size;		//空即返回true
	}
	bool Valid(QListNodePosi(T) p) const		//判断节点p位置是否合法
	{
		return p && (p!=header) && (p!=trailer);
	}

	//可写操作
	T Remove(QListNodePosi(T) p)				//删除p位置的节点并返回其中元素.四联表中,节点总是至塔顶而下逐个删除,故
	{											//对于单层链表来说只需层内删除即可
		p->succ->pred = p->pred;
		p->pred->succ = p->succ;				//因有头尾哨兵,前后节点节点必然存在,并将它们相连
		T t = p->Entry;
		_size--;
		delete p;
		return t;
	}
	QListNodePosi(T) InsertAfterAbove(T const& e,QListNodePosi(T) p,QListNodePosi(T) below = NULL)	//将元素e插入到p的后继,b的上邻位置
	{
		_size++;
		return p->InsertAsSuccAbove(e,below);
	}

	template<typename VST>		//操作器遍历接口
	void Traverse(VST& visit)
	{
		QListNodePosi(T) qnode = First();
		while(qnode!=trailer)
		{
			visit(qnode->Entry);		//访问词条
			qnode = qnode->succ;
		}
	}	
};//class QuadList