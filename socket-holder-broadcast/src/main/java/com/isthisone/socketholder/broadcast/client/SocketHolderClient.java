package com.isthisone.socketholder.broadcast.client;

public interface SocketHolderClient {

    String DEFAULT_TOPIC_NAME = "socket-holder-broadcast";

    default String getTopic() {
        return DEFAULT_TOPIC_NAME;
    }

    void broadcast(String msg);

}
