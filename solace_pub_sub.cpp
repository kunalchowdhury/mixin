//
// Created by Kunal Chowdhury on 8/17/2016.
//

#include <iostream>
#include <thread>
#include "solace_pub_sub.h"
#include <chrono>
static inline void spinlock(volatile int *lock) { while(!__sync_bool_compare_and_swap(lock, 0, 1)) { sched_yield(); } }

static inline void spinunlock(volatile int *lock) { *lock = 0; }

solace_pub_sub::solace_pub_sub(const string &hostname, const string &user_name, const string &vpn_name) : hostname(
        hostname), user_name(user_name), vpn_name(vpn_name)
{
    _lock = 0;
    _cur = 0;
}

solace_pub_sub::~solace_pub_sub() {

}

void solace_pub_sub::init() {

}

void solace_pub_sub::publish(const message &message) {

}

void solace_pub_sub::subscribe(string topic) {

}

void solace_pub_sub::unsubscribe(string topic) {

}

void solace_pub_sub::inc() {
    spinlock(&_lock);
    long t = std::chrono::high_resolution_clock::now().time_since_epoch().count()/1000000000  ;
    ++_cur;
    std::cout << " thread id value -> " << this_thread::get_id() << " -- " << _cur << " @ " << t << " \n" ;
    spinunlock(&_lock);
}
