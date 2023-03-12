package com.isthisone.socketholder.core.listener;

import com.isthisone.socketholder.channel.HolderChannel;

public interface SocketListener {

    void onSocketRegister(HolderChannel channel);

    void onSocketUnregister(HolderChannel channel);

}
