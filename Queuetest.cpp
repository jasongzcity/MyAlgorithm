/* Created at 2015-12-9 22:33:21 by LWZ */

#include "DerivedQueue.h"
#include <iostream>
#include "ObjectFunction.h"
#include "stdlib.h"

using namespace std;
void SimulateBank(int,int);


/* 接口测试
	
	全部通过

*/
class Customer
{
public:
	int ArrivedTime;
	int ProcTime;	//窗口处理时间
	int WaitTime;
	int LeaveTime;
	//int Window;		//所属窗口
	
	Customer(int a = 0)
		:ArrivedTime(a)
	{
		ProcTime = 1+rand()%10;		//假设客户处理时间在10分钟以内随机
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

	SimulateBank(4,4);//四个窗口,且每分钟有5分之四几率有顾客到来
	SimulateBank(4,3);
	SimulateBank(5,4);
	


	cin.get();
	
}

void SimulateBank(int win,int deno)		//第一个参数是窗口数,第二个参数,是模拟每分钟顾客到来的概率是deno/deno+1,每分钟不能大于一个顾客到来假设
{										//以分钟为最小时间单位
	int customcount = 0;
	int waittime = 0; 
	int customserved = 0;
	//SQueue<Customer> Wins[4];			//创建队列数
	SQueue<Customer> *Wins = new SQueue<Customer>[win];
	int i = 0;							//时间变量
	while((customserved < customcount) || i<600)	//循环直到所有顾客业务处理完毕且工作时间结束
	{
		if(i<600)								//前600分钟顾客入场
		{	
			if(rand()%(1+deno))					//有顾客入场
			{
				customcount++;
				Customer c(i);					//创建时已经自动生成了该顾客的处理时间,并保存了到访时间
				int shortestQ = 0;				//选出最短队列
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

		for(int n = 0;n<win;n++)		//出队列部分
		{
			if(!Wins[n].IsEmpty())	//队列非空
			{
				Wins[n].Front().ProcTime--;
				if(!(Wins[n].Front().ProcTime))		//业务处理完毕
				{
					Wins[n].Front().LeaveTime = i;
					Wins[n].Dequeue();				//顾客离开
					customserved++;					//服务顾客计数
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
	cout<<"窗口数:"<<win<<endl;
	cout<<"分母:"<<deno<<endl;
	cout<<"今日顾客:"<<customcount<<endl;
	cout<<"平均等待时间:"<<waittime/customcount<<endl;
	cout<<endl;
}
