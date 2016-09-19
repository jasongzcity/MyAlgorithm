/* Created at 2015-12-15 22:08:41 Author:LWZ */

#include "BinTree.h"
#include <iostream>

/* 
    BinTree�ӿڲ���:
	���в���ӿ���ȷ,���¸߶���ȷ,����������������ȷ,��α�����ȷ
	���������ȷ,����������ȷ
	�κ�ϰ����ȷ


*/

int main(int argc,char* argv[])
{
	using namespace std;
	BinTree<int> T1;				//�ڵ�������ȫ���ŵ�
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
	T1.AttachAsLT(p,T2);					//�����Ӹ���һ������Ҫ�������ÿ�,���������������ͻ
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