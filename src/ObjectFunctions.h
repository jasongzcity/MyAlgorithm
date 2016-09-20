/* Created on 2015-12-8 22:08:44 Author:LWZ  */

#ifndef OBJECTFUNCTION_H
#define OBJECTFUNCTION_H


#include <iostream>
using namespace std;

template <typename T>
class Plus1
{
public:
	virtual void operator()(T &e)
	{
		e++;		//ȷ��Ԫ���������Լ������
	}
};

template <typename T>
void Swap(T &e1,T &e2)
{
	T a = e1;
	e1 = e2;
	e2 = a;
}

template <typename T>
struct ShowAll
{
public:
	virtual void operator()(T const& e)
	{
		cout<<e<<endl;		//ȷ���Ѿ�����<<��cout��
	}
};

template <typename T>
class CountElement
{
public:
	int count;
	//���캯����ʼ��count
	CountElement()
	{
		count = 0;
	}
	virtual void operator()(T const& e)
	{
		count++;
	}
};//class CountElement

#endif //OBJECTFUNCTION_H