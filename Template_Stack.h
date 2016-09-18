#include "iostream.h"
#define STACK_INIT_SIZE 100
#define INCREMENT 10

template <class Type>
class CStack
{
private:
	Type *m_pBottom;		//栈底
	Type *m_pTop;			//栈顶
	int m_nStackSize;		//已分配存储空间
public:
	CStack()			//构造
	{
		m_pBottom = m_pTop = NULL;
	}
	virtual ~CStack()		//析构函数
	{
		delete [] m_pBottom;
		m_pTop = NULL;
		m_pBottom = NULL;
		cout<<"Destructor"<<endl;
	}
	bool InitStack()		//初始化，分配存储空间
	{
		m_pBottom = new Type [STACK_INIT_SIZE];
		if(!m_pBottom)													//内存分配失败
		{
			return false;
		}
		m_pTop = m_pBottom;
		m_nStackSize = STACK_INIT_SIZE;
		return true;
	}
	bool ClearStack()		//清空栈内元素//有可能错误！
	{
		while(m_pTop != m_pBottom)	//非空栈时
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

	int StackLength()		//返回栈内元素个数
	{
		return (m_pTop - m_pBottom);
	}

	bool GetTop(Type &pt)	//栈空则返回FALSE。非空，返回TURE并将栈顶元素赋给pt
	{
		if(m_pTop != m_pBottom)		//栈非空
		{
			pt = *(m_pTop - 1);
			return true;
		}
		else
		{
			return false;
		}
	}

	bool Push(Type tInsert)//元素入栈，若分配空间失败则返回FALSE，成功则返回TURE
	{
//	if((m_pTop - m_pBottom) >= m_nStackSize)		//栈满
//	{
//		m_pBottom = (Type*)realloc(m_pBottom,(m_nStackSize+INCREMENT)*sizeof(Type));	//重新分配区域
//		if(!m_pBottom)			//若分配失败
//		{
//			return false;
//		}
//		m_pTop = m_pBottom + m_nStackSize;
//		m_nStackSize += INCREMENT;
//	}//realloc如何在C++中表示????
		*m_pTop = tInsert;
		m_pTop++;
		return true;
	}
	bool Pop(Type &pt)		//元素出栈，若栈不空则将栈顶元素给pt,栈顶减一，返回TURE，否则返回FALSE
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
	bool StackTraverse(bool (*pf)(Type*))	//遍历栈，并对每个元素调用（*pf）函数，若函数执行失败，则返回FALSE
	{
		Type *pTraverse = m_pTop;			//获取栈顶指针
		if(pTraverse != m_pBottom)
		{	
			while(pTraverse != m_pBottom)
			{
				pTraverse--;
				if(!(*pf)(pTraverse))		//语法有可能错，记得改！
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


