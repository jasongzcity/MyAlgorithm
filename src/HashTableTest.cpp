/* Created on 2016-1-29 13:30:46 byLWZ. For hashtable testing */

#include "HashTable.h"

/*				接口测试
 *	Hashfunction对接正确
 *	Put正确 rehash正确
 *  remove正确 get正确
 *	size正确									*/

class Hashfunction
{
public:
	int operator()(int key,int bucketnum)		//基本除余法
	{
		return key%bucketnum;
	}
	//...可重载
};

int main(void)
{
	using namespace std;
	HashTable<char,int,Hashfunction> Hss=HashTable<char,int,Hashfunction>(5);//试下能否对接key为char和输入参数为int的哈希函数
	Hss.Put('a','a');
	Hss.Put('a','a');
	Hss.Put('b','b');
	Hss.Put('c','c');
	Hss.Put('f','f');
	Hss.Put('q','q');
	Hss.Put('r','r');
	Hss.Put('t','t');
	Hss.Put('z','z');
	Hss.Put('s','s');
	Hss.Put('y','y');
	Hss.Put('k','k');
	cout<<*Hss.Get('c')<<endl;
	cout<<"词条数目:"<<Hss.Size()<<endl;
	cout<<"删除开始"<<endl;
	Hss.Remove('s');
	Hss.Remove('b');
	cout<<*Hss.Get('c')<<endl;
	if(!Hss.Get('b'))
	{
		cout<<"Remove Rightly!"<<endl;
	}
	cout<<"词条数目:"<<Hss.Size()<<endl;
	cin.get();
	return 0;
}