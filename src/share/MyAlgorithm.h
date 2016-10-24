/**
* MyAlgorithm.h 
* Author: lwz
* This header file contains the shared functions & classes in the namespace MyAlgorithm.
* Since: 2016-9-24 
**/

#ifndef MYALGORITHM_H
#define MYALGORITHM_H

#define ALGORITHM_BEGIN namespace MyAlgorithm{
#define ALGORITHM_END };

#include <iostream>
#include <cstdlib>

ALGORITHM_BEGIN

typedef int Rank;

/**
* Obeject function that frequently used in tests.
* use the traverse interface to plus 1 on every element in a container.
* Make sure element type has overload operator ++ before use!
**/
template<typename E> class Plus1
{
public:
    virtual void operator()(E& element)
    {
        element++;
    }
};

/**
* Obeject function that frequently used in tests.
* use the traverse interface to count the number of elements.
**/
template <typename T> class CountElement
{
private:
	int count;
public:
	CountElement()
	{
		count = 0;
	}
	virtual void operator()(T const& e)
	{
		count++;
	}
    int result()
    {
        return count;
    }
    void clear()
    {
        count = 0;
    }
};

template<typename E>class ShowAll
{
public:
    virtual void operator()(E& const element)
    {
        std::cout<<element<<std::endl;
    }
};

/**
* Frequently used "swap" action.
* Swap the value of a,b
**/
template<typename E> void swap(E& a, E& b)
{
    E temp;
    temp = a;
    a = b;
    b = temp;
}

inline void PrepareRandom()
{
    time_t t;
    time(&t);
    srand(t);
}

ALGORITHM_END

#endif //MYALGORITHM_H