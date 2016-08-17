//
// Created by Kunal Chowdhury on 8/17/2016.
//

#include "solace_pub_sub.h"

solace_pub_sub::solace_pub_sub(const string &hostname, const string &user_name, const string &vpn_name) : hostname(
        hostname), user_name(user_name), vpn_name(vpn_name) {}

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
