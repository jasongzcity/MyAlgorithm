/* Created for temporary test for GraphTest */
#define VANDE_H
#ifndef _INC_LIMITS
#include "limits.h"
#endif

typedef enum
{
	UNDISCOVERED,
	DISCOVERED,
	VISITED
}VStatus;//����״̬

typedef enum
{
	UNDETERMINED,
	TREE,
	CROSS,
	FORWARD,
	BACKWARD
}EType;//���ڱ������е�����

template<typename Tv>	//����Ҫ���������ǰ��ñ����ͺͶ�������,�����ɾ������
class Vertex
{
//	friend class Graph<Tv,Te>;		//ͼ�������з���Ȩ
private:
	Tv Data;
	int InDegree,OutDegree;
	int DTime,FTime;
	int Parent;
	int Priority;
	VStatus Status;

public:
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

	Tv& RefData()
	{
		return Data;
	}
};//class Vertex

template <typename Te>
class Edge
{
//	friend class Graph<Tv,Te>;		//���ŷ���Ȩ��ͼ��
public:
	//������
	Te Data;
	int Weight;
	EType Type;

public:
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
