//
// Created by Kunal Chowdhury on 8/17/2016.
//

#ifndef ANN_V0_SOLACE_PUB_SUB_H
#define ANN_V0_SOLACE_PUB_SUB_H

#include <string>

using namespace std;
enum message_type
{
    INIT_REGISTRATION,
    INIT_REGISTRATION_REPLY,
    SM_PING,
    SM_PING_RESPONSE
};

struct message
{
  message_type type;
  string topic;
  string session_id;
};

class solace_pub_sub {

public:

    solace_pub_sub(const string &hostname, const string &user_name, const string &vpn_name);

    void init();

    void publish(const message& message);

    void subscribe(string topic);

    void unsubscribe(string topic);

    virtual ~solace_pub_sub();

private :

    string hostname, user_name, vpn_name;
};


#endif //ANN_V0_SOLACE_PUB_SUB_H
