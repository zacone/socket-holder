package com.isthisone.socketholder.broadcast.listener;

public interface BroadcastListener {

    String topic();

    void listen(String topic, String msg);

}
