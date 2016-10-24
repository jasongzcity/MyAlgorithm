/**
* PQInterface.h 
* Defines virtual methods for Primary Queue.
* Last modified on: 2016/10/15
* Author:lwz
**/
#ifndef PQINTERFACE_H
#define PQINTERFACE_H

#include "./MyAlgorithm.h"

ALGORITHM_BEGIN

template<typename T>
class PQ
{
public:
    virtual void Insert(const T& e) = 0;
    virtual int Size() const = 0;
    virtual T DeleteMax() = 0;
    virtual T GetMax() = 0;
};

ALGORITHM_END

#endif //PQINTERFACE_H
