package com.isthisone.socketholder.core.listener;

public interface SocketListenerRegistry {

    void addListener(SocketListener listener);

    void removeListener(SocketListener listener);

}
