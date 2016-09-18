/* Created on 2016-2-4 13:15:45 by LWZ.*/
#include "PQ_LeftHeap.h"
#include "PQ_BinHeap.h"


/*       接口测试
 * 构造函数   析构函数通过
 * 几个接口计数正常
 * 插入,向上渗透正常
 * 向下渗透正常
 * 左式堆基本验证正确
 *
 **/

using namespace std;

int main(void)
{
	int A[7] = {0,1,2,3,4,5,6};
	
	//PQ_BinHeap<int> Heap(A,7);
	PQ_LeftHeap<int> Heap(A,7);
	cout<<Heap.GetMax()<<endl;
	//cout<<Heap.DeleteMax()<<endl;
	Heap.Insert(78);
	cout<<Heap.GetMax()<<endl;
	//cout<<Heap.DeleteMax()<<endl;
	Heap.Insert(99);
	Heap.Insert(35);
	//cout<<Heap.DeleteMax()<<endl;
	//cout<<Heap.DeleteMax()<<endl;
	cout<<Heap.GetMax()<<endl;
	Heap.Insert(99);
	Heap.Insert(77);
	cout<<Heap.GetMax()<<endl;
	Heap.Insert(128);
	cout<<Heap.GetMax()<<endl;
	cout<<Heap.DeleteMax()<<endl;
	cout<<Heap.DeleteMax()<<endl;
	cout<<Heap.DeleteMax()<<endl;
	cout<<Heap.DeleteMax()<<endl;
	cout<<Heap.DeleteMax()<<endl;
	cout<<Heap.DeleteMax()<<endl;
	cout<<Heap.DeleteMax()<<endl;
	cout<<Heap.DeleteMax()<<endl;
	Heap.Insert(97);
	cout<<Heap.GetMax()<<endl;
	cout<<Heap.Size()<<endl;
	

	cin.get();
	return 0;
}