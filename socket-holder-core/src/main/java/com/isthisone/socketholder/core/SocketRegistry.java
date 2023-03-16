package com.isthisone.socketholder.core;

import com.isthisone.socketholder.channel.HolderChannel;

public interface SocketRegistry {

    void register(HolderChannel channel);

    boolean unregister(HolderChannel channel);

}
