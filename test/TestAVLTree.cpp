/**
* TestAVLTree.cpp
* Tests for AVLTree.h
* Created on: 2016/10/12
* Author:lwz
**/

#include "Test_Algorithm.h"

using namespace MyAlgorithm;
//recursive method for preorder traversal in this test.
template<typename VST,typename T> void PreorderTraverse(VST& visit, AVLTree<T>& avl, BinNodePosi(T) p)
{
    if(p==NULL)
        return;
    visit(p->Data);
    PreorderTraverse(visit,avl,p->LChild);
    PreorderTraverse(visit,avl,p->RChild);
}

void TestAVLTree()
{
    using namespace std;
    using namespace MyAlgorithm;
    cout<<"=======test for AVL Tree begins========"<<endl;
    SkipLines(3);
    cout<<"Insert 100 random number and then 50 of them"<<endl;
    cout<<"then print this AVL Tree's inorder and preorder travesal to see if sorted and balanced"<<endl;
    AVLTree<int> avl;
    PrepareRandom();
    MyVector<int> vec;
    for(int i=0;i<100;i++)
    {
        int r = rand();
        avl.Insert(r);
        vec.Insert(r);
    }
    for(int i=0;i<100;i++)
    {
        if(i%2==0)
            avl.Remove(vec[i]);
    }

    SkipLines(1);

    ShowAll<int> show_int;
    cout<<"the inorder traversal:"<<endl;
    avl.Traverse(show_int);
    SkipLines(1);
    cout<<"the preorder traversal:"<<endl;
    PreorderTraverse(show_int,avl,avl.Root());

    SkipLines(3);
    cout<<"========test for AVL Tree ends======="<<endl;
}
