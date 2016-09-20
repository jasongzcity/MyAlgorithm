/* Created on 2016-1-24 14:52:59 by LWZ. Template for QuadListNode & QuadList */
#include <iostream>

#define QUADLIST_H

#define QListNodePosi(T) QuadListNode<T>*

template <typename T>	//��TΪԪ�ص������ڵ�
class QuadListNode
{
public:					
	T Entry;
	QListNodePosi(T) succ;
	QListNodePosi(T) pred;
	QListNodePosi(T) above;
	QListNodePosi(T) below;
	//����
	QuadListNode(T t = T(),QListNodePosi(T) s = NULL,QListNodePosi(T) p = NULL,
		QListNodePosi(T) a = NULL,QListNodePosi(T) b = NULL)
		:Entry(t),succ(s),pred(p),above(a),below(b){}

	QListNodePosi(T) InsertAsSuccAbove(T const& e,QListNodePosi(T) below = NULL)	//������e���뵱ǰ�ڵ�ĺ��,��ȷ������b
	{
		QListNodePosi(T) x = new QuadListNode<T>(e,succ,this,NULL,below);
		succ->pred = x;
		succ = x;
		if(below)		//����������
		{
			below->above = x;
		}
		return x;
	}
};//QuadListNode

//��Ԫ��TΪ�ڵ���Ԫ�ص���������
template<typename T>
class QuadList
{
private:
	int _size;
	QListNodePosi(T) header;
	QListNodePosi(T) trailer;	//ͷβ�ڱ�

protected:
	void init()						//������ͷβ�ڱ��Ŀ���
	{
		header = new QuadListNode<T>();
		trailer = new QuadListNode<T>();
		header->succ = trailer;
		trailer->pred = header;
		_size = 0;
	}
	int clear()						//�������(��ͷβ�ڱ�),������ɾ���ڵ����
	{
		int count = _size;			//����
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

	//������ӿ�
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
		return !_size;		//�ռ�����true
	}
	bool Valid(QListNodePosi(T) p) const		//�жϽڵ�pλ���Ƿ�Ϸ�
	{
		return p && (p!=header) && (p!=trailer);
	}

	//��д����
	T Remove(QListNodePosi(T) p)				//ɾ��pλ�õĽڵ㲢��������Ԫ��.��������,�ڵ������������������ɾ��,��
	{											//���ڵ���������˵ֻ�����ɾ������
		p->succ->pred = p->pred;
		p->pred->succ = p->succ;				//����ͷβ�ڱ�,ǰ��ڵ�ڵ��Ȼ����,������������
		T t = p->Entry;
		_size--;
		delete p;
		return t;
	}
	QListNodePosi(T) InsertAfterAbove(T const& e,QListNodePosi(T) p,QListNodePosi(T) below = NULL)	//��Ԫ��e���뵽p�ĺ��,b������λ��
	{
		_size++;
		return p->InsertAsSuccAbove(e,below);
	}

	template<typename VST>		//�����������ӿ�
	void Traverse(VST& visit)
	{
		QListNodePosi(T) qnode = First();
		while(qnode!=trailer)
		{
			visit(qnode->Entry);		//���ʴ���
			qnode = qnode->succ;
		}
	}	
};//class QuadList