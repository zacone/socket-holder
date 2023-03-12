package com.isthisone.socketholder.autoconfigure.socket;

import com.isthisone.socketholder.channel.HolderChannel;

public interface SocketHandler {

    void process(HolderChannel channel, String text);
}
