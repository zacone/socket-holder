package com.isthisone.socketholder.core;

import java.util.Set;

public interface SocketNotify {

    void send(String socketId, String msg);

    void send(Set<String> socketIds, String msg);

    void sendAll(String msg);
}
