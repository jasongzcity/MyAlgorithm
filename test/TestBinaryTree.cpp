/**
* TestBinaryTree.cpp
* Test function of BinTree.h
* Author: lwz
* Created: 2016/10/11
**/

#include "stdafx.h"

void TestBinaryTree()
{
    using namespace std;
    using namespace MyAlgorithm;
    cout<<"===========Tests for binary tree begin============"<<endl;
    
    BinTree<int> tree;
    BinNodePosi(int) p_left;
    tree.InsertAsRoot(1);
    p_left = tree.InsertAsLC(tree.Root(), 3);
    p_left = tree.InsertAsLC(p_left,8);
    p_left = tree.InsertAsLC(p_left,38);
    tree.InsertAsLC(p_left,1346);
    tree.InsertAsRC(p_left,1447);

    p_left = tree.Root()->LChild;
    p_left = tree.InsertAsRC(p_left,7);
    p_left = tree.InsertAsRC(p_left,77);
    tree.InsertAsLC(p_left,1777);

    p_left = tree.InsertAsRC(tree.Root(),4);
    tree.InsertAsLC(p_left,12);
    p_left = tree.InsertAsRC(p_left,18);
    p_left = tree.InsertAsLC(p_left,132);
    tree.InsertAsLC(p_left,888);
    tree.InsertAsRC(p_left,999);

    SkipLines(3);
    cout<<"Using tree like: "<<endl;
    cout<<"                                   1"<<endl;
    cout<<"                       3                     4"<<endl;
    cout<<"                   8      7            12       18"<<endl;
    cout<<"              38            77              132"<<endl;
    cout<<"           1346 1447    1777              888 999"<<endl;

    SkipLines(2);
    ShowAll<int> show_int;

    cout<<"Inorder traversal without stack:"<<endl;
    tree.InTravWithNoStack(show_int);
    SkipLines(1);

    cout<<"Inorder traversal with stack:"<<endl;
    tree.InTravWithStack(show_int);
    SkipLines(1);
    
    cout<<"Preorder traversal:"<<endl;
    tree.PreTrav_Iter(show_int);
    SkipLines(1);

    cout<<"Postorder traversal iteration:"<<endl;
    tree.PostTrav_Iter(show_int);
    SkipLines(1);

    cout<<"Postorder traversal recursion:"<<endl;
    tree.PostTrav_Recur(show_int);
    SkipLines(1);

    cout<<"All nodes larger than  sum of  its ancestors(recursion): "<<endl;
    if(tree.TestSum_Recur())
        cout<<"pass"<<endl;
    else
        cout<<"fail"<<endl;
    SkipLines(1);

    cout<<"All nodes larger than  sum of  its ancestors(iteration): "<<endl;
    if(tree.TestSum_Iter())
        cout<<"pass"<<endl;
    else
        cout<<"fail"<<endl;
    SkipLines(3);

    cout<<"==========Tests for binary tree end========"<<endl;
}