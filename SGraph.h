/* Created on 2015-12-17 09:56:04 Author: LWZ 
   This file contains templates for graph and graph matrix.
*/

#define SGRAPH_H
//#include "ASonStack.h"

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

template<typename Tv,typename Te>	//������,��������		����ģ���������ڽӿ�Э��,��һЩ�ӿڱ���ʵ��,���ܹ����廯��ʵ����
class Graph							//���ǳ���ģ����,���ڽӱ���ڽӾ���������ʵ��
{
public:
	int ENum;		//����
	int VNum;		//�������
private:
	void reset()
	{
		for(int i=0;i<VNum;i++)
		{
			Status(i) = UNDISCOVERED;
			FTime(i) = DTime(i) = -1;
			Parent(i) = -1;
			Priority(i) = INT_MAX;
			for(int j=0;j<VNum;j++)
			{
				if(Exist(i,j))		//����(i,j)����
				{
					Type(i,j) = UNDETERMINED;
				}
			}//for
		}//for
	}

	//void bfs(int,int&);					//��ͨ����ڲ��Ĺ�����ȱ����㷨(�������ͨ����ṹ��ɭ��)
	//void dfs(int,int&);					//��ͨ����ڲ���������ȱ����㷨
	//void bcc(int,int&,SStack<int>&);	//��ͨ���ڲ��Ļ���DFS��˫��ͨ�����ֽ��㷨
	//bool tsort(int,int&,SStack<Tv>*);	//��ͨ���ڲ�����DFS�����������㷨

	//template<typename PU>void pfs(int,PU);	//��ͨ�������ȼ��������

public:
	//���㲿�ַ���
	virtual int Insert(Tv const&) = 0;		//���붥��,���ر��
	virtual Tv Remove(int) = 0;				//ɾ�����㼰�������,���ض�����Ϣ
	virtual Tv& Vertex(int) = 0;			//���ض��������(��ֱ���޸�)
	virtual int InDegree(int) = 0;			//���ظö�������
	virtual int OutDegree(int) = 0;			//���ض���ĳ���
	virtual int FirstNbhd(int) = 0;			//���ض�����׸��ڽӶ���ı��(��������)
	virtual int NextNbhd(int i,int j) = 0;	//����i����ڶ���j����һ�ڽӶ���
	virtual VStatus& Status(int) = 0;		//���ظö���״̬������(��ֱ���޸�)
	virtual int& DTime(int) = 0;			//���ض����ʱ���ǩ������
	virtual int& FTime(int) = 0;			//���ض����ʱ���ǩ������
	virtual int& Parent(int) = 0;			//���ض����ڱ������еĸ���
	virtual int& Priority(int) = 0;			//���ض����ڱ������е����ȼ�

	//�߲��ַ���,Լ��������ͼ
	virtual bool Exist(int,int) = 0;		//�жϱ�i->j�Ƿ����
	virtual void Insert(Te const&,int,int,int) = 0;	//����e���뵽����i,j֮�䲢����Ȩ��w
	virtual Te Remove(int,int) = 0;			//ɾ�������ı�
	virtual EType& Type(int,int) = 0;		//���رߵ����͵�����
	virtual Te& Edge(int,int) = 0;			//���رߵ�����(��ֱ���޸��ڲ�����)
	virtual int& Weight(int,int) = 0;		//���رߵ�Ȩ�ص�����

	//�����㷨�ӿ�
	//void BFS(int);			//��������㷨
	//void DFS(int);			//��������㷨
	//void BCC(int);			//����DFS��˫��ͨ�����ֽ��㷨!
	//SStack<Tv>* TSort(int);	//����DFS�����������㷨
	//void Prim(int);			//��С֧����Prim�㷨
	//void Dijkstra(int);		//���·��Dijkstra�㷨

	//template<typename PU>
	//void PFS(int,PU);		//���ȼ��������
};//class Graph



	

