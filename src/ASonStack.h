/* Created at 2015-12-9 22:33:21 by LWZ */
#ifndef ASONSTACK_H
#define ASONSTACK_H

#include "ADT_Vector.h"

//�̳й�����DataBuff��ջ��,Size���������ݸ���,Capacity���ݹ�ģ
//����Ϊprotected
//DEFAULT_CAPACITY,Rank,���ɼ���ʹ��,��ȥVC��������ʽ
//��ͬ����ջ�ǽ���ջ�����в���(������),��ֻ����Ӽ�����ջ���Ĳ�������,�Լ�������еĹ������������
template<typename T>
class SStack :public SVector<T>
{
//��������������,��û��Ҫ���ջ��ָ����
private:
//	T* StackBase;       //��Ҫ��ջ��.�����������๲���˹�������???
public:

	//���캯��
	SStack(int c = DEFAULT_CAPACITY,int s = 0,T e = 0)
		:SVector(c,s,e)		//Ĭ�Ϲ��췽��
	{
//		Capacity = c;
//		DataBuff = new T[c];		//��չ��ʼ�ռ�
//		for(Size=0;Size<s;Size++)	
//		{
//			DataBuff[Size] = e;		//��������Ĭ��ֵ	����:����Ĭ��������T�����������[]
//		}
		//ע��!����ֱ�ӵ���SVector.���ĵ�ֱ֪�Ӵ����������ʱ��˳�㴴���������???������!
//		cout<<"stack"<<endl;
	}

	SStack(SStack<T> const& S,Rank lo,Rank hi)
	{
		CopyFrom(S.DataBuff,lo,hi);
	}

	SStack(T const* A,Rank lo,Rank hi)
	{
		CopyFrom(A,lo,hi);
	}

	//��������

	~SStack()
	{
//		cout<<"delete S"<<endl;
	}

	//��д����
	int Push(T const& e)	//��ջ,���ص�ǰջ��Ԫ�ظ���
	{
		Insert(e,Size);		//�൱��ĩβ����
		return Size;
	}

	T& Top()				//����ջ����Ԫ��
	{
		return DataBuff[Size-1];
	}

	T Pop()					//ֱ��ɾ��ջ��Ԫ�ز�����Ԫ��
	{
		return Remove(Size-1);
	}

};//SStack

#endif //ASONSTACK_H

