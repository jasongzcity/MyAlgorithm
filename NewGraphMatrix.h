/* Created on 2016-1-4 21:46:31 Author:LWZ   */
#include "graph.h"
#ifndef ADT_VECTOR_H
#include "ADT_Vector.h"
#endif


////����ͱߵĽṹ��ģ��////////////
//ע��:dTimeһ���ʾ����ʱ��,fTime��ʾ"�뿪"ʱ��
template <typename Tv> struct Vertex { //�������Ϊ���������δ�ϸ��װ��
   Tv data; int inDegree, outDegree; VStatus status; //���ݡ����������״̬
   int dTime, fTime; //ʱ���ǩ
   int parent; int priority; //�ڱ������еĸ��ڵ㡢���ȼ���
   Vertex ( Tv const& d = ( Tv ) 0 ) : //�����¶���
      data ( d ), inDegree ( 0 ), outDegree ( 0 ), status ( UNDISCOVERED ),
      dTime ( -1 ), fTime ( -1 ), parent ( -1 ), priority ( INT_MAX ) {} //�ݲ�����Ȩ�����
};

template <typename Te> struct Edge { //�߶���Ϊ���������δ�ϸ��װ��
   Te data; int weight; EType type; //���ݡ�Ȩ�ء�����
   Edge ( Te const& d, int w ) : data ( d ), weight ( w ), type ( UNDETERMINED ) {} //����
};
//////////////////////////////////////////////////////////////////////////////


/////////////////////�ڽӾ���ģ��////////////////////////////////
template<typename Tv,typename Te>
class GraphMatrix: public Graph<Tv,Te>			//ͼ����,�������������ɵı߼���һ���������صĶ��㼯����
{	
private:

	SVector< Vertex< Tv > > V;					//���㼯��������
	SVector< SVector< Edge< Te > * > > E;		//�߼����ڽӾ���

public:
	GraphMatrix()
	{
		n = e = 0;
	}
	virtual ~GraphMatrix()
	{
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				delete E[i][j];			//���ɾ����
			}
		}
	}

	//�����������:��ѯ

	virtual Tv& vertex(int i)
	{
		return V[i].data;
	}
	
	virtual int inDegree(int i)
	{
		return V[i].inDegree;
	}

	virtual int outDegree(int i)
	{
		return V[i].outDegree;
	}

	virtual int firstNbr(int i)
	{
		return nextNbr(i,n);
	}

	virtual int nextNbr(int i,int j)			//�Զ���i��˵,j����һ���ڽӶ���
	{
		while((-1<j) && (!exists(i,--j)));		//������VNum��ʼ���¼�����һ�����ڵı��򷵻�.���ö������ھ��򷵻�-1
		return j;
	}

	virtual VStatus& status(int i)
	{
		return V[i].status;
	}

	virtual int& dTime(int i)
	{
		return V[i].dTime;
	}

	virtual int& fTime(int i)
	{
		return V[i].fTime;
	}

	virtual int& parent(int i)
	{
		return V[i].parent;			//�������еĸ���
	}

	virtual int& priority(int i)
	{
		return V[i].priority;
	}

	//����Ķ�̬����
	virtual int insert(Tv const& data)		//�������,��Ҫ���ڽӾ����м�
	{
		for(int i=0;i<n;i++)
		{
			E[i].Insert(NULL,n);			//������Ҫ���������=
		}
		n++;
		E.Insert(SVector<Edge<Te>*>(n,n,(Edge<Te>*)NULL));	//......
		return V.Insert(Vertex<Tv>(data));			//���ظö����������е���
	}

	virtual Tv remove(int i)				//�ͷž����пռ�,�Լ�����洢��ָ��ָ��Ķѿռ�
	{
		for(int j=0;j<n;j++)
		{
			if(exists(i,j))
			{
				delete E[i][j];				//ɾ������
				V[j].inDegree--;
			}
		}
		E.Remove(i);
		n--;
		Tv VBack = vertex(i);				//���ƶ���i��Ϣ,Tv���������ز�����=
		V.Remove(i);
		for(int j=0;j<n;j++)
		{
			if(Edge<Te>* p = E[j].Remove(i))			//��p��ΪNull(��Ϊһ��ʼȫ������ΪNULL)
			{
				delete p;
				V[j].outDegree--;
			}
		}
		return VBack;
	}

	//�����Բ���
	virtual bool exists(int i,int j)
	{
		return ((i>=0) && (i<n) && (j>=0) && (j<n) && (E[i][j]!=NULL));
	}

	virtual EType& type(int i,int j)
	{
		return E[i][j]->type;
	}

	virtual Te& edge(int i,int j)
	{
		return E[i][j]->data;
	}

	virtual int& weight(int i,int j)
	{
		return E[i][j]->weight;
	}

	//�ߵĶ�̬����
	virtual void insert(Te const& data,int i,int j,int w)	//�������new�ڶ��д����ṹ��,�ڷ�������������
	{
		if(exists(i,j))		//ȷ��������
		{
			return;
		}
		E[i][j] = new Edge<Te>(data,w);			//�����ṹ��,ѭ�ȷ���������
		V[i].outDegree++;
		V[j].inDegree++;
		e++;
	}

	virtual Te remove(int i,int j)					//ɾ����(i,j)
	{
		Te EBack = edge(i,j);			//���ݱ�������
		delete E[i][j];
		//E[i].Remove[j];				//����,���㻹��!
		E[i][j] = NULL;
		V[i].outDegree--;
		V[j].outDegree--;
		e--;
		return EBack;
	}	
};//class GraphMatrix

#include "GraphImp.h"					//ͼ���������㷨��ʹ��ͼ������ʵ�ֵĲ�ѯ����