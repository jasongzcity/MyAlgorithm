/* Cleaner test*/
#ifndef ADT_VECTOR_H
#include "ADT_Vector.h"
#endif

using namespace std;

class CleanerNode
{
	SVector< SVector<int> > Temp;
public:
	~CleanerNode()
	{
		cout<<"Clean Node!"<<endl;
	}
};