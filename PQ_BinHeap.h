/* Created on 2016-2-4 09:42:45 BY LWZ. Template for complete Binary Heap */
#ifndef PQ_BINHEAP_H
#define PQ_BINHEAP_H

#include "PQInterface.h"
#include <ADT_Vector.h>
#include <ObjectFunctions.h>
#include <iostream>

/////////////////////////Shortcut////////////////////////
#define InHeap(i,n)		(((i)>(-1)) && ((i)<n))		//i�Ƿ��ڶ���
#define Parent(i)			(((i)-1)>>1)				//i�ĸ��ڵ����
#define LChild(i)			(((i)<<1)+1)				//i�����Һ���
#define RChild(i)			(LChild(i)+1)
#define RChildValid(i,n)	InHeap(RChild(i),n)			//i�����Һ����Ƿ�Ϸ�
#define LChildValid(i,n)	InHeap(LChild(i),n)	
#define ParentValid(i)		((i)>0)					//�����Ǹ��ڵ���򶼺Ϸ�
#define LastInternal(n)		(Parent(n-1))				//n��ʾ�ѵù�ģ
#define Prior(buff,i,j)		((buff[i])<(buff[j])?j:i)		//�ⲽǰ��Ҫ�������������ʾ���ȼ�.ͬʱ,�ò��������ȼ��ϸߵ�Ԫ�ص���
												//��Ҫ�Ż�,�ɽ�������"����"��Ԫ�ذ���i��λ��,��ô���������ʱ���ص���i
////////////////////////ShortcutEnd//////////////////////

template <typename T>
class PQ_BinHeap : public PQ<T>							//����һ����Ĭ�����ȸߵĸ��ӽ��ѵø���
{														//�����޸����ڿ�ݷ�ʽ���޸�Prior����
private:
	int _size;		//��ģ
	SVector<T> Buff;//������������,��ֱ����[]����
protected:
	Rank ProperParent(Rank i)		//�ҵ�i�������������������ȼ�����ߵ���
	{
		Rank temp = i;
		if(LChildValid(i,_size))			//���ж��������Ƿ�Ϸ�,�����������Ϸ���ô������Ҳ�ز��Ϸ�
		{
			temp = (Prior(Buff,i,LChild(i)) == i)?i:LChild(i);
			if(RChildValid(i,_size))
			{
				temp = (Prior(Buff,temp,RChild(i)) == temp)?temp:RChild(i);
			}
		}
		return temp;
	}
	Rank PercolateUp(Rank i)		//����Ϊi��λ�ÿ�ʼ����͸
	{								//�˷�������Ҫ����Swap�������θ�ֵ,Ч�ʸ���
		T temp = Buff[i];			//��temp�ݴ�Ҫ������Ԫ��					
		while(i)					//i=0Ҳû��Ҫ����
		{
			if(temp>Buff[Parent(i)])	//i���ȼ��ϸ�,����
			{
				Buff[i] = Buff[Parent(i)];	//��Ԫ�ػ�����,�������ż���temp��������,��Ϊ�п��ܻ�Ҫ����
				i = Parent(i);
			}
			else					//iλ�����ȼ�С�����,���ؼ���������
			{
				break;
			}
		}
		Buff[i] = temp;				//�貹�����һ�θ�ֵ
		return i;
	}
	Rank PercolateDown(Rank i)		//����i��Ԫ�ؿ�ʼ������͸
	{								//����������ܱ�֤ÿ��ֻ��ֵһ��
		T temp = Buff[i];			//�ݴ�Ԫ��		
		while((RChildValid(i,_size)&&(temp<Buff[RChild(i)]))||(LChildValid(i,_size)&&(temp<Buff[LChild(i)])))	//���к��Ӵ�����
		{																										//���ȼ���temp��ʱ,�ͱ���������͸
			if(RChildValid(i,_size) && (Prior(Buff,LChild(i),RChild(i)) == RChild(i)))	//�����Һ��Ӵ������Һ������ȼ�����ʱ
			{
				Buff[i] = Buff[RChild(i)];
				i = RChild(i);
			}
			else
			{
				Buff[i] = Buff[LChild(i)];
				i = LChild(i);
			}
		}
		Buff[i] = temp;				//����һ�θ�ֵ
		return i;
	}
	void Heapify()					//�ѵĿ��ٹ���
	{								//O(n)ʱ�临�Ӷ�
		for(Rank i=LastInternal(_size);i>-1;i--)
		{
			PercolateDown(i);		//�������ϱ����ڵ�,ÿ����������͸
		}
	}
public:
	PQ_BinHeap(){}						//Ĭ�Ϲ��췽��(�������Զ�����)
	PQ_BinHeap(const T* Array,int size)		//����һ������(ͬʱ�ƶ�Ԫ�ظ���),���������
	{
		_size = size;
		Buff = SVector<T>(Array,size);		//ͨ����������������˳�ʼ��...���ǲ���....
		Heapify();
	}
	PQ_BinHeap(const SVector<T>& vec)		//��ȡ����,��ͨ���������Ʒ���
	{
		_size = vec.Num();
		Buff = SVector<T>(vec);				//���������ĸ��ƹ��췽��
		Heapify();
	}
	virtual ~PQ_BinHeap(){}
	virtual void Insert(const T& e)				//����Ԫ��T
	{
		Buff.Insert(e);
		_size++;
		//Buff[_size++] = e;
		PercolateUp(_size-1);
	}
	virtual int Size() const 
	{
		return _size;
	}
	virtual T DeleteMax()
	{
		T t = Buff[0];
		Buff[0] = Buff[--_size];
		Buff.Remove(_size);			//�����õ�һλ��������ɾ��,�������һλ,Ч�ʸ�
		PercolateDown(0);
		return t;
	}
	virtual T GetMax()
	{
		return Buff[0];
	}
};

#endif //PQ_BINHEAP_H