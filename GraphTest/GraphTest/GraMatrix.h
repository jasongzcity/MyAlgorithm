/* Created on 2016-1-4 16:42:56  Author:LWZ */

#include "SGraph.h"
#ifndef _INC_LIMITS
#include "limits.h"
#endif

//#ifndef ADT_VECTOR_H
//#include "ADT_Vector.h"
//#endif
#include "vector.h"

template<typename Tv>				//����Ҫ���������ǰ��ñ����ͺͶ�������,�����ɾ������
struct Vertex
{
//	friend class Graph<Tv,Te>;		//ͼ�������з���Ȩ
	Tv Data;
	int InDegree,OutDegree;
	int DTime,FTime;
	int Parent;
	int Priority;
	VStatus Status;

//public:
	Vertex(Tv const& d  = (Tv)0)	//���޲���Ĭ��TvΪ0
		:Data(d),InDegree(0),OutDegree(0),DTime(-1),FTime(-1),
		Parent(-1),Priority(INT_MAX),Status(UNDISCOVERED){}

	Vertex<Tv>& operator=(const Vertex<Tv>& v)
	{
		Data = v.Data;
		InDegree = v.InDegree;
		OutDegree = v.OutDegree;
		DTime = v.DTime;
		FTime = v.FTime;
		Parent = v.Parent;
		Priority = v.Priority;
		Status = v.Status;
		return (*this);
	}
};//class Vertex

template <typename Te>
struct Edge
{
//	friend class Graph<Tv,Te>;		//���ŷ���Ȩ��ͼ��
//public:
	//������
	Te Data;
	int Weight;
	EType Type;

//public:
	Edge(Te const& e,int w)
		:Data(e),Weight(w),Type(UNDETERMINED){}

	Edge<Te>& operator=(const Edge<Te>& e)
	{
		Te = e.Data;
		Weight = e.Weight;
		EType = e.Type;
		return (*this);
	}
};//class Edge


/******************  
 *   �ڽӾ���
*******************/
template<typename Tv,typename Te>
class GraphMatrix: public Graph<Tv,Te>		//ͼ����,�������������ɵı߼���һ���������صĶ��㼯����
{	
private:
	//SVector< Vertex< Tv > > V;				//���㼯��������
	//SVector< SVector< Edge< Te > * > > E;		//�߼����ڽӾ���
	//Vector< Vertex< Tv > > V; //���㼯��������
	//Vector< Vector< Edge< Te > * > > E; //�߼����ڽӾ���

public:
	GraphMatrix()
	{
		ENum = VNum = 0;
	}
	virtual ~GraphMatrix()
	{
		for(int i=0;i<VNum;i++)
		{
			for(int j=0;j<VNum;j++)
			{
				delete E[i][j];			//���ɾ����
			}
		}
	}

	//�����������:��ѯ

	virtual Tv& Vertex(int i)
	{
		return V[i].Data;
	}
	
	virtual int InDegree(int i)
	{
		return V[i].InDegree;
	}

	virtual int OutDegree(int i)
	{
		return V[i].OutDegree;
	}

	virtual int FirstNbhd(int i)
	{
		return NextNbhd(i,VNum);
	}

	virtual int NextNbhd(int i,int j)
	{
		while((-1<j) && (!Exist(i,--j)));		//������VNum��ʼ���¼�����һ�����ڵı��򷵻�.���ö������ھ��򷵻�-1
		return j;
	}

	virtual VStatus& Status(int i)
	{
		return V[i].Status;
	}

	virtual int& DTime(int i)
	{
		return V[i].DTime;
	}

	virtual int& FTime(int i)
	{
		return V[i].FTime;
	}

	virtual int& Parent(int i)
	{
		return V[i].Parent;			//�������еĸ���
	}

	virtual int& Priority(int i)
	{
		return V[i].Priority;
	}

	//����Ķ�̬����
	virtual int Insert(Tv const& data)		//�������,��Ҫ���ڽӾ����м�
	{
		for(int i=0;i<VNum;i++)
		{
			E[i].Insert(NULL,VNum);			//������Ҫ���������=
		}
		VNum++;
		E.Insert(SVector<Edge<Te>*>(VNum,VNum,(Edge<Te>*)NULL));	//......
		return V.Insert(Vertex<Tv>(data));			//���ظö����������е���
	}

	virtual Tv Remove(int i)				//�ͷž����пռ�,�Լ�����洢��ָ��ָ��Ķѿռ�
	{
		for(int j=0;j<VNum;j++)
		{
			if(Exist(i,j))
			{
				delete E[i][j];				//ɾ������
				V[j].InDegree--;
			}
		}
		E.Remove(i);
		VNum--;
		Tv VBack = Vertex(i);				//���ƶ���i��Ϣ,Tv���������ز�����=
		V.Remove(i);
		for(int j=0;j<VNum;j++)
		{
			if(Edge<Te>* p = E[j].Remove(i))			//��p��ΪNull(��Ϊһ��ʼȫ������ΪNULL)
			{
				delete p;
				V[j].OutDegree--;
			}
		}
		return VBack;
	}

	//�����Բ���
	virtual bool Exist(int i,int j)
	{
		return ((i>=0) && (i<VNum) && (j>=0) && (j<VNum) && (E[i][j]!=NULL));
	}

	virtual EType& Type(int i,int j)
	{
		return E[i][j]->Type;
	}

	virtual Te& Edge(int i,int j)
	{
		return E[i][j]->Data;
	}

	virtual int& Weight(int i,int j)
	{
		return E[i][j]->Weight;
	}

	//�ߵĶ�̬����
	virtual void Insert(Te const& data,int i,int j,int w)	//�������new�ڶ��д����ṹ��,�ڷ�������������
	{
		if(Exist(i,j))		//ȷ��������
		{
			return;
		}
		E[i][j] = new Edge<Te>(data,w);			//�����ṹ��,ѭ�ȷ���������
		V[i].OutDegree++;
		V[j].InDegree++;
		ENum++;
	}

	virtual Te Remove(int i,int j)					//ɾ����(i,j)
	{
		Te EBack = Edge(i,j);			//���ݱ�������
		delete E[i][j];
		//E[i].Remove[j];				//����,���㻹��!
		E[i][j] = NULL;
		V[i].OutDegree--;
		V[j].OutDegree--;
		ENum--;
		return EBack;
	}
};//class GraphMatrix