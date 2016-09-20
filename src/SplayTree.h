/* Created on 2016-1-12 16:26:02 by LWZ. Templates for SplayTree,Derived from BST*/

#define SPLAYTREE_H

#ifndef BST_H
#include "BST.h"
#endif

///////////////////////��������////////////////////////////
//ʹ�ýڵ�ָ����Ϊģ��
template<typename TreeNode> inline	
void AttachAsLChild(TreeNode p,TreeNode lc)
{
	p->LChild = lc;
	if(lc)			//���Ӵ���
	{
		lc->Parent = p;
	}
}

template<typename TreeNode> inline	
void AttachAsRChild(TreeNode p,TreeNode rc)
{
	p->RChild = rc;
	if(rc)			//���Ӵ���
	{
		rc->Parent = p;
	}
}
////////////////////////����////////////////////////////



template<typename T>							//��չ��(SplayTree ģ��)
class SplayTree : public BST<T>
{
protected:
	BinNodePosi(T) splay(BinNodePosi(T) v)		//�����ͷ���,��Ŀ��v��չ������λ��,��������λ��
	{
		//v���Ѿ�������ϵ�ָ��
		if(!v)				//������Ϊ��,��������չֱ�ӷ���
		{
			return v;
		}
		BinNodePosi(T) p;
		BinNodePosi(T) g;
		while((p= v->Parent)&&(g=p->Parent))	//������Դ�ұ�֤�����游�ڵ㲻Ϊ��
		{
			BinNodePosi(T) gg = g->Parent;		//�ȼ�¼���游(����Ϊ��,��gΪ��),�Ժ�Ҫ����
			//���·��������������չ
			if(IsLChild(*p))
			{
				if(IsLChild(*v))
				{
					//��-�� zig-zig
					//��һ��˳ʱ����ת
					AttachAsLChild(g,p->RChild);
					AttachAsLChild(p,v->RChild);
					//�ڶ���˳ʱ����ת
					AttachAsRChild(p,g);
					AttachAsRChild(v,p);
				}
				else	
				{
					//��-�� zag-zig  
					//zag
					AttachAsRChild(p,v->LChild);
					AttachAsLChild(v,p);
					//zig
					AttachAsLChild(g,v->RChild);
					AttachAsRChild(v,g);
				}
			}
			else
			{
				if(IsRChild(*v))
				{
					//��-�� zag-zag
					//zag1
					AttachAsRChild(g,p->LChild);
					AttachAsLChild(p,g);
					//zag2
					AttachAsRChild(p,v->LChild);
					AttachAsLChild(v,p);
				}
				else
				{
					//��-�� zig-zag
					//zig
					AttachAsLChild(p,v->RChild);
					AttachAsRChild(v,p);
					//zag
					AttachAsRChild(g,v->LChild);
					AttachAsLChild(v,g);
				}
			}

			//�������游
			if(!gg)		//��ô��ʱvΪ��
			{
				v->Parent = NULL;
			}
			else
			{
				//(g==gg->LChild)? gg->LChild:gg->RChild = v;		//����д�Ǵ����,�ᵼ��gg�ĺ��ӻ���g
				(g==gg->LChild)?AttachAsLChild(gg,v):AttachAsRChild(gg,v);
				v->Parent = gg;
			}
			UpdateHeight(g);		//ÿ����ת�궼Ҫ���¸߶�
			UpdateHeight(p);
			UpdateHeight(v);
			
		}//while((p=v->Parent) && (g=p->Parent))

		if(p = v->Parent)			//�п��ܻ���һ�����ڵ�,�游�Ѿ��ǿ�
		{
			if(IsLChild(*v))		//˳ʱ����һ�ε���
			{
				AttachAsLChild(p,v->RChild);
				AttachAsRChild(v,p);
			}
			else
			{
				AttachAsRChild(p,v->LChild);
				AttachAsLChild(v,p);
			}
			UpdateHeight(p);
			UpdateHeight(v);
		}

		v->Parent = NULL;//��������
		return v;
	}

public:
	//��д����,ɾ��������
	virtual BinNodePosi(T)& Search(const T& e)		//�����󽫽ڵ㷭������(����ʧ��������λ�õĸ��ڵ㷭������)
	{
		BinNodePosi(T) p = SearchIn(_root,e,_hot = NULL);
		_root = splay( p?p:_hot );
		return _root;			//����,��ʱ�ϲ�Է���ֵ���޸ļ��޸���_root,�򷵻�����
	}

	virtual BinNodePosi(T) Insert(const T& e)
	{
		if(!_root)		//�����˻����
		{
			_size++;
			return _root = new BinNode<T>(e);
		}
		if(e == Search(e)->Data)		//�����и�Ԫ��
		{
			return _root;
		}
		_size++;
		BinNodePosi(T) p = _root;	//��¼������ַ
		if(p->Data > e)
		{
			p->Parent = _root = new BinNode<T>(e,NULL,p->LChild,p);	//�����½ڵ���Ϊ����
			if(p->LChild)			//p������������
			{
				p->LChild->Parent = _root;
				p->LChild = NULL;
			}
		}
		else
		{
			p->Parent = _root = new BinNode<T>(e,NULL,p,p->RChild);
			if(p->RChild)
			{
				p->RChild->Parent = _root;
				p->RChild = NULL;
			}
		}
		UpdateHeightAbove(p);
		return _root;		//��ô����ԭ�������Ƿ���e,������Ȼ��Ϊe�ҷ�������
	}

	virtual bool Remove(const T& e)	//ɾ��Ԫ��Ϊe�Ľڵ�,�����丸�׽ڵ���������-���������ݵľֲ���
	{
		BinNodePosi(T) p = Search(e);	//����Ԫ��(���ǿ�������,�����׽ڵ㵽����)
		if(p->Data != e)				//������ʧ��
		{
			return false;	//��ɾ��ʧ��
		}
		if(!HasLChild(*p))	//����������
		{
			_root = p->RChild;
			if(_root)		//����������
			{	
				_root->Parent = NULL;
			}
		}
		else if(!HasRChild(*p))	//�ֻ������Һ���
		{
			_root = p->LChild;
			if(_root)
			{
				_root->Parent = NULL;
			}
		}
		else					//���Һ��Ӷ���
		{	
			BinNodePosi(T) lefttree = p->LChild;	//��¼������
			p->LChild = NULL;
			lefttree->Parent = NULL;				//�������ķ���

			_root = p->RChild;						//�������ķ���
			_root->Parent = NULL;
			Search(p->Data);						//��������������һ��Ԫ��e(��ʧ��,���������Ľڵ��Ȼ��������)
													//��ΪeС��������������Ԫ��,����SearchIn����,ֻ��һֱ"��ת"ֱ����
			_root->LChild = lefttree;				//�����������½���
			lefttree->Parent = _root;		
			p->~BinNode();//ɾ���ڵ�p
			delete p;
			_size--;
		}
		if(_root)	//������
		{
			UpdateHeight(_root);		//�п����������ϸ�,������ȫ���Ѿ���Splay()�и��¹��߶�
		}
		return true;
	}
};//class SplayTree