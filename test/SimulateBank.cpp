/**
* SimulateBank.cpp
* use queue data structure to simulate queueing situation in bank
* and calculate queueing time.
* Created on 2016-12-16 
* Author: lwz
**/

#include "stdafx.h"

/**
* class Customer to simulate the customer's behavior
**/
class Customer
{
public:
    int ArrivedTime;
    int ProcTime;   //process time at the window
    int WaitTime;
    int LeaveTime;
    //int Window; 
    
    Customer(int a = 0)
        :ArrivedTime(a)
    {
        ProcTime = 1+rand()%10;  // proc time from 1-10 minute.
        WaitTime = 0;
    }

    ~Customer(){}

    Customer& operator=(Customer const& c)
    {
        ArrivedTime = c.ArrivedTime;
        ProcTime = c.ProcTime;
        WaitTime = c.WaitTime;
    }

};//class Customer

/**
* The time is simulated in minute.
* parameter win stands for the windows(also lines) the bank opens.
* parameter deno stands for the probability of custom arrives in one minute: deno/deno+1
* parameter minute stands for the simulated minutes.
**/
void SimulateBank(int win,int deno,int minute)
{
    using namespace MyAlgorithm;
    using namespace std;

    int customcount = 0; //record number of custom
    int waittime = 0; //record ALL customs wait time
    int customserved = 0; 
    PrepareRandom();

    MyQueue<Customer> *Wins = new MyQueue<Customer>[win];
    int i = 0; 
    while((customserved < customcount) || i<minute)//end loop only when time > limit and all customs served.
    {
        if(i<minute)    // "generate" customs
        {   
            if(rand()%(1+deno))            //custom arrived
            {
                customcount++;
                Customer c(i);                  //the custom arrives at minute i
                int shortestQ = 0;              
                for(int j=0;j<win-1;j++)//find the shortest queue
                {
                    if(Wins[j].Size() > Wins[j+1].Size())
                        shortestQ = j+1;
                }
                Wins[shortestQ].Enqueue(c); //get in line
            }
        }

        for(int n = 0;n<win;n++)//check windows
        {
            if(!Wins[n].IsEmpty())  
            {
                Wins[n].Front().ProcTime--;
                if(!(Wins[n].Front().ProcTime))    //process ends
                {
                    Wins[n].Front().LeaveTime = i; //leave at minute i
                    Wins[n].Dequeue();
                    customserved++;
                    if(!Wins[n].IsEmpty())
                    {
                        Wins[n].Front().WaitTime =  i - Wins[n].Front().ArrivedTime;//record the custom's wait time
                        waittime += Wins[n].Front().WaitTime;
                    }
                    
                }
            }//IsEmpty
        }//for
        i++;
    }//while

    delete [] Wins;
    float avg = waittime/customcount;
    cout<<"Window number: "<<win<<endl;
    cout<<"Probability: "<<deno<<endl;
    cout<<"Today's customs: "<<customcount<<endl;
    cout<<"average wait time: "<<avg<<endl;
    cout<<endl;
}
