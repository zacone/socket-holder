package com.isthisone.socketholder.autoconfigure.socket;

import com.isthisone.socketholder.core.SocketHolder;

public interface SocketServerBootstrap {

    void start() throws Exception;

    void setSocketHandler(SocketHandler socketHandler);

    void setSocketHolder(SocketHolder socketHolder);

}
