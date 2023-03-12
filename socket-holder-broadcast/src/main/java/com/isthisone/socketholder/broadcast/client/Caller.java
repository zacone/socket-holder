package com.isthisone.socketholder.broadcast.client;

public interface Caller {

    void broadcast(String topic, String msg);
}
