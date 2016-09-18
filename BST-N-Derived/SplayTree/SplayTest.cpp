/* Created on 2016-1-13 10:45:46 by LWZ*/

#include "SplayTree.h"

/**********************************         
 *			  接口测试
 *  splay()正确 Search()正确 
 *  Insert()正确 Remove()正确                        
 *********************************/

int main(void)
{
	SplayTree<int> S;
	ShowAll<int> ShowInt;
	BinNodePosi(int) p = S.InsertAsRoot(5);
	p = S.InsertAsLC(p,4);
	p = S.InsertAsLC(p,3);
	p = S.InsertAsLC(p,2);
	p = S.InsertAsLC(p,1);
	//S.Search(1);
	S.Remove(2);
	//S.Insert(0);
	S.LevelTrav_Iter(ShowInt);
	//S.InTravWithStack(ShowInt);
	//cout<<"this should be 3:";
	cout<<S.Root()->Data<<endl;
	cout<<S.Root()->Height<<endl;
	cout<<S.Root()->RChild->LChild->Data<<endl;
	cin.get();
	return 0;
}