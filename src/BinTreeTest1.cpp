/* Created at 2015-12-15 22:08:41 Author:LWZ */

#include "BinTree.h"
#include <iostream>

/* 
    BinTree接口测试:
	所有插入接口正确,更新高度正确,先序遍历中序遍历正确,层次遍历正确
	后序遍历正确,析构函数正确
	课后习题正确


*/

int main(int argc,char* argv[])
{
	using namespace std;
	BinTree<int> T1;				//节点类是完全开放的
	ShowAll<int> show;
	BinNodePosi(int) p = T1.InsertAsRoot(4);
	T1.InsertAsLC(p,11);
	T1.InsertAsRC(p,384);
	p = p->LChild;
	BinNodePosi(int) q = T1.InsertAsRC(p,888);
	T1.InsertAsLC(q,1998);
	T1.InsertAsRC(q,1992);


	BinTree<int> T2;							
	q = T2.InsertAsRoot(778);
	T2.InsertAsLC(q,1888);
	T2.InsertAsRC(q,1967);

	p = T1.Root();
	p = p->RChild;
	T1.AttachAsLT(p,T2);					//将树接给另一棵树后要将本树置空,否则析构函数会冲突
	q = T1.Root()->RChild;
	BinNode<int>* a = new BinNode<int>(120);
	//T1.InsertAsRC(q,33);
	//T1.InTravWithStack(show);
	//cout<<endl;
	//T1.InTravWithNoStack(show);
	//cout<<endl;
	//T1.ShowBelowHeight(p->Parent);
	//cout<<T1.Size()<<endl;
	//T1.LevelTrav_Iter(show);
	//while(q)
	//{
	//	cout<<q->Data<<endl;
	//	q = q->Pred();
	//}
	//cout<<T1.TestNode_Iter()<<endl;
	T1.PostTrav_Iter(show);



	cin.get();
}