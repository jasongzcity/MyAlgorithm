/* Created on 2015-11-22 16:21:08 ListNode template    Author:LWZ */
#ifndef ADT_LISTNODE_H
#define ADT_LISTNODE_H

#include "stdlib.h"			//for rand()

#define ListNodePosi(T) ListNode<T>* 

typedef int Rank;

template <class T> class ListNode
{
public:
	//成员数据
	T data;						//数据主体
	ListNodePosi(T) pred;		//节点前驱
	ListNodePosi(T) succ;		//节点后继
	
	//default构造函数
	ListNode()
	{}
	
	ListNode(T e,ListNodePosi(T) p = NULL, ListNodePosi(T) s = NULL)		//带参数构造函数,默认无前驱和后继
		:data(e),pred(p),succ(s){}

	ListNodePosi(T) insertAsPred(T const& e)								//将数据e作为当前节点的前驱插入
	{
		ListNodePosi(T) pTemp = new ListNode(e,pred,this);					//隐式调用构造函数,同时将后继指针指向当前节点
		pred->succ = pTemp;
		pred = pTemp;
		return pTemp;
	}

	ListNodePosi(T) insertAsSucc(T const& e)								//将数据e作为当前节点的后继插入
	{
		ListNodePosi(T) pTemp = new ListNode(e,this,succ);
		succ->pred = pTemp;
		succ = pTemp;
		return pTemp;
	}
};//类型为T的ListNode类模板，节点模板,所有为公共类，可直接读写单个节点

template <class T> class List
{
private:
	int m_nSize;					//节点个数
	ListNodePosi(T) m_pHeader;		//头哨兵
	ListNodePosi(T) m_pTrailer;		//尾哨兵

protected:
	//内部使用函数
	
	void init()			//默认初始化方法
	{
		m_pHeader = new ListNode<T>;			//在节点类之外使用节点模板，需声明类型
		m_pTrailer = new ListNode<T>;

		m_pHeader->succ = m_pTrailer;
		m_pHeader->pred = NULL;

		m_pTrailer->pred = m_pHeader;
		m_pTrailer->succ = NULL;

		m_nSize = 0;

	}


	void copyNodes(ListNodePosi(T) p,int n)				//复制自p起n项,从尾部插入,与书本不同在于，这里不包含初始化！
	{
		while(n--)
		{
			InsertAsLast(p->data);
			p = p->succ;
		}
	}


public:
	//Constrctor
	List()
	{
		init();		//default method
		//cout<<"List"<<endl;
	}

	List(List<T> const& L)	//整体复制列表L
	{
		init();
		copyNodes(L.First(),L.Size());
	}

	List(List<T> const& L,Rank r,int n)				//从列表L的第r项开始复制n项,默认L列表非空
	{
		init();										//创建头尾节点
		ListNodePosi(T) pData = L.First();
		while(r--)
		{
			pData = pData->succ;		
		}
		copyNodes(pData,n);
		m_nSize = n;
	}

	//Destructor
	virtual ~List()
	{
		Clear();
		delete m_pHeader;
		delete m_pTrailer;
	}



	//只读接口

	ListNodePosi(T) First() const			//第一个节点
	{
		return m_pHeader->succ;
	}

	ListNodePosi(T) Last() const			//最后一个节点
	{
		return m_pTrailer->pred;
	}

	
	T& operator[](Rank r) const				//循秩访问效率低，需慎用,需上层考虑不超标
	{
		if(r+1>m_nSize)						//溢出
		{
			abort();
		}

		ListNodePosi(T) pTemp = First();
		
		while(r--)					//迭代r次
		{
			pTemp = pTemp->succ;
		}
		return pTemp->data;
	}

	int Size() const
	{
		return m_nSize;
	}

	bool IsEmpty() const					//空则返回true，非空返回false
	{
		return (m_pHeader->succ == m_pTrailer);
	}

	bool IsValid() const					//合法则返回true，否则返回false
	{
		return p && (p != m_pHeader) && (p != m_pTrailer);		//p不为空且不等于头尾节点即为合法！
	}

	ListNodePosi(T) Find(T const& e,ListNodePosi(T) p,int n) const	//在节点p(可包含尾哨兵)的n个前驱查找数据为e的节点
	{																//失败则返回NULL
		while(n--)													//时间复杂度：O(n)
		{
			p = p->pred;
			if(e == p->data)
			{
				return p;
			}
		}
		return NULL;
	}

	ListNodePosi(T) Find(T const& e) const		//在全列表中查找元素e
	{
		return Find(e,m_pTrailer,m_nSize);
	}

	ListNodePosi(T) Search(T const& e,ListNodePosi(T) p,int n) const	//在有序空间查找e，成功时返回最后一个不大于e的元素
	{																	//失败时返回区间的前驱，这么做方便接口调用者下一步操作
		while(n>=0)														//如插入操作
		{
			n--;
			p = p->pred;
			if(p->data <= e)											//需重载<=运算符
			{
				break;					//跳出循环
			}
		}
		return p;						//此处有可能是区间前驱，例如m_pHeader
	}

	ListNodePosi(T) Search(T const& e)	const							//全列表有序查找
	{
		return Search(e,m_pTrailer,m_nSize);
	}

	bool Disordered() const						//判断列表是否有序(默认从小往大)
	{
		ListNodePosi(T) p = First();
		T e = p->data;
		while(p != Last())
		{
			if(e > (p->succ->data))				//当元素大于下一节点元素,假设已重载元素运算符
			{
				return false;
			}
			else
			{
				p = p->succ;
				e = p->data;
			}
		}
		return true;
	}

	ListNodePosi(T) SelectMax(ListNodePosi(T) p,int n)	const		//在p和n-1个后继中选出最大值，返回所在节点（配合选择排序）
	{																//O(n-1)
		ListNodePosi(T) max = p;			//max用于记录最大节点的地址	
		while(n>1)							//迭代n-1次
		{
			n--;
			p = p->succ;
			if(max->data < p->data)
			{
				max = p;
			}
		}
		return max;			
	}

	ListNodePosi(T) SelectMax() const
	{
		return SelectMax(First(),m_nSize);
	}


	//可写接口//留意！在列表层面的添加单个节点，需要改变size

	ListNodePosi(T) InsertAsFirst(T const& e)	//将数据插入头结点，返回头结点指针
	{
		ListNodePosi(T) pTemp = m_pHeader->insertAsSucc(e);
		m_nSize++;
		return pTemp;
	}

	ListNodePosi(T) InsertAsLast(T const& e)	//将数据插入尾节点，返回尾结点指针
	{
		ListNodePosi(T) pTemp = m_pTrailer->insertAsPred(e);
		m_nSize++;
		return pTemp;
	}

	ListNodePosi(T) InsertAsSelSucc(ListNodePosi(T) p,T const& e)	//将e作为p节点的下一节点插入
	{
		ListNodePosi(T) pTemp = p->insertAsSucc(e);
		m_nSize++;
		return pTemp;
	}

	ListNodePosi(T) InsertAsSelPred(ListNodePosi(T) p,T const& e)	//将e作为p节点的上一节点插入
	{
		ListNodePosi(T) pTemp = p->insertAsPred(e);
		m_nSize++;
		return pTemp;
	}

	T Remove(ListNodePosi(T) p)						//删除节点p并返回其中数值
	{
		T temp = p->data;							//假设可直接赋值
		p->succ->pred = p->pred;
		p->pred->succ = p->succ;
		delete p;
		m_nSize--;
		return temp;
	}

	int Clear()			//清除所有节点 (除头哨兵和尾哨兵)
	{
		int nTemp = m_nSize;
		while(m_nSize)	//Size值会随在Remove中减少直到为0
		{
			Remove(m_pHeader->succ);
		}
		return nTemp;
	}

	int Deduplicate()								//无序列表的唯一化，返回删除节点个数
	{												//O(size^2)
		if(m_nSize<2)
		{
			return 0;
		}

		int count = 0;
		ListNodePosi(T) p = First();
		Rank r = 0;
		while(p != m_pTrailer)
		{
										
			ListNodePosi(T) q = Find(p->data,p,r);	//此时秩为0，find往回找0个

			if(q)									//查找成功，有雷同
			{
				Remove(q);
				count++;							//记录删除个数，同时，秩不加，p往前一节点
			}
			else									//查找失败
			{
				r++;
			}
			p = p->succ;
		}
		return count;
	}

	int Uniquify()									//有序列表的唯一化
	{												//O(Size)
		int count = 0;
		if(m_nSize<2)
		{
			return 0;
		}
		ListNodePosi(T) p = First();
		ListNodePosi(T) q = p->succ;
		while(q != m_pTrailer)
		{
			if(p->data == q->data)
			{
				Remove(q);
				count++;
			}
			else
			{
				p = q;
			}
			q = p->succ;
		}
		return count;
	}

	void Merge(ListNodePosi(T) p,int n,List<T>& L,ListNodePosi(T) q,int m)		//将链表L中的q起m个节点归并到本链p起n个节点中
	{
		ListNodePosi(T) head = p->pred;
		while(m)			//策略:逐个移动与q比较，找到第一个比q中数据大的节点，把q插入到该节点前驱。当m=0时说明q插入完
		{
			if((n>0) && (p->data <= q->data))		//确认p还在归并序列内，且p的数据比q小（目标是找到第一个比q大的数据节点）
			{										//故p指向后继
				p = p->succ;
				if(p == q)							//退化情况：p q在同一序列内，且首尾相接（意味着p序列最大者小于等于
				{									//q序列最小者，此时归并结束
					break;
				}
				n--;
			}
			else									//此时p的数据比q的大，将q插入p之前
			{
				q = q->succ;						//本节点准备插入，准备删除，q指向下一节点
				T e =L.Remove(q->pred);				//删除节点,注意应是L.Remove，
				InsertAsSelPred(p,e);				//将元素e插入p节点之前
				m--;
			}
		}//while
		p = head->succ;				//此时p回到序列头
	}			

	//以下四个排序接口，p一定是合法的，不能是头尾哨兵
	void Sort(ListNodePosi(T) p,int n)				//自节点p起n个节点的排序（默认从小到大）
	{
		switch(rand()%3)							//随机选择一种方法,留意rand()所需头文件
		{
			case 1:
				InsertionSort(p,n);
				break;
			case 2:
				SelectionSort(p,n);
				break;
			default:
				MergeSort(p,n);
				break;
		}
	}

	void MergeSort(ListNodePosi(T) p,int n)			//p开始n项归并排序
	{												//时间复杂度：O(nlogn)
		
		if(n<2)										//递归基，只剩一个节点时
		{
			return;
		}
		int m = n>>1;
		ListNodePosi(T) q = p;
		for(int i=0;i<m;i++)						//迭代mid次,找到第mid的节点的下一个节点
		{											//即后半部分的起始节点
			q = q->succ;
		}
		ListNodePosi(T) ppred = p->pred;			//做备份
				
		MergeSort(p,m);								//一直二路切分

		ListNodePosi(T) qpred = q->pred;			//做备份

		MergeSort(q,n-m);							//直至都只剩一个节点时

		p = ppred->succ;	
		q = qpred->succ;							//疑问? "*&"这种类型存在??

		Merge(p,n,*this,q,n-m);				//切分完再归并		

	}
	
	void SelectionSort(ListNodePosi(T) p,int n)		//p开始n项选择排序 - 将前缀最大的选出，放入后缀中
	{												//时间复杂度：O(n^2)
		int temp = n;
		ListNodePosi(T) tail = p;
		ListNodePosi(T) head = p->pred;
		while(temp--)
		{
			tail = tail->succ;
		}											//确定排序区间[p,tail)不包含tail

		while(n>1)		//迭代n-1次，包括p在内共n个元素需要移动到后缀中
		{
			ListNodePosi(T) copy = SelectMax(head->succ,n);	//考察边界条件，n=2时，前缀内只剩两节点，SelectMax会返回大者
			InsertAsSelPred(tail,copy->data);		//在tail的前面插入
			Remove(copy);
			tail = tail->pred;
			n--;
		}
	}

	void InsertionSort(ListNodePosi(T) p,int n)		//p开始n项插入排序 - 将后缀的无序序列逐个放入前缀的有序序列
	{												//时间复杂度：O(n^2)，因迭代n次，每次复杂度依次为1 - n
		int count = 0;								//表示已排序列表中节点个数
		while(n--)
		{
			ListNodePosi(T) q = Search(p->data,p,count);		//返回的节点是最后一个不大于e的节点
			InsertAsSelSucc(q,p->data);							//故插入到该节点后，可实现升序
			p = p->succ;
			Remove(p->pred);									//切记这一部，否则会多出很多节点，而且没有了排序效果
			count++;
		}
	}
	
		
	//以下为遍历接口
	void Traverse(void (*visit)(T&))				//遍历接口，函数参数为函数指针,此为函数指针用法
	{
		ListNodePosi(T) p = First();
		while(p != m_pTrailer)
		{
			(*visit)(p->data);						//指针函数访问data
			p = p->succ;
		}
	}
	
	template <typename VST>							//元素类型
	void Traverse(VST& visit)						//元素对象为visit
	{
		ListNodePosi(T) p = First();
		while(p != m_pTrailer)
		{
			visit(p->data);							//此处visit为函数对象
			p = p->succ;
		}
	}
};//List模板类

#endif //ADT_LISTNODE_H












		