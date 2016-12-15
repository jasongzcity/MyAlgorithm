/**
* BinTree.h
* Defines a most basic binary tree.
* Last modified on: 2016/10/09
* Author:lwz
**/

#ifndef BINTREE_H
#define BINTREE_H

#include "./MyStack.h"
#include "./MyQueue.h"

#define BinNodePosi(T) BinNode<T>*

ALGORITHM_BEGIN

typedef enum {RB_RED,RB_BLACK} RBColor;    //for red black tree

///////////////////////////////SHORTCUT//////////////////////////////////
////JUDGE LOGIC//////
//all judge function use x as the node itself, not the node's pointer.
#define IsRoot(x) (!(x).Parent)   
#define IsLChild(x) (!IsRoot(x) && ( &(x) == (x).Parent->LChild ))
#define IsRChild(x) (!IsRoot(x) && ( &(x) == (x).Parent->RChild ))
#define HasParent(x) ((x).Parent)
#define HasLChild(x) ((x).LChild)
#define HasRChild(x) ((x).RChild)
#define HasChild(x) ((x).RChild || (x).LChild)
#define HasBothChild(x) ((x).LChild && (x).RChild)
#define IsLeaf(x) (!HasChild(x))
/////JUDGE LOGIC END/////

#define Stature(x) ((x)?(x)->Height:-1) //the height node x. use x as node's pointer
#define Sibling(x) (IsLChild(x)? (x).Parent->RChild: (x).Parent->LChild) //the "brother" node the x.use x as the node itself.
#define Uncle(x) Sibling(((x).Parent))  //the "brother" of x's parent.use x as node itself
#define FromParentTo(x) (IsLChild(x)? (x).Parent->LChild:(x).Parent->RChild)//the pointer to x itself own by x's parent
/////////////////////////////////SHORTCUT END////////////////////////////////////////

//the node of binary tree
template <typename T>
class BinNode
{
public:
    T Data;
    BinNodePosi(T) Parent;
    BinNodePosi(T) LChild;
    BinNodePosi(T) RChild;
    int Height;//the height of the node. If the node is a leaf, length=0
    int Npl;//null path length for left heap.
    RBColor Color;//the color of the node

    BinNode()
        :Parent(NULL),LChild(NULL),RChild(NULL),Height(0),Npl(1),Color(RB_RED)
    {}
    BinNode(T e,BinNodePosi(T) p = NULL,BinNodePosi(T) l = NULL,BinNodePosi(T) r = NULL,int h = 0,int n = 1,RBColor c = RB_RED)
        :Data(e),Parent(p),LChild(l),RChild(r),Height(h),Npl(n),Color(c)
    {}
    
    virtual ~BinNode(){}

    //insert e as the node's left child
    BinNodePosi(T) InsertAsLC(T const& e)
    {
        LChild = new BinNode(e,this);   
        return LChild;
    }

    //insert e as the node's right child
    BinNodePosi(T) InsertAsRC(T const& e)
    {
        RChild = new BinNode(e,this);
        return RChild;
    }

    BinNodePosi(T) AttachAsLChild(BinNodePosi(T) lc)
    {
        LChild = lc;
        if(lc)
            lc->Parent = this;
        return this;
    }

    BinNodePosi(T) AttachAsRChild(BinNodePosi(T) rc)
    {
        RChild = rc;
        if(rc)
            rc->Parent = this;
        return this;
    }

    //Return the size of the subtree of this node.
    int Size() const
    {
        CountElement<T> c;
        PreTrav(c);//traverse the subtree using preorder traversal
        return c.result();
    }

    /**
    * Return the pointer of inorder traversal's successor of current node
    **/
    BinNodePosi(T) Succ()
    {
        BinNodePosi(T) p = this;
        if(RChild)
        {
            p = RChild;
            while(HasLChild(*p))
                p = p->LChild;
        }
        else
        {
            while(IsRChild(*p))
                p = p->Parent;
            p = p->Parent;
        }
        return p;
    }

     /**
    * Return the pointer of inorder traversal's predecessor of current node
    **/   
    BinNodePosi(T) Pred() 
    {
        BinNodePosi(T) p = this;
        if(HasLChild(*p))//then the predecessor must in the left subtree
        {
            p = p->LChild;
            while(HasRChild(*p))
                p = p->RChild;
            return p;
        }
        else if(IsLChild(*p))
        {
            while(IsLChild(*p))
                p = p->Parent;
            return p->Parent;//return parent for p is right child or return NULL for p is the root.
        }
        else                            //this node is right child but no has no left child, or this node is the root.
            return p->Parent;
    }

    //==========traversal methods use this node as root===========//
    //using function object to traverse
    //using stack as help instead of using recursive call (better at performance)

    //preorder traversal
    template <typename VST>
    void PreTrav(VST& visit)
    {
        MyStack<BinNodePosi(T)> S;//use stack to "remember" the right child of current node, and visit it later
        BinNodePosi(T) x = this;
        while(1)
        {
            while(x)
            {
                visit(x->Data);
                S.Push(x->RChild);
                x = x->LChild;
            }
            if(S.IsEmpty())
                return;
            x = S.Pop();
        }
    }

    //inorder traversal version 1, using helper stack
    template <typename VST>
    void Intrav_V1(VST& visit)
    {
        MyStack<BinNodePosi(T)> S;
        BinNodePosi(T) x = this;
        while(1)
        {
            while(x)
            {
                S.Push(x);
                x = x->LChild;
            }
            if(S.IsEmpty())
                return;
            x = S.Pop();
            visit(x->Data);
            x = x->RChild;
        }
    }

    /**
    * Inorder traversal version 2. Using backtracking flag and the successor & predecessor method 
    * above instead of using helper stack.
    * So it is supposed to be slightly better at performance.
    **/
    template <typename VST>
    void Intrav_V2(VST& visit)
    {
        BinNodePosi(T) x = this;    
        bool backtracked = false;//backtrack flag.
        while(1)
        {
            if(!backtracked && HasLChild(*x))//Do not search left subtree if already backtracked
                x = x->LChild;                               //Find successor instead
            else
            {
                visit(x->Data);//visit the "leftest"
                if(HasRChild(*x))//successor must be in right subtree, if any.     
                {
                    x = x->RChild;
                    backtracked = false;//x is not backtracked from its left subtree now, able to "go left"
                }
                else//if  x has neither left child  nor right child, then it must backtrack. 
                {
                    x = x->Succ();
                    if(!x)
                        return;
                    backtracked = true;
                }
            }
        }
    }

    //Recursive version of Postorder traversal
    template <typename VST>
    void PostTrav_Recur(VST& visit)
    {
        PostTrav_rec_inner(this,visit);
    }

    template <typename VST> void PostTrav_rec_inner(BinNodePosi(T) p, VST& visit)
    {
        if(p==NULL) //base
            return;
        PostTrav_rec_inner(p->LChild,visit);
        PostTrav_rec_inner(p->RChild,visit);
        visit(p->Data);
    }

    //postorder traversal using helper stack.
    template <typename VST>
    void PostTrav(VST& visit)
    {
        MyStack<BinNodePosi(T)> S;
        BinNodePosi(T) p = this;
        S.Push(p);
        while(!S.IsEmpty())
        {
            if(S.Top() != p->Parent)//then top must not be "digged"
            {                                      //"dig"
                p = S.Top();
                while(p)//the procedure of  "digging" into p's subtree
                {            //dig and put nodes in right sequence
                    if(HasLChild(*p))
                    {
                        if(HasRChild(*p))
                            S.Push(p->RChild);  //push left child after right child to visit left child first.
                        S.Push(p->LChild);
                    }
                    else
                        S.Push(p->RChild);
                    p = S.Top();
                }//while(p)
                S.Pop();                    //pop out an empty node.
            }
            p = S.Pop();//pop and visit a node in each outer loop
            visit(p->Data);
        }
    }

    //level traversal.
    //the FIFO data structure fits level traversal perfectly.
    template <typename VST>
    void LevelTrav(VST& visit)
    {
        MyQueue <BinNodePosi(T)> Q;
        BinNodePosi(T) p = this;
        Q.Enqueue(p);
        while(!Q.IsEmpty())
        {
            p = Q.Dequeue();
            visit(p->Data);
            if(HasLChild(*p))//enqueue left child and then right.
                Q.Enqueue(p->LChild);
            if(HasRChild(*p))
                Q.Enqueue(p->RChild);
        }
    }

    bool operator<(BinNodePosi(T) p)
    {
        return Data < p->Data;
    }

    bool operator==(BinNodePosi(T) p)
    {
        return (Data == p->Data);
    }

};

/**
* Binary Tree.
* Most methods are based on methods of BinNode.
**/
template <typename T> 
class BinTree
{
protected:
    BinNodePosi(T) _root;
    int _size;

    virtual int UpdateHeight(BinNodePosi(T) p) // reserve for further extension, different trees have different definition for height.
    {
        //using namespace std;
        p->Height = 1+std::max(Stature(p->LChild),Stature(p->RChild));
        return p->Height;
    }
    void UpdateHeightAbove(BinNodePosi(T) p)
    {
        UpdateHeight(p);
        p = p->Parent;
        while(p)
        {
            int temp = p->Height;
            UpdateHeight(p);//if height stop changing, stop updating.
            if(temp == p->Height)
                break;
            p = p->Parent;
        }
    }

    /**
    * rotate left at node n
    * the caller should adjust the node's height himself.
    * Return false if rotation fail.
    * Return true if rotation success.
    **/
    bool rotateLeft(BinNodePosi(T) n)
    {
        BinNodePosi(T) rc = n->RChild;
        BinNodePosi(T) p = n->Parent;
        if(rc==NULL)
            return false;//can't rotate left if right child doesn't exist
        n->RChild = rc->LChild;
        if(n->RChild != NULL)
            n->RChild->Parent = n;
        rc->LChild = n;

        n->Parent = rc;
        rc->Parent = p;
        if(p!=NULL)
            FromParentTo(*n) = rc;
        return true;
    }

    /**
    * rotate right at node n
    * the caller should adjust the node's height himself.
    * Return false if rotation fail.
    * Return true if rotation success.
    **/
    bool rotateRight(BinNodePosi(T) n)
    {
        BinNodePosi(T) lc = n->LChild;
        BinNodePosi(T) p = n->Parent;
        if(lc==NULL)
            return false;//can't rotate left if right child doesn't exist
        n->LChild = lc->RChild;
        if(n->LChild != NULL)
            n->LChild->Parent = n;
        lc->RChild = n;

        n->Parent = lc;
        lc->Parent = p;
        if(p!=NULL)
            FromParentTo(*n) = lc;
        return true;
    }

public:
    BinTree()
        :_root(NULL),_size(0)
    {}
    
    virtual ~BinTree()//release nodes of this tree. The same algorithm with level traversal.
    {
        if(IsEmpty())
            return;
        MyQueue<BinNodePosi(T)> Q;
        Q.Enqueue(_root);
        while(!Q.IsEmpty())
        {
            BinNodePosi(T) p = Q.Dequeue();
            if(HasLChild(*p))
                Q.Enqueue(p->LChild);
            if(HasRChild(*p))
                Q.Enqueue(p->RChild);
            delete p;
        }
    }

    int Size() const
    {
        return _size;
    }

    bool IsEmpty() const
    {
        return _size==0;
    }

    BinNodePosi(T) Root() const
    {
        return _root;
    }

    BinNodePosi(T) InsertAsRoot(T const& e) 
    {
        _size = 1;
        return _root = new BinNode<T>(e);
    }

    BinNodePosi(T) InsertAsLC(BinNodePosi(T) p,T const& e)
    {
        _size++;
        p->InsertAsLC(e);
        UpdateHeightAbove(p->LChild);
        return p->LChild;
    }

    BinNodePosi(T) InsertAsRC(BinNodePosi(T) p,T const& e)
    {
        _size++;
        p->InsertAsRC(e);
        UpdateHeightAbove(p->RChild);
        return p->RChild;
    }

    /**
    * Attach BinTree t as p's left subtree.
    **/
    BinNodePosi(T) AttachAsLT(BinNodePosi(T) p,BinTree<T>& t)
    {
        p->LChild = t._root;
        p->LChild->Parent = p;
        _size += t._size;
        UpdateHeightAbove(p);

        t._root = NULL;//withdraw t
        t._size = 0;
        return p;
    }

    /**
    * Attach BinTree t as p's left subtree.
    **/
    BinNodePosi(T) AttachAsRT(BinNodePosi(T) p,BinTree<T>& t)
    {
        p->RChild = t._root;
        p->RChild->Parent = p;
        _size += t._size;
        UpdateHeightAbove(p);

        t._root = NULL;
        t._size = 0;
        return p;
    }

    //=====Traversal methods======//
    template<typename VST>
    void PreTrav_Iter(VST& visit)//preorder traversal iteration version.
    {
        _root->PreTrav(visit);
    }

    template<typename VST>
    void InTravWithStack(VST& visit)//inorder traversal using stack
    {
        _root->Intrav_V1(visit);
    }

    template<typename VST>
    void InTravWithNoStack(VST& visit)//inorder traversal using succ() method & backtrack flag.
    {
        _root->Intrav_V2(visit);
    }

    template<typename VST>
    void PostTrav_Iter(VST& visit)//postorder traversal iteration version.
    {
        _root->PostTrav(visit);
    }

    template<typename VST>
    void LevelTrav_Iter(VST& visit)//level traversal.
    {
        _root->LevelTrav(visit);
    }

    template <typename VST>
    void PostTrav_Recur(VST& visit)//post traversal recursive version
    {
        _root->PostTrav_Recur(visit);
    }

    /////////////////////practise..////////////////////////////
    // The question is: check if the data of any node in this tree larger than the sum of its ancestors' data
    //using recursive method and iteration(loop) method to solve this problem respectively

    //the entrance
    bool TestSum_Recur()
    {
        MyStack<int> sumtemp;
        sumtemp.Push(0);
        return TestNode(_root,sumtemp);
    }

    //this recursive method uses preorder traversal
    bool TestNode(BinNodePosi(T) p, MyStack<int>& S)
    {
        int temp = S.Top();
        if(temp>p->Data)//if the sum of ancestors' is larger, return directly.
            return false;
        S.Push(temp + p->Data);
        if(HasLChild(*p))
        {
            if(TestNode(p->LChild,S))   //recursive call, check the children
                S.Pop();
            else
                return false;
        }
        if(HasRChild(*p))
        {
            if(TestNode(p->RChild,S))
                S.Pop();
            else
                return false;
        }
        return true;
    }

    //iteration version
    //using preorder traversal
    bool TestSum_Iter()
    {
        MyStack<int> Sum;                //stack of sum 
        MyStack<BinNodePosi(T)> S;       //stack of nodes
        Sum.Push(0);
        S.Push(_root);
        BinNodePosi(T) p;
        while(!S.IsEmpty())
        {
            p = S.Pop();
            int temp = Sum.Pop();
            if(temp > p->Data)
                return false;
            if(HasRChild(*p))
            {
                S.Push(p->RChild);
                Sum.Push(temp + p->Data);
            }
            if(HasLChild(*p))
            {
                S.Push(p->LChild);
                Sum.Push(temp+p->Data);
            }
        }
        return true;
    }
    ///////////////////ends..////////////////////////
};

ALGORITHM_END

#endif //BINTREE_H