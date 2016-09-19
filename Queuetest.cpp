/* Created at 2015-12-9 22:33:21 by LWZ */

#include "DerivedQueue.h"
#include <iostream>
#include "ObjectFunction.h"
#include "stdlib.h"

using namespace std;
void SimulateBank(int,int);


/* �ӿڲ���
	
	ȫ��ͨ��

*/
class Customer
{
public:
	int ArrivedTime;
	int ProcTime;	//���ڴ���ʱ��
	int WaitTime;
	int LeaveTime;
	//int Window;		//��������
	
	Customer(int a = 0)
		:ArrivedTime(a)
	{
		ProcTime = 1+rand()%10;		//����ͻ�����ʱ����10�����������
		WaitTime = 0;
	}

	~Customer()
	{

	}

	Customer& operator=(Customer const& c)
	{
		ArrivedTime = c.ArrivedTime;
		ProcTime = c.ProcTime;
		WaitTime = c.WaitTime;
	}

};//class Customer



int main()
{
	//SQueue<> q;
	//Plus1<int> p;
	//ShowAll<int> Show;
	////q.Enqueue(4);
	//q.Enqueue(8);
//	q.Enqueue(8);
//	q.Enqueue(129);
//	int d = q.Dequeue();
//	q.Traverse(p);
//
//	q.Traverse(Show);
//	cout<<endl;
//	cout<<d<<endl;

	SimulateBank(4,4);//�ĸ�����,��ÿ������5��֮�ļ����й˿͵���
	SimulateBank(4,3);
	SimulateBank(5,4);
	


	cin.get();
	
}

void SimulateBank(int win,int deno)		//��һ�������Ǵ�����,�ڶ�������,��ģ��ÿ���ӹ˿͵����ĸ�����deno/deno+1,ÿ���Ӳ��ܴ���һ���˿͵�������
{										//�Է���Ϊ��Сʱ�䵥λ
	int customcount = 0;
	int waittime = 0; 
	int customserved = 0;
	//SQueue<Customer> Wins[4];			//����������
	SQueue<Customer> *Wins = new SQueue<Customer>[win];
	int i = 0;							//ʱ�����
	while((customserved < customcount) || i<600)	//ѭ��ֱ�����й˿�ҵ��������ҹ���ʱ�����
	{
		if(i<600)								//ǰ600���ӹ˿��볡
		{	
			if(rand()%(1+deno))					//�й˿��볡
			{
				customcount++;
				Customer c(i);					//����ʱ�Ѿ��Զ������˸ù˿͵Ĵ���ʱ��,�������˵���ʱ��
				int shortestQ = 0;				//ѡ����̶���
				for(int j=0;j<win-1;j++)
				{
					if(Wins[j].Size() > Wins[j+1].Size())
					{
						shortestQ = j+1;
					}
				}
				Wins[shortestQ].Enqueue(c);
			}//if(rand())
		}//if

		for(int n = 0;n<win;n++)		//�����в���
		{
			if(!Wins[n].IsEmpty())	//���зǿ�
			{
				Wins[n].Front().ProcTime--;
				if(!(Wins[n].Front().ProcTime))		//ҵ�������
				{
					Wins[n].Front().LeaveTime = i;
					Wins[n].Dequeue();				//�˿��뿪
					customserved++;					//����˿ͼ���
					if(!Wins[n].IsEmpty())
					{
						Wins[n].Front().WaitTime =  i - Wins[n].Front().ArrivedTime;
						waittime += Wins[n].Front().WaitTime;
					}
					
				}
			}//IsEmpty
		}//for
		i++;
	}//while
	delete [] Wins;
	cout<<"������:"<<win<<endl;
	cout<<"��ĸ:"<<deno<<endl;
	cout<<"���չ˿�:"<<customcount<<endl;
	cout<<"ƽ���ȴ�ʱ��:"<<waittime/customcount<<endl;
	cout<<endl;
}
