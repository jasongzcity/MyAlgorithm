/* Created on 2016-1-7 by LWZ. Template for AVL Tree,derived from BST */

#define AVLTREE_H

#ifndef BST_H
#include "BST.h"
#endif

#include "math.h"

/********************************************
  BinTree
    BST
      kd-tree/�����/AVL�� - BBST(Balanced Binary Search Tree)
*********************************************/

//////////////////////////��ݷ�ʽ/////////////////////////////////////
/*  ƽ���������������߽�ȥ��������(ȡ����ֵ)
 *  AVLƽ��������ƽ������С��2
 *  �ÿ�ݷ�ʽ���ٲ�ѯ
 */
#define Balanced(x) ( Stature( (x).LChild ) == Stature( (x).RChild ) )      //�������������������ƽ��
#define BalFactor(x) (abs( Stature( (x).LChild ) - Stature( (x).RChild ) )) //ƽ������
#define AVLBalanced(x) (BalFactor(x)<2)

///////////////////////////////////////////////////////////////////////

template <typename T>
class AVL : public BST<T>
{
public:
    //ֻ���������нӿ���Ҫ��д

    virtual bool Remove(T const& e)
    {
        BinNodePosi(T)& del = Search(e);
        if(!del)
        {
            return false;   //������Ͳ����ھ�ֱ�ӷ���
        }
        RemoveAt(del,_hot);
        _size--;
        for(BinNodePosi(T) g=_hot;g;g=g->Parent)    //��ɾ���ڵ�����������ƽ���ֱ������
        {
            if(!AVLBalanced(*g))    //���ֲ�ƽ��//���۵���˫����ֻ��һ�β���
            {
                if(IsRoot(*g))
                {
                    _root = rotateAt(TallerChild(TallerChild(g)));
                }
                else
                {
                    g = FromParentTo(*g) = rotateAt(TallerChild(TallerChild(g)));   //��ת�󷵻ص��Ǹ�,��ԭ���ϲ�����
                }                                           
            }
            UpdateHeight(g);
        }
        return true;
    }

    virtual BinNodePosi(T) Insert(T const& e)
    {                                                       //���Ӷ�O(logn)
        BinNodePosi(T)& x = Search(e);
        if(x)
        {
            return x;                   //���Ѿ��иö�����,ֱ�ӷ���
        }                   
        x = new BinNode<T>(e,_hot);
        //if(_hot)
        //{
        //  UpdateHeightAbove(_hot);
        //}
        _size++;
        for(BinNodePosi(T) g=_hot;g;g=g->Parent)    //����
        {
            if(!AVLBalanced(*g))    //���ֲ�ƽ��
            {
                if(IsRoot(*g))
                {
                    _root = rotateAt(TallerChild(TallerChild(g)));      //��¼��ת֮��ĸ��ڵ��ַ
                }
                else
                {
                    BinNodePosi(T)& tp = FromParentTo(*g);
                    tp = rotateAt(TallerChild(TallerChild(g))); //��ת�󷵻ص��Ǹ�,��ԭ���ϲ�����
                }
                                
                break;
            }
            else
            {
                UpdateHeight(g);            //��3+4�ĸ�����ʼ���¸߶�,���½ڵ�߶��Ѹ��¹�
            }
        }
        return x;
    }

};//class AVL