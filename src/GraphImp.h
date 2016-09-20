/* Created on 2016-1-5 15:25:33 by LWZ. The impletation of class Graph */

#ifndef DERIVEDQUEUE_H
#include "DerivedQueue.h" 
#endif
#include "stdlib.h"				//for rand()
#include "time.h"				//for srand()

template<typename Tv,typename Te>
void Graph<Tv,Te>::bfs(int start)					//ȫͼ ��������㷨(���з���)
{
	reset();			//�������в���
	int clock = 0;
	int v = start;		//��start�Ŷ��㿪ʼ����
	do
	{
		if(status(v) == UNDISCOVERED)
		{
			BFS(v,clock);		//����ͨ���ڹ����������
		}						//����ÿ�����㶼���Լ��ķ���ʱ��
		v++;
		v %= n;
	}while(start != v)
}

template<typename Tv,typename Te>
void Graph<Tv,Te>::BFS(int startV,int& clock)		//����ͨ�� ��������㷨(˽�з���)
{
	SQueue<int> Q;
	Q.Enqueue(startV);
	status(startV) = DISCOVERED;	//���׶�����Ϊ�ѷ���
	while(!Q.IsEmpty())				//�ǿ�ʱ
	{
		int ver = Q.Dequeue();		
		dTime(ver) = ++clock;		//��¼����ʱ�� - ��������"����"��"����"������:���� �����Ƿ��������
		for(int u=firstNbr(ver);u>-1;u=nextNbr(ver,u))	//�����ھ���������,����-1ʱ���ھ�,�Ӵ�С�����ھ�
		{												//�ڶ���������ʾ�ӵڼ�����ʼ����
			if(status(u) == UNDISCOVERED)				//��δ������
			{
				status(u) = DISCOVERED;
				Q.Enqueue(u);
				type(ver,u) = TREE;						//����������Ⱥ��ǲ�һ����,����������ͼ��.�����״ε��õı�������
				parent(u) = ver;
			}
			else										//�Ѿ����ù���
			{
				type(ver,u) = CROSS;					//��ô���ǵı������
			}
		}												//���������ھ����
		status(ver) = VISITED;							//���ѵ�������,�������,��"�ѷ���"
	}//while
}
	
template<typename Tv,typename Te>
void Graph<Tv,Te>::dfs(int start)						//�Ӷ���start��ʼ����ȫͼ�����������(���нӿ�)
{
	reset();				//����
	int v = start;
	int clock = 0;
	do
	{
		if(status(v) == UNDISCOVERED)
		{
			DFS(v,clock);		//����ͨ���ڹ����������
		}						//����ÿ�����㶼���Լ��ķ���ʱ��
		v++;
		v %= n;
	}while(start != v)
}

template<typename Tv,typename Te>
void Graph<Tv,Te>::DFS(int start,int& clock)
{
	srand( (unsigned int)time(NULL) );		//��ʱ��������
	switch(rand()%2)						//��ѡһѡȡһ�ְ汾
	{
	case 1:
		DFS_Iter(start,clock);
		break;
	case 2:
		DFS_Recur(start,clock);
		break;
	default:
		break;
	}
}

template<typename Tv,typename Te>
void Graph<Tv,Te>::DFS_Recur(int start,int& clock)			//��ͨ���������������(�ݹ��)
{
	dTime(start) = ++clock;									//����:clock>0
	status(start) = DISCOVERED;
	for(int u=firstNbr(start);u>-1;u=nextNbr(start,u))
	{
		if(status(u) == UNDISCOVERED)						//����δ�����ֵĶ���
		{
			type(start,u) = TREE;		//����
			parent(u) = start;			//��¼������
			DFS_Recur(u,clock);			//��¼��߹�ϵ����и���һ��ݹ�
		}
		else if(status(u) == DISCOVERED)					//�ѷ��ֵ�δ���ʵĽڵ�
		{
			type(start,u) = BACKWARD;	//�ýڵ㻹δ���ڻ�δ���ʽ���״̬,�ʱ�Ϊ���ȵĽڵ�
		}
		else							//����״̬���ѷ��ʽ���VISITED
		{
			type(start,u) = (dTime(start) < dTime(u) )? FORWARD:CROSS;	//ǰ��߻���
		}
	}
	fTime(start) = ++clock;				//��¼���ʽ���ʱ��,ÿ�������Ծʱ����fTime - dTime
	status(start) = VISITED;
}

#define nhb(x) fTime(x)				//����fTime���洢��ǰ���ʵ��ڼ����ھ�
template<typename Tv,typename Te>
void Graph<Tv,Te>::DFS_Iter(int start,int& clock)			//����ͨ��������������㷨(������)
{
	//����������ҵ,����ջ��ʵ��								//��������鷳���ڼ�¼�Ѿ����ʵ��ڼ����ڵ�
	SStack<int> S;
	S.Push(start);
	while(!S.IsEmpty())
	{
top:	int v = S.Top();
		
		if(status(v) == UNDISCOVERED)
		{
			dTime(v) = ++clock;
			status(v) == DISCOVERED;
			nhb(v) = n;
		}
		while(-1< (nhb(v)=nextNbr(v,nhb(v))) )		//�����ھ�		�˴�����д��!
		{
			if(status(nhb(v)) == UNDISCOVERED)		//����δ�����ھ���ջ
			{
				S.Push(nhb(v));
				goto top;
			}
			else if(status(nhb(v)) == DISCOVERED)
			{
				type(v,nhb(v)) = BACKWARD;
			}	
			else									//VISITED
			{
				(dTime(v)<dTime(nhb(v)))? FORWARD:CROSS;	//���ݷ���ʱ���ж�
			}
		}//while(�����ھ�)
		status(v) = VISITED;
		S.Pop();					//���ʹ����е��ھ�,����
	}//while(!S.IsEmpty())
}
#undef nhb		//����ú궨��

template<typename Tv,typename Te>
SStack<Tv>* Graph<Tv,Te>::tSort(int start)			//ȫͼ��������(���нӿ�)
{
	SStack<Tv>* S = new SStack<Tv>;					//Ĭ�Ϲ��췽��
	reset();
	int clock = 0;
	int v = start;
	do
	{
		if(status(v) == UNDISCOVERED)
		{
			if(!TSort(v,clock,S))		//�����򲻳ɹ�(���������޻�ͼ)
			{
				while(!S->IsEmpty())
				{
					S->Pop();			//���ջ
				}
				break;
			}
		}
		v++;
		v %= n;
	}while(v != start)
	return S;			//�����򲻳ɹ����ؿ�ջ
}

template<typename Tv,typename Te>
bool Graph<Tv,Te>::TSort(int start,int& clock,SStack<Tv>* S)			//��ͨ���ڻ���DFS�����������㷨(�ݹ�)
{																		//�����������㷨��ȷ�Խ�����:ֻ�����еĺ������ջ֮������ջ,���ɱ�֤����
	status(start) = DISCOVERED;											//�к������ǰ��
	dTime(start) = ++clock;
	for(int u=firstNbr(start);u>-1;u=nextNbr(start,u))
	{
		switch(status(u))
		{
		case UNDISCOVERED:
			//����߹�ϵ
			type(start,u) = TREE;
			parent(u) = start;
			TSort(u,clock,S);
			break;
		case DISCOVERED:
			//�γɻ�
			type(start,u) = BACKWARD;
			return false;			//������������������޻�ͼ
		default:
			type(start,u) = (dTime(start)<dTime(u))?FORWARD:CROSS;
			break;
		}
	}
	S->Push(vertex(start));			//ע�������ɶ�������������Ԫ�ع��ɵ����������Ƕ������
}

template<typename Tv,typename Te>
void Graph<Tv,Te>::bcc(int start)				//ȫͼ��Χ��˫��ͨ��ֽ�,�Եݹ�DFSΪ��Ҫ���(˫��ͼ�����)
{
	reset();
	int clock = 0;
	int v = start;
	SStack<int> S;					//����ջ
	do
	{
		if(status(v) == UNDISCOVERED)
		{
			BBC(v,clock,S);
			S.Pop();				//��������Push�Ὣ����ͨ������������ջ
		}							//�ڴ���ͨ�����ʱ�轫��㵯��
		v++;
		v %= n;
	}while(v != start)
}

#define hca(x) fTime(x)			//��fTime��Ϊ��¼��ߴ�������
template<typename Tv,typename Te>
void Graph<Tv,Te>::BCC(int v,int& clock,SStack<int>& S)		//(��ͨ��)����DFS��˫��ͨ�����ֽ��㷨(�ڲ��ӿ�)
{
	S.Push(v);						//��ջ
	hca(v) = dTime(v) = ++clock;	//��¼����ʱ��͵�ǰ�������(�Լ�)
	status(v) = DISCOVERED;
	//�����������,��һ��Ѱ���ھ�

	for(int u=firstNbr(v);u>-1;u=nextNbr(v,u))
	{
		switch(status(u))
		{
		case UNDISCOVERED:
			type(v,u) = TREE;
			parent(u) = v;
			BCC(u,clock,S);
			//�ݹ������߶Ⱥ���!
			//if(hca(v)>hca(u))		//����ж��Ǵ����!��Ҫ����û��ָ���v���ߵ����ȶ����Ǳ�˭ָ�ĸ�!	
			if(hca(u)<dTime(v))		//����жϲ�˵����ָ����v������
			{						//��vҲҪ���¸߶�
				hca(v) = min(hca(v),hca(u));	//��������һ���������vָ����һ���ǳ��ߵ�����,��hca(v)�ǳ�С
			}									//��ô��ʱ���ĸ߶��ǲ���Ҫ��u�仯��,����ʱ�ж�if(hca(v)>hca(u))���Ǵ����
			else								//��ʱhca(u)>=dTime(v)(���п��ܵ��ڵ�)��u��������һȦ����,���ⲻ����v��Ϊ�ؽڵ�
			{									//������Ҫ�ָ�.v��ʱ���ǹؽڵ�!
				while(v != S.Pop());//�˴��ɽ�ջ��ת������������
				S.Push(v);			//���һ��������vͬ������������ͨ����,ѹ��
			}
			break;

		case DISCOVERED:
			type(v,u) = BACKWARD;
			if(u != parent(v))		//˫��ͼ�б����ų��ھ��Ǹ��׵������,��ָ����ߵ�����
			{
				hca(v) = min(hca(v),dTime(u));		//���¸߶�
			}
		default:		//VISITED�����ܳ���������ͼ��
			break;
		}//switch
	}//for
	status(v) = VISITED;
}
#undef hca

template<typename Tv,typename Te> template <typename PU>	//�������������ݵ����������ģ����ʽ���ɶ�Ӧ����ʵ��
void Graph<Tv,Te>::pfs(int start,PU prioOpt)				//ȫͼ���ȼ�����������,prioOpt�Ǹ��¶������ȼ��Ĳ�����
{
	reset();				//����
	int v = start;
	//int clock = 0;		//���Բ���clock,���з���˳���Ѿ��������ȼ���������
	do
	{
		if(status(v) == UNDISCOVERED)
		{
			PFS(v,prioOpt);		//����ͨ���ڹ����������
		}						//����ÿ�����㶼���Լ��ķ���ʱ��
		v++;
		v %= n;
	}while(start != v)
}

template<typename Tv,typename Te> template <typename PU>	//������ͨ���ڵ����ȼ�����,���Ӷ���O(n^2).ǰ����������O(n+e)�ߺͶ���ĺ� 
void Graph<Tv,Te>::PFS(int start,PU prioOpt)				//���ں��淽��Ҫ�ͱߵ�Ȩ�ҹ�,�涨:���ȼ�ֵԽС ���ȼ�Խ��
{
	priority(start) = 0;		//��ʼ��start�Ѿ���������,�����ȼ����
	status(start) = VISITED;
	parent(start) = -1;			//��ʾ������
	while(1)
	{
		//���µ�ǰ������ھӵ����ȼ�
		for(int u=firstNbr(start);u>-1;u=nextNbr(start,u))
		{
			prioOpt(this,start,u);			//��start���������ھ��������.this��Ϊ��ָ��ǰ��ͼ(������������ͼ��)
		}
		for(int shortest=INT_MAX,u=0;u<n;u++)		//ע��,������ȫͼɨ��������ȼ���δ���ʹ��Ķ��� - �Ľ�:ֻ�����Ѿ����¹����ھ�
		{											//��������start���ھ���ɨ��.����shortestΪ�˼�¼��ǰ������ȼ�
			if(status(u) == UNDISCOVERED && priority(u)< shortest)	//uδ���������ȼ����
			{
				shortest = priority(u);
				start = u;					//������һ��׼�����ʵĵ�
			}
		}
		if(status(start) == VISITED)	//ȫ��ͨ����ʹ�һ����
		{
			break;						//����while(1)ѭ��
		}
		status(start) = VISITED;
		type(parent(start),start) = TREE;	//��Ϊ���� �ڲ������и����˸�����
	}//while(1)
}

///////////////////////////prim�㷨������С֧����/////////////////////////////////
//prim�㷨�����ȼ������� prim�㷨���ڲ���minimum spanning tree(MST)��С֧����
//�����ѷ��ʼ�δ���ʶ�����"����"�������ȼ�,��Ŀ�����ҵ�Ȩ�غ���С����
template<typename Tv,typename Te>
class primPO
{
public:
	virtual void operator()(Graph<Tv,Te>* G,int v,int u)		//v�Ƿ�����Ͻڵ�,u�����������ھ�
	{
		if(G->status(u) == VISITED)		//���㷨�в����� DISCOVERED�����,���迼��
		{
			return;			//���ʹ������������u�����ȼ�,����Ҳ�ò���
		}
		if(G->weight(v,u) + G->priority(v) < G->priority(u))	//��·����С��ԭ��u�����ȼ�	
		{														//��u�����ȼ���Ҫ����
			G->priority(u) = G->weight(v,u) + G->priority(v);
			G->parent(u) = v;			//ͬʱ���¸�����
		}
	}
};//class primPO

template<typename Tv,typename Te> 
void Graph<Tv,Te>::prim(int start)						//��prim�㷨����MST�Ĺ��нӿ�
{
	pfs(start,primPO);
}
//////////////////////////////////////////////////////////////////////////////////////

///////////////////////////Dijkstra�㷨�������·����/////////////////////////////////
template<typename Tv,typename Te>
class DijkstraPO										//Ψһ���õ��Ǵ�ʱpriority�ڼ�¼���Ǹö��㵽��ʼ��������̾���
{														//���������ѷ����������̾���
	virtual void operator()(Graph<Tv,Te>* G,int v,int u)		//v�Ƿ�����Ͻڵ�,u�����������ھ�
	{
		if(G->status(u) == VISITED)		//���㷨�в����� DISCOVERED�����,���迼��
		{
			return;			//���ʹ������������u�����ȼ�,����Ҳ�ò���
		}
		if(G->weight(v,u) < G->priority(u))				//��Ȩ��С��ԭ��u�����ȼ�	
		{												//��u�����ȼ���Ҫ����
			G->priority(u) = G->weight(v,u);
			G->parent(u) = v;			//ͬʱ���¸�����
		}
	}
};//class DijkstraPO

template<typename Tv,typename Te> 
void Graph<Tv,Te>::dijkstra(int start)					//ֻ��д�����.������С·�����Ĺ��нӿ�
{
	pfs(start,DijkstraPO);
}
/////////////////////////////////////////////////////////////////////////

//��ҵ