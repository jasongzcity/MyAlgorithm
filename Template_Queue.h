//队列链模板,注意区分顺序链和数组构造的顺序表

#include "iostream.h"


template <class Type>
class CQueue
{
private:
	Type *m_pFront;
	Type *m_pRear;
	int m_nNodeCount;			//节点个数
public:
	CQueue()
	{
		m_nNodeCount = 0;
		cout<<"constructor check"<<endl;
	}
	~CQueue()
	{
		while(m_pFront)
		{
			m_pRear = m_pFront->m_pNext;
			delete m_pFront;
			m_pFront = m_pRear;
		}
		cout<<"destructor check"<<endl;
	}

	bool InitQueue()
	{
		m_pRear = m_pFront = new Type;
		if(m_pRear)								//非空，非配空间成功
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	bool ClearQueue()							//清空非销毁
	{
		m_pRear = m_pFront->m_pNext;
		Type *pTemp = m_pRear;
		while(pTemp)
		{
			m_pRear = pTemp->m_pNext;
			delete pTemp;
			pTemp = m_pRear;
		}
		m_pFront->m_pNext = NULL;				//头结点指向空
		m_pRear = m_pFront;						//尾指针和头指针一起指向头结点
		return true;	
	}

	bool IsEmpty()								//当头尾指针相等时，队列为空，返回1
	{
		return (m_pFront == m_pRear);
	}

	int QueueLength()							//包含节点个数，除却头结点
	{
		return m_nNodeCount;
	}

	bool GetHead(Type &record)
	{
		if(IsEmpty())
		{
			return false;
		}
		else									//非空
		{
			Type *temp = m_pFront->m_pNext;
			record = (*temp);					//拷贝
		}
		return true;
	}

	bool EnQueue(Type *tInsert)					//插入队尾,插入的必须是new
	{
		//Type *temp = new Type;					//在堆中建立，建立Type时最好在构造函数中将NEXT置空
		//if(!temp)								//temp = NULL,空间分配失败
		//{
		//	cout<<"neicun";
		//	return false;
		//}
		//*temp = *tInsert;						//拷贝数据
		m_pRear->m_pNext = tInsert;				//插入队尾元素
		tInsert->m_pNext = NULL;					//队尾指向NULL，保持队尾指向NULL是很关键的！		
		m_pRear = tInsert;							//尾指针指向队尾
		m_nNodeCount++;							//节点数加1
		return true;
	}

	bool DeQueue(Type &record)					//队头出列，记录在record
	{
		if(IsEmpty())							//方法内部调用测试
		{
			return false;
		}
		Type *temp = m_pFront->m_pNext;			//指向队头
		m_pFront->m_pNext = temp->m_pNext;		//头结点指向队头下一节点
		temp->m_pNext = NULL;					//置空,置空之后再拷贝
		record = *temp;							//拷贝
		if(temp == m_pRear)						//删除到最后一个节点
		{
			m_pRear = m_pFront;					//一同指向头结点
		}
		delete temp;							//删除
		return true;
	}

	bool QueueTraverse(bool (*pf)(Type*))		//pf是参数类型为Type的函数指针，返回类型为布尔类型
	{
		if(IsEmpty())
		{
			return false;
		}
		Type *temp = m_pFront->m_pNext;			//指向队头
		while(temp)
		{
			if(!(*pf)(temp))					//访问失败,接口在此
			{
				return false;
			}
			temp = temp->m_pNext;
		}
		return true;
	}
};//End of CQueue



