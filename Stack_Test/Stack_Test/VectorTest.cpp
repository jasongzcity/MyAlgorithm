/* Created at 2015-12-9 22:33:21 by LWZ */
#include "ADT_Vector.h"
#include <iostream>

using namespace std;
template <class T> void ShowAll(T&);
/*  
	接口测试:
	遍历正确,Num正确 判断排序正确
	Find z正确  无序去重正确
	Uniquify正确 BinSearch正确 

*/

template <typename T>			//操作器在STL中广泛用作迭代器等功能
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







