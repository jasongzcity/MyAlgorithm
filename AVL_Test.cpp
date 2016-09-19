#include "AVLTree.h"
#ifndef ENTRY_H
#include "Entry.h"
#endif
//#include "ObjectFunctions.h"

/**************AVL&BST 接口测试***************

 * TallerChild()正确 Search() SearchIn()正确
 * rotateAt() connect34() 正确 Insert()正确
 * Remove()正确
*********************************************/

int main(void)
{
	AVL<int> A;
	using namespace std;
	BinNode<int>* pNode = A.Insert(10);
	A.Insert(12);
	A.Insert(2);
	A.Insert(72);
	A.Insert(111);
	A.Insert(112);
	A.Insert(71);
	A.Insert(77);
	A.Insert(88);
	A.Insert(133);
	A.Insert(144);
	A.Insert(258);
	A.Insert(777);
	A.Insert(1000);

	ShowAll<int> ShowInt;

	A.LevelTrav_Iter(ShowInt);

	cout<<endl;

	cout<<A.Size()<<endl;
	cout<<A.Root()->Height<<endl;
	cout<<A.Root()->Data<<endl;
	cout<<endl;

	

	A.InTravWithStack(ShowInt);
	//A.LevelTrav_Iter(ShowInt);
	cout<<endl;

	A.Remove(72);
	A.Remove(2);
	//
	A.Remove(88);
	//A.InTravWithStack(ShowInt);

	//cout<<endl;
	//cout<<"this should be 71:";
	//cout<<A.Root()->LChild->RChild->Data<<endl;
	//cout<<"this should be 88:";
	//cout<<A.Root()->RChild->LChild->Data<<endl;
	//cout<<"this should be Height 2:";
	//cout<<A.Root()->Height<<endl;
	//cout<<A.Size()<<endl;
	A.LevelTrav_Iter(ShowInt);


	cout<<endl;

	cout<<A.Size()<<endl;
	cout<<A.Root()->Height<<endl;
	cout<<A.Root()->Data<<endl;


	cin.get();
	return 0;
}