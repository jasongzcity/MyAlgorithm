/* Created on 2016-2-4 by LWZ. */
#ifndef PQINTERFACE_H
#define PQINTERFACE_H

template<typename T>
class PQ
{
public:
	virtual void Insert(const T& e) = 0;
	virtual int Size() const = 0;
	virtual T DeleteMax() = 0;
	virtual T GetMax() = 0;
};

#endif //PQINTERFACE_H
