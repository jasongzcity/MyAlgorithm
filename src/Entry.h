/* Created on 2016-1-7 10:31:45 by LWZ. Template for entry */

#define ENTRY_H

template<typename K,typename V>
class Entry
{
public:
	V value;		
	K key;			//查找词条

	//默认构造函数
	Entry(K k = (K)0,V v = (V)0)
		:value(v),key(k){}

	//重载运算符便于搜索 < == > !=
	bool operator<(const Entry<K,V>& E)	//须和同类型的表比较
	{
		return (key < E.key);
	}

	bool operator>(const Entry<K,V>& E)
	{
		return (key > E.key);
	}
	bool operator==(const Entry<K,V>& E)
	{
		return (key == E.key);	//留意这是互异的词条情况!
	}
	bool operator!=(const Entry<K,V>& E)
	{
		return (key != E.key);
	}
};//class Entry词条