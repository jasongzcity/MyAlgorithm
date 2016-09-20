//������ģ��,ע������˳���������鹹���˳���

#include "iostream.h"


template <class Type>
class CQueue
{
private:
	Type *m_pFront;
	Type *m_pRear;
	int m_nNodeCount;			//�ڵ����
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
		if(m_pRear)								//�ǿգ�����ռ�ɹ�
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	bool ClearQueue()							//��շ�����
	{
		m_pRear = m_pFront->m_pNext;
		Type *pTemp = m_pRear;
		while(pTemp)
		{
			m_pRear = pTemp->m_pNext;
			delete pTemp;
			pTemp = m_pRear;
		}
		m_pFront->m_pNext = NULL;				//ͷ���ָ���
		m_pRear = m_pFront;						//βָ���ͷָ��һ��ָ��ͷ���
		return true;	
	}

	bool IsEmpty()								//��ͷβָ�����ʱ������Ϊ�գ�����1
	{
		return (m_pFront == m_pRear);
	}

	int QueueLength()							//�����ڵ��������ȴͷ���
	{
		return m_nNodeCount;
	}

	bool GetHead(Type &record)
	{
		if(IsEmpty())
		{
			return false;
		}
		else									//�ǿ�
		{
			Type *temp = m_pFront->m_pNext;
			record = (*temp);					//����
		}
		return true;
	}

	bool EnQueue(Type *tInsert)					//�����β,����ı�����new
	{
		//Type *temp = new Type;					//�ڶ��н���������Typeʱ����ڹ��캯���н�NEXT�ÿ�
		//if(!temp)								//temp = NULL,�ռ����ʧ��
		//{
		//	cout<<"neicun";
		//	return false;
		//}
		//*temp = *tInsert;						//��������
		m_pRear->m_pNext = tInsert;				//�����βԪ��
		tInsert->m_pNext = NULL;					//��βָ��NULL�����ֶ�βָ��NULL�Ǻܹؼ��ģ�		
		m_pRear = tInsert;							//βָ��ָ���β
		m_nNodeCount++;							//�ڵ�����1
		return true;
	}

	bool DeQueue(Type &record)					//��ͷ���У���¼��record
	{
		if(IsEmpty())							//�����ڲ����ò���
		{
			return false;
		}
		Type *temp = m_pFront->m_pNext;			//ָ���ͷ
		m_pFront->m_pNext = temp->m_pNext;		//ͷ���ָ���ͷ��һ�ڵ�
		temp->m_pNext = NULL;					//�ÿ�,�ÿ�֮���ٿ���
		record = *temp;							//����
		if(temp == m_pRear)						//ɾ�������һ���ڵ�
		{
			m_pRear = m_pFront;					//һָͬ��ͷ���
		}
		delete temp;							//ɾ��
		return true;
	}

	bool QueueTraverse(bool (*pf)(Type*))		//pf�ǲ�������ΪType�ĺ���ָ�룬��������Ϊ��������
	{
		if(IsEmpty())
		{
			return false;
		}
		Type *temp = m_pFront->m_pNext;			//ָ���ͷ
		while(temp)
		{
			if(!(*pf)(temp))					//����ʧ��,�ӿ��ڴ�
			{
				return false;
			}
			temp = temp->m_pNext;
		}
		return true;
	}
};//End of CQueue



