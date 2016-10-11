/* Created on 2016-1-7 by LWZ. Template for AVL Tree,derived from BST */

#define AVLTREE_H

#ifndef BST_H
#include "BST.h"
#endif

#include "math.h"

/********************************************
  BinTree
    BST
      kd-tree/红黑树/AVL树 - BBST(Balanced Binary Search Tree)
*********************************************/

//////////////////////////快捷方式/////////////////////////////////////
/*  平衡因子是左子树高将去右子树高(取绝对值)
 *  AVL平衡条件是平衡因子小于2
 *  用快捷方式快速查询
 */
#define Balanced(x) ( Stature( (x).LChild ) == Stature( (x).RChild ) )      //左子树右子树高相等则平衡
#define BalFactor(x) (abs( Stature( (x).LChild ) - Stature( (x).RChild ) )) //平衡因子
#define AVLBalanced(x) (BalFactor(x)<2)

///////////////////////////////////////////////////////////////////////

template <typename T>
class AVL : public BST<T>
{
public:
    //只有两个公有接口需要改写

    virtual bool Remove(T const& e)
    {
        BinNodePosi(T)& del = Search(e);
        if(!del)
        {
            return false;   //若本身就不存在就直接返回
        }
        RemoveAt(del,_hot);
        _size--;
        for(BinNodePosi(T) g=_hot;g;g=g->Parent)    //从删除节点向上搜索不平衡点直到树根
        {
            if(!AVLBalanced(*g))    //发现不平衡//无论单旋双旋都只是一次操作
            {
                if(IsRoot(*g))
                {
                    _root = rotateAt(TallerChild(TallerChild(g)));
                }
                else
                {
                    g = FromParentTo(*g) = rotateAt(TallerChild(TallerChild(g)));   //旋转后返回的是根,与原根上层相连
                }                                           
            }
            UpdateHeight(g);
        }
        return true;
    }

    virtual BinNodePosi(T) Insert(T const& e)
    {                                                       //复杂度O(logn)
        BinNodePosi(T)& x = Search(e);
        if(x)
        {
            return x;                   //若已经有该顶点了,直接返回
        }                   
        x = new BinNode<T>(e,_hot);
        //if(_hot)
        //{
        //  UpdateHeightAbove(_hot);
        //}
        _size++;
        for(BinNodePosi(T) g=_hot;g;g=g->Parent)    //搜索
        {
            if(!AVLBalanced(*g))    //发现不平衡
            {
                if(IsRoot(*g))
                {
                    _root = rotateAt(TallerChild(TallerChild(g)));      //记录旋转之后的根节点地址
                }
                else
                {
                    BinNodePosi(T)& tp = FromParentTo(*g);
                    tp = rotateAt(TallerChild(TallerChild(g))); //旋转后返回的是根,与原根上层相连
                }
                                
                break;
            }
            else
            {
                UpdateHeight(g);            //从3+4的根部开始更新高度,向下节点高度已更新过
            }
        }
        return x;
    }

};//class AVL