/**
* TestAVLTree.cpp
* Tests for AVLTree.h
* Created on: 2016/10/12
* Author:lwz
**/

#include "stdafx.h"


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
    cout<<"Insert 1 5 9 99 88 664 778 55 32 34 65 78 45 46 15 2 and then delete 78 &15"<<endl;
    cout<<"then print this AVL Tree's inorder and preorder travesal to see if sorted and balanced"<<endl;
    AVLTree<int> avl;
    avl.Insert(1);
    avl.Insert(5);
    avl.Insert(9);
    avl.Insert(99);
    avl.Insert(88);
    avl.Insert(664);
    avl.Insert(778);
    avl.Insert(55);
    avl.Insert(32);
    avl.Insert(34);
    avl.Insert(65);
    avl.Insert(78);
    avl.Insert(45);
    avl.Insert(46);
    avl.Insert(15);
    avl.Insert(2);
    avl.Remove(78);
    avl.Remove(15);
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
