/* Created at 2015-12-9 22:33:21 by LWZ */
#include "ADT_Vector.h"
#include <iostream>

using namespace std;
template <class T> void ShowAll(T&);
/*  
	�ӿڲ���:
	������ȷ,Num��ȷ �ж�������ȷ
	Find z��ȷ  ����ȥ����ȷ
	Uniquify��ȷ BinSearch��ȷ 

*/

template <typename T>			//��������STL�й㷺�����������ȹ���
class Plus1
{
public:
	virtual void operator()(T &e)
	{
		e++;
	}
};

int main()
{
	Plus1<int> dbPlus;
	SVector<int> V(5,3);
	
	V.Insert(32111,0);
	V.Insert(9,0);
	V.Insert(9,0);
	V.Insert(88,0);
	V.Insert(213,0);
	V.Insert(9,6);
	V.Insert(123,0);
	V.Insert(244,0);
	V.Insert(666,7);

	V.Traverse(ShowAll);

	V.MergeSort(0,V.Num());
	cout<<endl;
	V.Uniquify();

	//
	V.Traverse(ShowAll);
	cout<<endl;
	
	V.Traverse(dbPlus);

	V.Traverse(ShowAll);
	cout<<endl;

	cin.get();
	return 0;
}

template <typename T>
void ShowAll(T& e)
{
	cout<<e<<endl;
}







