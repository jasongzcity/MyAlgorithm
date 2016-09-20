/* Created on 2015-12-8 22:08:44 Author:LWZ 
   For testing */

template <typename T>
class Plus1
{
public:
	virtual void operator()(T &e)
	{
		e++;		//确保元素已重载自加运算符
	}
};

template <typename T>
void Swap(T &e1,T &e2)
{
	T a = e1;
	e1 = e2;
	e2 = a;
}

template <typename T>
struct ShowAll
{
public:
	virtual void operator()(T const& e)
	{
		cout<<e<<endl;		//确保已经重载<<在cout中
	}
};