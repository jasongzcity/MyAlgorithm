/* Created on 2016-1-21 20:50:44 by LWZ. Template for RedBlack Tree */

#ifndef RBTREE_H
#define RBTREE_H

#include "./MyAlgorithm.h"
#include "./BinarySearchTree.h"

///////////////////////FAST ROUTE////////////////////////////
#define IsBlack(x) ((!x) || ((x)->Color == RB_BLACK))
#define IsRed(x)   !(IsBlack(x))
////////////////////////////////////////////////////////


//�����ģ��,����Ϊ��׼����������
template<typename T>
class RBTree : public BinarySearchTree<T>
{
protected:
    void solveDoubleRed(BinNodePosi(T) p)
    {

    }
    void solveDoubleBlack(BinNodePosi(T) p)
    {

    }
    virtual int UpdateHeight(BinNodePosi(T) p)      //����Height�Ǻڸ߶�,�ʸ�д
    {
        p->Height = max(Stature(p->LChild),Stature(p->RChild));
        return IsBlack(p)?p->Height++:p->Height;    //���ڽڵ㻹���1�߶�
    }
public:
    virtual BinNodePosi(T) Insert(T const& e)
    {

    }
    virtual bool Remove(T const& e)
    {

    }
    bool BlackHeightUpdated(BinNodePosi(T) p)       //���ò�ѯ������ʽʵ��
    {
        if(Stature(p->LChild) == Stature(p->RChild))
        {
            if(IsBlack(p))
            {
                return (p->Height == Stature(p->LChild)+1);
            }
            else
            {
                return (p->Height == Stature(p->LChild));
            }
        }
        return false;
    }
    //Search���������ñ�׼������������
};

#endif //RBTREE_H