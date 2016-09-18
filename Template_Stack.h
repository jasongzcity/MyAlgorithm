#include "iostream.h"
#define STACK_INIT_SIZE 100
#define INCREMENT 10

template <class Type>
class CStack
{
private:
	Type *m_pBottom;		//ջ��
	Type *m_pTop;			//ջ��
	int m_nStackSize;		//�ѷ���洢�ռ�
public:
	CStack()			//����
	{
		m_pBottom = m_pTop = NULL;
	}
	virtual ~CStack()		//��������
	{
		delete [] m_pBottom;
		m_pTop = NULL;
		m_pBottom = NULL;
		cout<<"Destructor"<<endl;
	}
	bool InitStack()		//��ʼ��������洢�ռ�
	{
		m_pBottom = new Type [STACK_INIT_SIZE];
		if(!m_pBottom)													//�ڴ����ʧ��
		{
			return false;
		}
		m_pTop = m_pBottom;
		m_nStackSize = STACK_INIT_SIZE;
		return true;
	}
	bool ClearStack()		//���ջ��Ԫ��//�п��ܴ���
	{
		while(m_pTop != m_pBottom)	//�ǿ�ջʱ
		{
			m_pTop--;
			delete m_pTop;
		}
		return true;
	}	
	bool IsEmpty()
	{
		if(m_pTop==m_pBottom)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	int StackLength()		//����ջ��Ԫ�ظ���
	{
		return (m_pTop - m_pBottom);
	}

	bool GetTop(Type &pt)	//ջ���򷵻�FALSE���ǿգ�����TURE����ջ��Ԫ�ظ���pt
	{
		if(m_pTop != m_pBottom)		//ջ�ǿ�
		{
			pt = *(m_pTop - 1);
			return true;
		}
		else
		{
			return false;
		}
	}

	bool Push(Type tInsert)//Ԫ����ջ��������ռ�ʧ���򷵻�FALSE���ɹ��򷵻�TURE
	{
//	if((m_pTop - m_pBottom) >= m_nStackSize)		//ջ��
//	{
//		m_pBottom = (Type*)realloc(m_pBottom,(m_nStackSize+INCREMENT)*sizeof(Type));	//���·�������
//		if(!m_pBottom)			//������ʧ��
//		{
//			return false;
//		}
//		m_pTop = m_pBottom + m_nStackSize;
//		m_nStackSize += INCREMENT;
//	}//realloc�����C++�б�ʾ????
		*m_pTop = tInsert;
		m_pTop++;
		return true;
	}
	bool Pop(Type &pt)		//Ԫ�س�ջ����ջ������ջ��Ԫ�ظ�pt,ջ����һ������TURE�����򷵻�FALSE
	{
		if(m_pTop != m_pBottom)
		{
			m_pTop--;
			pt = *m_pTop;
			return true;
		}
		else
		{
			return false;
		}
	}
	bool StackTraverse(bool (*pf)(Type*))	//����ջ������ÿ��Ԫ�ص��ã�*pf��������������ִ��ʧ�ܣ��򷵻�FALSE
	{
		Type *pTraverse = m_pTop;			//��ȡջ��ָ��
		if(pTraverse != m_pBottom)
		{	
			while(pTraverse != m_pBottom)
			{
				pTraverse--;
				if(!(*pf)(pTraverse))		//�﷨�п��ܴ��ǵøģ�
				{
					return false;
				}
			}
			return true;
		}
		else
		{
			return false;
		}
	}
};


