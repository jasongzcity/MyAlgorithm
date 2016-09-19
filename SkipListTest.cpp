/* Created on 2016-1-25 21:55:30 by LWZ. For SkipList testing */

#include "SkipList.h"
#include <ObjectFunctions.h>
//#include <iostream>
using namespace std;

template<typename K,typename V>
struct VisitEntry
{
	typedef typename K T1;
	typedef typename V T2;
	virtual void operator()(Entry<T1,T2> &E)
	{
		cout<<"key:"<<E.key<<"  value:"<<E.value<<endl;
	}
};
/*  �ӿڲ���
	Put()��ȷ
	skipSearch()��ȷ
	Remove()��ȷ,һ��ʼ��������Ϊ_sizeû�г�ʼ��!�ɼ���ʼ������Ҫ�̶�!
	Get()��ȷ
	                    */

int main(void)
{
	SkipList<char,int> SkipL;
	VisitEntry<char,int> Visit;
	SkipL.Put('a','a');
	SkipL.Put('a','a');
	SkipL.Put('b','b');
	SkipL.Put('b','b');
	SkipL.Put('c','c');
	SkipL.Put('d','d');
	SkipL.Put('e','e');
	SkipL.Put('f','f');
	SkipL.Put('g','g');
	SkipL.Put('d','d');
	SkipL.TraverseByLevel(Visit);
	SkipL.Remove('f');
	SkipL.TraverseByLevel(Visit);
	int* np = SkipL.Get('a');
	cout<<*np<<endl;
	cin.get();
	return 0;
}
