/* Created on 2015-12-18 09:35:47.Author:LWZ
   Remember to test here all the time 
*/

//#include <vector>
#include <iostream>
#include "VandE.h"
#include <vector>
#include <iterator>
#include <algorithm>
#include "TestCleaner.h"

using namespace std;

int main(int argc,char* argv[])
{
	//vector<vector<int>> M;
	//vector<int> K;
	//Vertex<int> V;
	//cout<<V.RefData()<<endl;
	//V.RefData() = 11;
	//cout<<V.RefData()<<endl;

	//这部分测试迭代器
	//vector<int> A(10,0);
	//A[0] = 38;
	//A[2] = 44;
	//*A._Myfirst = 77;
	//vector<int> B(A.begin(),A.end()-3);
	//cout<<B.size()<<endl;
	//cout<<B[0]<<endl;
	//ostream_iterator<int> out_iter(cout," ");
	//
	//ostream_iterator<int> out_I(out_iter);
	//copy(A.begin(),A.end(),out_iter);
	//cout<<endl;
	//copy(B.begin(),B.end(),out_I);
	//cout<<endl;

	//cout<<"Argu count="<<argc<<endl;
	//cout<<argv[0]<<endl;
	//cout<<argv[1]<<endl;
	CleanerNode* p = new CleanerNode();
	delete p;

	
	cin.get();


}