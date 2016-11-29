/* Created on 2016-1-21 20:50:44 by LWZ. Template for RedBlack Tree */

#ifndef RBTREE_H
#define RBTREE_H

#include "./MyAlgorithm.h"
#include "./BinarySearchTree.h"

///////////////////////FAST ROUTE////////////////////////////
#define IsBlack(x) ((!x) || ((x)->Color == RB_BLACK))
#define IsRed(x)   !(IsBlack(x))
////////////////////////////////////////////////////////


//红黑树模板,基类为标准二叉搜索树
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
    virtual int UpdateHeight(BinNodePosi(T) p)      //这里Height是黑高度,故改写
    {
        p->Height = max(Stature(p->LChild),Stature(p->RChild));
        return IsBlack(p)?p->Height++:p->Height;    //若黑节点还需加1高度
    }
public:
    virtual BinNodePosi(T) Insert(T const& e)
    {

    }
    virtual bool Remove(T const& e)
    {

    }
    bool BlackHeightUpdated(BinNodePosi(T) p)       //改用查询函数方式实现
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
    //Search方法仍沿用标准二叉搜索树的
};

#endif //RBTREE_H