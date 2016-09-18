/* Created on 2015-11-22 16:21:08 ListNode template    Author:LWZ */
#ifndef ADT_LISTNODE_H
#define ADT_LISTNODE_H

#include "stdlib.h"			//for rand()

#define ListNodePosi(T) ListNode<T>* 

typedef int Rank;

template <class T> class ListNode
{
public:
	//��Ա����
	T data;						//��������
	ListNodePosi(T) pred;		//�ڵ�ǰ��
	ListNodePosi(T) succ;		//�ڵ���
	
	//default���캯��
	ListNode()
	{}
	
	ListNode(T e,ListNodePosi(T) p = NULL, ListNodePosi(T) s = NULL)		//���������캯��,Ĭ����ǰ���ͺ��
		:data(e),pred(p),succ(s){}

	ListNodePosi(T) insertAsPred(T const& e)								//������e��Ϊ��ǰ�ڵ��ǰ������
	{
		ListNodePosi(T) pTemp = new ListNode(e,pred,this);					//��ʽ���ù��캯��,ͬʱ�����ָ��ָ��ǰ�ڵ�
		pred->succ = pTemp;
		pred = pTemp;
		return pTemp;
	}

	ListNodePosi(T) insertAsSucc(T const& e)								//������e��Ϊ��ǰ�ڵ�ĺ�̲���
	{
		ListNodePosi(T) pTemp = new ListNode(e,this,succ);
		succ->pred = pTemp;
		succ = pTemp;
		return pTemp;
	}
};//����ΪT��ListNode��ģ�壬�ڵ�ģ��,����Ϊ�����࣬��ֱ�Ӷ�д�����ڵ�

template <class T> class List
{
private:
	int m_nSize;					//�ڵ����
	ListNodePosi(T) m_pHeader;		//ͷ�ڱ�
	ListNodePosi(T) m_pTrailer;		//β�ڱ�

protected:
	//�ڲ�ʹ�ú���
	
	void init()			//Ĭ�ϳ�ʼ������
	{
		m_pHeader = new ListNode<T>;			//�ڽڵ���֮��ʹ�ýڵ�ģ�壬����������
		m_pTrailer = new ListNode<T>;

		m_pHeader->succ = m_pTrailer;
		m_pHeader->pred = NULL;

		m_pTrailer->pred = m_pHeader;
		m_pTrailer->succ = NULL;

		m_nSize = 0;

	}


	void copyNodes(ListNodePosi(T) p,int n)				//������p��n��,��β������,���鱾��ͬ���ڣ����ﲻ������ʼ����
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

	List(List<T> const& L)	//���帴���б�L
	{
		init();
		copyNodes(L.First(),L.Size());
	}

	List(List<T> const& L,Rank r,int n)				//���б�L�ĵ�r�ʼ����n��,Ĭ��L�б�ǿ�
	{
		init();										//����ͷβ�ڵ�
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



	//ֻ���ӿ�

	ListNodePosi(T) First() const			//��һ���ڵ�
	{
		return m_pHeader->succ;
	}

	ListNodePosi(T) Last() const			//���һ���ڵ�
	{
		return m_pTrailer->pred;
	}

	
	T& operator[](Rank r) const				//ѭ�ȷ���Ч�ʵͣ�������,���ϲ㿼�ǲ�����
	{
		if(r+1>m_nSize)						//���
		{
			abort();
		}

		ListNodePosi(T) pTemp = First();
		
		while(r--)					//����r��
		{
			pTemp = pTemp->succ;
		}
		return pTemp->data;
	}

	int Size() const
	{
		return m_nSize;
	}

	bool IsEmpty() const					//���򷵻�true���ǿշ���false
	{
		return (m_pHeader->succ == m_pTrailer);
	}

	bool IsValid() const					//�Ϸ��򷵻�true�����򷵻�false
	{
		return p && (p != m_pHeader) && (p != m_pTrailer);		//p��Ϊ���Ҳ�����ͷβ�ڵ㼴Ϊ�Ϸ���
	}

	ListNodePosi(T) Find(T const& e,ListNodePosi(T) p,int n) const	//�ڽڵ�p(�ɰ���β�ڱ�)��n��ǰ����������Ϊe�Ľڵ�
	{																//ʧ���򷵻�NULL
		while(n--)													//ʱ�临�Ӷȣ�O(n)
		{
			p = p->pred;
			if(e == p->data)
			{
				return p;
			}
		}
		return NULL;
	}

	ListNodePosi(T) Find(T const& e) const		//��ȫ�б��в���Ԫ��e
	{
		return Find(e,m_pTrailer,m_nSize);
	}

	ListNodePosi(T) Search(T const& e,ListNodePosi(T) p,int n) const	//������ռ����e���ɹ�ʱ�������һ��������e��Ԫ��
	{																	//ʧ��ʱ���������ǰ������ô������ӿڵ�������һ������
		while(n>=0)														//��������
		{
			n--;
			p = p->pred;
			if(p->data <= e)											//������<=�����
			{
				break;					//����ѭ��
			}
		}
		return p;						//�˴��п���������ǰ��������m_pHeader
	}

	ListNodePosi(T) Search(T const& e)	const							//ȫ�б��������
	{
		return Search(e,m_pTrailer,m_nSize);
	}

	bool Disordered() const						//�ж��б��Ƿ�����(Ĭ�ϴ�С����)
	{
		ListNodePosi(T) p = First();
		T e = p->data;
		while(p != Last())
		{
			if(e > (p->succ->data))				//��Ԫ�ش�����һ�ڵ�Ԫ��,����������Ԫ�������
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

	ListNodePosi(T) SelectMax(ListNodePosi(T) p,int n)	const		//��p��n-1�������ѡ�����ֵ���������ڽڵ㣨���ѡ������
	{																//O(n-1)
		ListNodePosi(T) max = p;			//max���ڼ�¼���ڵ�ĵ�ַ	
		while(n>1)							//����n-1��
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


	//��д�ӿ�//���⣡���б�������ӵ����ڵ㣬��Ҫ�ı�size

	ListNodePosi(T) InsertAsFirst(T const& e)	//�����ݲ���ͷ��㣬����ͷ���ָ��
	{
		ListNodePosi(T) pTemp = m_pHeader->insertAsSucc(e);
		m_nSize++;
		return pTemp;
	}

	ListNodePosi(T) InsertAsLast(T const& e)	//�����ݲ���β�ڵ㣬����β���ָ��
	{
		ListNodePosi(T) pTemp = m_pTrailer->insertAsPred(e);
		m_nSize++;
		return pTemp;
	}

	ListNodePosi(T) InsertAsSelSucc(ListNodePosi(T) p,T const& e)	//��e��Ϊp�ڵ����һ�ڵ����
	{
		ListNodePosi(T) pTemp = p->insertAsSucc(e);
		m_nSize++;
		return pTemp;
	}

	ListNodePosi(T) InsertAsSelPred(ListNodePosi(T) p,T const& e)	//��e��Ϊp�ڵ����һ�ڵ����
	{
		ListNodePosi(T) pTemp = p->insertAsPred(e);
		m_nSize++;
		return pTemp;
	}

	T Remove(ListNodePosi(T) p)						//ɾ���ڵ�p������������ֵ
	{
		T temp = p->data;							//�����ֱ�Ӹ�ֵ
		p->succ->pred = p->pred;
		p->pred->succ = p->succ;
		delete p;
		m_nSize--;
		return temp;
	}

	int Clear()			//������нڵ� (��ͷ�ڱ���β�ڱ�)
	{
		int nTemp = m_nSize;
		while(m_nSize)	//Sizeֵ������Remove�м���ֱ��Ϊ0
		{
			Remove(m_pHeader->succ);
		}
		return nTemp;
	}

	int Deduplicate()								//�����б��Ψһ��������ɾ���ڵ����
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
										
			ListNodePosi(T) q = Find(p->data,p,r);	//��ʱ��Ϊ0��find������0��

			if(q)									//���ҳɹ�������ͬ
			{
				Remove(q);
				count++;							//��¼ɾ��������ͬʱ���Ȳ��ӣ�p��ǰһ�ڵ�
			}
			else									//����ʧ��
			{
				r++;
			}
			p = p->succ;
		}
		return count;
	}

	int Uniquify()									//�����б��Ψһ��
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

	void Merge(ListNodePosi(T) p,int n,List<T>& L,ListNodePosi(T) q,int m)		//������L�е�q��m���ڵ�鲢������p��n���ڵ���
	{
		ListNodePosi(T) head = p->pred;
		while(m)			//����:����ƶ���q�Ƚϣ��ҵ���һ����q�����ݴ�Ľڵ㣬��q���뵽�ýڵ�ǰ������m=0ʱ˵��q������
		{
			if((n>0) && (p->data <= q->data))		//ȷ��p���ڹ鲢�����ڣ���p�����ݱ�qС��Ŀ�����ҵ���һ����q������ݽڵ㣩
			{										//��pָ����
				p = p->succ;
				if(p == q)							//�˻������p q��ͬһ�����ڣ�����β��ӣ���ζ��p���������С�ڵ���
				{									//q������С�ߣ���ʱ�鲢����
					break;
				}
				n--;
			}
			else									//��ʱp�����ݱ�q�Ĵ󣬽�q����p֮ǰ
			{
				q = q->succ;						//���ڵ�׼�����룬׼��ɾ����qָ����һ�ڵ�
				T e =L.Remove(q->pred);				//ɾ���ڵ�,ע��Ӧ��L.Remove��
				InsertAsSelPred(p,e);				//��Ԫ��e����p�ڵ�֮ǰ
				m--;
			}
		}//while
		p = head->succ;				//��ʱp�ص�����ͷ
	}			

	//�����ĸ�����ӿڣ�pһ���ǺϷ��ģ�������ͷβ�ڱ�
	void Sort(ListNodePosi(T) p,int n)				//�Խڵ�p��n���ڵ������Ĭ�ϴ�С����
	{
		switch(rand()%3)							//���ѡ��һ�ַ���,����rand()����ͷ�ļ�
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

	void MergeSort(ListNodePosi(T) p,int n)			//p��ʼn��鲢����
	{												//ʱ�临�Ӷȣ�O(nlogn)
		
		if(n<2)										//�ݹ����ֻʣһ���ڵ�ʱ
		{
			return;
		}
		int m = n>>1;
		ListNodePosi(T) q = p;
		for(int i=0;i<m;i++)						//����mid��,�ҵ���mid�Ľڵ����һ���ڵ�
		{											//����벿�ֵ���ʼ�ڵ�
			q = q->succ;
		}
		ListNodePosi(T) ppred = p->pred;			//������
				
		MergeSort(p,m);								//һֱ��·�з�

		ListNodePosi(T) qpred = q->pred;			//������

		MergeSort(q,n-m);							//ֱ����ֻʣһ���ڵ�ʱ

		p = ppred->succ;	
		q = qpred->succ;							//����? "*&"�������ʹ���??

		Merge(p,n,*this,q,n-m);				//�з����ٹ鲢		

	}
	
	void SelectionSort(ListNodePosi(T) p,int n)		//p��ʼn��ѡ������ - ��ǰ׺����ѡ���������׺��
	{												//ʱ�临�Ӷȣ�O(n^2)
		int temp = n;
		ListNodePosi(T) tail = p;
		ListNodePosi(T) head = p->pred;
		while(temp--)
		{
			tail = tail->succ;
		}											//ȷ����������[p,tail)������tail

		while(n>1)		//����n-1�Σ�����p���ڹ�n��Ԫ����Ҫ�ƶ�����׺��
		{
			ListNodePosi(T) copy = SelectMax(head->succ,n);	//����߽�������n=2ʱ��ǰ׺��ֻʣ���ڵ㣬SelectMax�᷵�ش���
			InsertAsSelPred(tail,copy->data);		//��tail��ǰ�����
			Remove(copy);
			tail = tail->pred;
			n--;
		}
	}

	void InsertionSort(ListNodePosi(T) p,int n)		//p��ʼn��������� - ����׺�����������������ǰ׺����������
	{												//ʱ�临�Ӷȣ�O(n^2)�������n�Σ�ÿ�θ��Ӷ�����Ϊ1 - n
		int count = 0;								//��ʾ�������б��нڵ����
		while(n--)
		{
			ListNodePosi(T) q = Search(p->data,p,count);		//���صĽڵ������һ��������e�Ľڵ�
			InsertAsSelSucc(q,p->data);							//�ʲ��뵽�ýڵ�󣬿�ʵ������
			p = p->succ;
			Remove(p->pred);									//�м���һ������������ܶ�ڵ㣬����û��������Ч��
			count++;
		}
	}
	
		
	//����Ϊ�����ӿ�
	void Traverse(void (*visit)(T&))				//�����ӿڣ���������Ϊ����ָ��,��Ϊ����ָ���÷�
	{
		ListNodePosi(T) p = First();
		while(p != m_pTrailer)
		{
			(*visit)(p->data);						//ָ�뺯������data
			p = p->succ;
		}
	}
	
	template <typename VST>							//Ԫ������
	void Traverse(VST& visit)						//Ԫ�ض���Ϊvisit
	{
		ListNodePosi(T) p = First();
		while(p != m_pTrailer)
		{
			visit(p->data);							//�˴�visitΪ��������
			p = p->succ;
		}
	}
};//Listģ����

#endif //ADT_LISTNODE_H












		