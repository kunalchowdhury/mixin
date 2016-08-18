#include <iostream>
#include <map>
#include <memory>
//#include <w32api/cdoex.h>
#include "Boltzman.h"
#include "Person.h"
#include "TNode.h"
#include "solace_pub_sub.h"

#include <functional>
#include <chrono>
#include <future>
#include <cstdio>

using namespace std;

void f(int i, std::string s) {
    cout << "Simple ANN " << s << "\n";
}

enum Src {
    t1, t2, t3
};

enum Dest {
    u1, u2, u3
};

void ff1(int i) {
    cout << "value from ff1 = " << i << "\n";
}

bool ff2(std::string s) {
    cout << "value from ff2 " << s << "\n ";
    return false;
}

typedef void (*FTransitionAction)(int);

typedef bool (*FPreCondition)(std::string);

template<typename T>
std::map<T, std::pair<FPreCondition, FTransitionAction> > m1;

template<typename T>
void add(T &&key, std::pair<FPreCondition, FTransitionAction> val) {
    m1<Src>[std::forward<T>(key)] = val;
}
//////////////////
void f1(int p1)
{
    cout << "inside f1" ;
}

void f2(int p1)
{
    cout << "inside f2" ;
}

void f3(int p1)
{
    cout << "inside f3" ;
}

bool p1(std::string s)
{
    cout << "inside p1" ;
    return true;
}

bool p2(std::string  s)
{
    cout << "inside p2" ;
    return true;
}

bool p3(std::string  s)
{
    cout << "inside p3" ;
    return true;
}

void fun_thread(solace_pub_sub* s)
{
   while(1) {
       s->inc();
       this_thread::sleep_for(chrono::milliseconds(5000));
   }
}

//////////
int main()
{
    //std::unique_ptr<TNode> ptr= std::make_unique<TNode>(0, 0, 0);

    int thread_count = thread::hardware_concurrency();
    cout << "total threads = " << thread_count << "\n" ;
    thread total_thread[thread_count];
    solace_pub_sub ps("","","");
    for (int j = 0 ; j < thread_count ; j++)
    {
        total_thread[j] = std::thread(fun_thread, &ps);
    }

    for (int j = 0 ; j < thread_count ; j++)
    {
        total_thread[j].join();
    }
    cout << "hello \n " ;
    std::unique_ptr<std::vector<std::pair<FPreCondition, FTransitionAction>>> ptr;
    vector<pair<FPreCondition, FTransitionAction>> type; //&type = *ptr;
    type.push_back(std::pair<FPreCondition, FTransitionAction>(p1, f1));
    type.push_back(std::pair<FPreCondition, FTransitionAction>(p2, f2));
    type.push_back(std::pair<FPreCondition, FTransitionAction>(p3, f3));


    for (auto && ent :type ) {
        ent.first("s");
        ent.second(1);
        cout << "\n";

    }

    /*for (int i = 0 ; i < 3; i++) {
        type.at(i).first("s");
        type.at(i).second(1);
        //cout << first << " -- " << a.second;
    }*/
}

int main000()
{
    const Person p("Nancy");
    cout << "Nancy created \n" ;

    Person p1(p);
    cout << "Nancy created again\n" ;

    unique_ptr<Person> ptr;
    ptr.reset(new Person(p));

    //*ptr->getname();
    cout << ptr.get()->getname() << " from ptr \n";

    map <string, string> m;
    m["D"] ="AB";
    m["B"] ="CC";

    std::unique_ptr<std::map<string, string>> ptr1;
    ptr1.reset(&m) ;

    map<string, string> &type = ptr1.operator*();
    for (auto &&  e: type ) {
        cout << e.first << " -- " << e.second << "\n";
    }
}
int main3() {
    std::map<Src, std::string> m;
    m[t1] = "first";
    m[t2] = "second";
    m[t3] = "third";

    auto s = m.find(t2)->second;
    cout << s << "\n";

    //  std::pair <Src, std::string> pair;

    add(t1, std::make_pair(ff2, ff1));

    pair<FPreCondition, FTransitionAction> &sec = m1<Src>.find(t1)->second;
    sec.second(3);

    cout << sec.first("hello ") << "\n";

//    cout << "test " << "\n";

}

int main1() {
    nn::Holder<float> *h = new nn::Holder<float>();
    typedef void(nn::Holder<float>::*FloatTypeExec)(float, int);
    FloatTypeExec floatTypeExecPtr = &nn::Holder<float>::exec;

    (h->*floatTypeExecPtr)(12.4f, 6);

    cout << "FPTR!" << endl;

    void (*funptr1)(int, std::string) = nn::fun<int>;
    typedef void (*TFPtr)(int, std::string);

    std::vector<TFPtr> v;
    v.push_back(nn::fun<int>);
    v.push_back(f);

    for (auto f : v) {
        f(2, "On a dark desert highway .. ");

    }


    return 0;
}
