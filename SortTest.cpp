/* Created on 2016-2-15 21:04:14 by lwz */

#include "QuickSort.h"

int main(int argc,char* argv[])
{
	int a[10] = {15,84,1564,84,561,152,48,49,20,99};
	QuickSort(a,0,10);
	for(int i=0;i<10;i++)
	{
		cout<<a[i]<<endl;
	}
	cin.get();
	return 0;
}
