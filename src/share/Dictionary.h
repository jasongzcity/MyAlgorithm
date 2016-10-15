/**
* Dictionary.h.
* Defines interfaces.
* Last modified on: 2016/10/13
* Author:lwz
**/
#ifndef DICTIONARY_H
#define DICTIONARY_H

#include "./MyAlgorithm.h"

ALGORITHM_BEGIN

template <typename K,typename V>
class Dictionary
{
public:
    /**
    * Return the size of this dictionary.
    **/
    virtual int Size() const = 0;

    /** 
    * Put this key-value pair into the hashtable.
    * Return true if put success.
    **/
    virtual bool Put(K k,V v) = 0;

    /** 
    * Remove Key.
    * Return true if remove success.
    **/
    virtual bool Remove(K k) = 0;

    /**
    * Return the pointer of the value of this key.
    **/
    virtual V* Get(K k) = 0;
};

ALGORITHM_END

#endif //DICTIONARY_H