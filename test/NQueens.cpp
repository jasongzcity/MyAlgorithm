/**
* NQueens.cpp
* Use stack and backtrack to solve N Queen problem.
* Author: lwz
* Created on 2016-12-16
**/

#include "stdafx.h"

class Queen;
//print queen on standard output
void PrintQueen(MyAlgorithm::MyStack<Queen> & ,int);

/**
* Object Queen.
* To simulate the behavior of queens.
**/
class Queen
{
/*    x,y  axis
        y| 
         |
         |
         .----------x    
         0
*/
    
public:
    int x,y;//coordinate
    bool Valid;
    Queen(int xx = 0,int yy = 0)
    {
        x = xx;
        y = yy;
        Valid = true; 
    }

    /**
    * use operator to check if the current queen valid.
    **/
    void operator()(Queen const &q)
    {
        if( (x == q.x) || (y == q.y) || ( x+y == q.x+q.y) || (x-y == q.x - q.y))
            Valid &= false;
    }
    Queen& operator=(Queen const&q)
    {
        x = q.x;
        y = q.y;
        return *this;
    }
};//class Queen


/**
* Place queen to solve.
* N is the number of queens.
* stop when number of solutions surpasses the limit
**/
void PlaceQueen(int N, int limit)
{
    using namespace MyAlgorithm;
    int SoluCount = 0;

    MyStack<Queen> Solu;             //storage of current solution and help backtracking.
    Queen q(0,0);
    
    while(q.x<N || q.y>0)   //when the queen in the first line(y==0) is out of right margin(x==N), loop ends.
    {
begin:  q.Valid = true;
        Solu.Traverse(q);
        while(!q.Valid && q.x<N)//search in the same line. So x change.
        {
            q.x++;
            q.Valid = true;
            Solu.Traverse(q);//check queen
        }
        if(q.x<N)                //valid position
        {
            Solu.Push(q);     //copy the queen in the stack
            if(Solu.size() == N)//solution get!
            {
                SoluCount++;  
                PrintQueen(Solu,SoluCount);   //print
                Solu.Pop();         //pop out the position of the last line.
                q = Solu.Pop();   //then search again in the second to last line
                q.x++;                  //and the next row.   
                goto begin;         //goto the beginning of the search
            }
            q.y++;                  //search in the next line and first position
            q.x = 0;                
        }
        else                          //no suitable position in current line
        {
            q = Solu.Pop();  //backtrack, search again in the last line
            q.x++;
        }
        
        if(SoluCount>=limit) // control the number of solutions.
            return;
    }//while
}

void PrintQueen(MyAlgorithm::MyStack<Queen> & s,int solutionCount)
{
    using namespace std;
    cout<<"S"<<solutionCount<<":  ";
    for(int i = 0;i<s.size();i++)
        cout<<"("<<s[i].x<<", "<<s[i].y<<")  ";
    cout<<"\n"<<endl;
}
