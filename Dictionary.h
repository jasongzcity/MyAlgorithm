/* Created on 2016-1-24 14:45:52 by LWZ. Declaration for class Dictionary */

#define DICTIONARY_H

template <typename K,typename V>
class Dictionary
{
public:
	virtual int Size() const = 0;
	virtual bool Put(K k,V v) = 0;
	virtual bool Remove(K k) = 0;
	virtual V* Get(K k) = 0;			//∂¡»°¥ Ãı
};//class Dictionary